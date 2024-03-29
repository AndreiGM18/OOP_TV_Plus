package application;

import constants.Constants;
import fileio.ContainsInput;
import fileio.FilterInput;
import implementation.Movie;
import implementation.User;
import page.AuthenticatedPage;
import page.Login;
import page.Logout;
import page.Movies;
import page.Page;
import page.Register;
import page.SeeDetails;
import page.UnauthenticatedPage;
import page.Upgrades;
import application.sortstrategy.SortStrategy;
import application.sortstrategy.SortStrategyFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class App {
    private static App instance = null;

    private App() {
        this.init();
    }

    /**
     * Singleton pattern
     * @return instance
     */
    public static App getApp() {
        if (instance == null) {
            instance = new App();
        }

        return instance;
    }

    private Page currentPage;
    private User currentUser;
    private ArrayList<Movie> currentMoviesList = new ArrayList<>();

    private HashMap<String, Page> pages = new HashMap<>();

    /**
     * Initializes the pages, adding them in the pages HashMap
     * Also sets the current page to the UnauthenticatedPage
     */
    public void init() {
        AuthenticatedPage authenticatedPage = new AuthenticatedPage();
        Login login = new Login();
        Logout logout = new Logout();
        Movies moviesPage = new Movies();
        Register register = new Register();
        SeeDetails seeDetails = new SeeDetails();
        UnauthenticatedPage unauthenticatedPage = new UnauthenticatedPage();
        Upgrades upgrades = new Upgrades();

        this.pages.put(Constants.Page.AUTH, authenticatedPage);
        this.pages.put(Constants.Page.LOGIN, login);
        this.pages.put(Constants.Page.LOGOUT, logout);
        this.pages.put(Constants.Page.MOVIES, moviesPage);
        this.pages.put(Constants.Page.REGISTER, register);
        this.pages.put(Constants.Page.DETAILS, seeDetails);
        this.pages.put(Constants.Page.UNAUTH, unauthenticatedPage);
        this.pages.put(Constants.Page.UPGRADES, upgrades);

        this.currentPage = unauthenticatedPage;
    }

    /**
     * Clears the app session
     * Initializes the pages HashMap again
     */
    public void clearApp() {
        this.currentUser = null;
        this.currentMoviesList.clear();
        this.pages.clear();
        this.init();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public HashMap<String, Page> getPages() {
        return pages;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    /**
     * When this setter is called, it makes sure to exclude any movies that are banned in the
     * current user's country
     * @param movies
     */
    public void setCurrentMoviesList(final ArrayList<Movie> movies) {
        currentMoviesList.clear();
        for (Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(currentUser.getCredentials().getCountry())) {
                this.currentMoviesList.add(movie);
            }
        }
    }

    /**
     * Returns a movie from the currentMoviesList
     * @param name
     * @return the movie, if it is found, or null
     */
    public Movie getMovie(final String name) {
        for (Movie movie : currentMoviesList) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Sets the currentMoviesList to all movies that start with the startWith string
     * @param startWith
     */
    public void search(final String startWith) {
        ArrayList<Movie> newCurrentMoviesList = new ArrayList<>();
        for (Movie movie : currentMoviesList) {
            if (movie.getName().startsWith(startWith)) {
               newCurrentMoviesList.add(movie);
            }
        }
        currentMoviesList = newCurrentMoviesList;
    }

    /**
     * Sets the currentMovieList to a list that contains everything specified in the containsInput
     * @param containsInput
     */
    public void contains(final ContainsInput containsInput) {
        ArrayList<Movie> newCurrentMoviesList = new ArrayList<>();

        for (Movie movie : currentMoviesList) {
            boolean checkActors = true, checkGenre = true;

            /* Checks if all given actors are part of the movie's cast */
            if (containsInput.getActors() != null) {
                for (String actor : containsInput.getActors()) {
                    if (!movie.getActors().contains(actor)) {
                        checkActors = false;
                        break;
                    }
                }
            }

            /* Checks if all given genres are part of the movie's genres */
            if (containsInput.getGenre() != null) {
                for (String genre : containsInput.getGenre()) {
                    if (!movie.getGenres().contains(genre)) {
                        checkGenre = false;
                        break;
                    }
                }
            }

            if (checkActors && checkGenre) {
                newCurrentMoviesList.add(movie);
            }
        }

        currentMoviesList = newCurrentMoviesList;
    }

    /**
     * Sorts the currentMovieList, making sure it contains everything that the filter specifies
     * @param filter
     */
    public void filter(final FilterInput filter) {
        if (filter.getSort() != null) {
            SortStrategy strategy = SortStrategyFactory.createStrat(filter.getSort());
            if (strategy != null) {
                strategy.sort(this.currentMoviesList);
            }
        }

        if (filter.getContains() != null) {
            this.contains(filter.getContains());
        }
    }

    /**
     * Returns a list of the current user's most liked genres
     * @return the list
     */
    public ArrayList<String> getMostLikedGenres() {
        ArrayList<String> mostLikedGenres = new ArrayList<>();

        /* Copies all genres that have at least a like from the user */
        for (Map.Entry<String, Integer> entry : currentUser.getGenreNumLikes().entrySet()) {
            mostLikedGenres.add(entry.getKey());
        }

        /* Sorts them based on the number of likes and, in case of equality, lexicographically */
        mostLikedGenres.sort(new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                if (currentUser.getGenreNumLikes().get(o1)
                        == currentUser.getGenreNumLikes().get(o2)) {
                    if (o1.compareTo(o2) >= 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (currentUser.getGenreNumLikes().get(o1)
                        < currentUser.getGenreNumLikes().get(o2)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return mostLikedGenres;
    }

    /**
     * Returns a list of the most liked movies in the current movies list
     * @return the list
     */
    public ArrayList<Movie> getMostLikedMovies() {
        /* Copies all movies */
        ArrayList<Movie> mostLikedMovies = new ArrayList<>(currentMoviesList);

        /* Sorts them based on the number of likes and, in case of equality, lexicographically */
        mostLikedMovies.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getNumLikes() == o2.getNumLikes()) {
                    if (o1.getName().compareTo(o2.getName()) >= 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if (o1.getNumLikes() < o2.getNumLikes()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return mostLikedMovies;
    }
}
