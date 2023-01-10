package database;

import constants.Constants;
import obs.Observable;
import fileio.CredentialsInput;
import fileio.MovieInput;
import fileio.UserInput;
import implementation.Movie;
import implementation.User;
import notification.Notification;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class implements the Observable design pattern
 */
public final class Database extends Observable {
    private static Database instance = null;

    private Database() {
    }

    /**
     * Singleton pattern
     * @return instance
     */
    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private LinkedList<User> userDatabase = new LinkedList<>();
    private LinkedList<Movie> movieDatabase = new LinkedList<>();

    /**
     * Adds a user to the userDatabase
     * @param user
     */
    public void putUser(final User user) {
        userDatabase.addLast(user);
    }

    /**
     * Adds a movie to the movieDatabase
     * @param movie
     */
    public void putMovie(final Movie movie) {
        movieDatabase.addLast(movie);
    }

    /**
     * Returns a user from the database
     * @param credentialsInput credentials
     * @return the user, if found, or null
     */
    public User getUser(final CredentialsInput credentialsInput) {
        for (User user : userDatabase) {
            if (user.getCredentials().getName().equals(credentialsInput.getName())
                    && user.getCredentials().getPassword().equals(credentialsInput.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public LinkedList<Movie> getMovieDatabase() {
        return movieDatabase;
    }

    /**
     * Clears both the user and the movie databases
     */
    public void clearDatabase() {
        userDatabase.clear();
        movieDatabase.clear();
    }

    /**
     * Creates a user, then it adds it to the database
     * A user is also considered an observer
     * @param credentialsInput credentials
     * @return the newly created user
     */
    public User addUser(final CredentialsInput credentialsInput) {
        User user = new User.UserBuilder(credentialsInput)
                .build();
        this.putUser(user);

        /* Adds the user as an observer */
        this.addObserver(user);

        return user;
    }

    /**
     * Adds a movie in the database, if the movie is not already present
     * @param movieInput the movie's input data
     * @return whether the movie was already present or not
     */
    public boolean addMovie(final MovieInput movieInput) {
        /* Going through all movies */
        for (Movie movie : movieDatabase) {
            /* The movie was found, so it already exists */
            if (movie.getName().equals(movieInput.getName())) {
                return false;
            }
        }

        Movie newMovie = new Movie.MovieBuilder(movieInput)
                .build();
        this.putMovie(newMovie);

        /* Notifying all observers */
        Notification notification = new Notification("ADD", newMovie);
        notifyAllObs(notification);

        return true;
    }

    /**
     * Removes a movie from the database, if the movie is already present
     * @param movieName movieInput the movie's input data
     * @return whether the movie was present in the database or not
     */
    public boolean removeMovie(final String movieName) {
        /* Going through all movies */
        for (Movie movie : movieDatabase) {
            if (movie.getName().equals(movieName)) {
                movieDatabase.remove(movie);

                /* Notifying all observers */
                Notification notification = new Notification(Constants.Notification.DEL, movie);
                notifyAllObs(notification);

                return true;
            }
        }

        /* The movie was not found */
        return false;
    }

    /**
     * Creates both the user and the movie databases, by building each user and movie individually,
     * then adding them in their respective database
     * All users are considered observers
     * @param userInputs users' input data
     * @param movieInputs movies' input data
     */
    public void createDatabase(final ArrayList<UserInput> userInputs,
                               final ArrayList<MovieInput> movieInputs) {
        for (UserInput userInput : userInputs) {
            User user = new User.UserBuilder(userInput.getCredentials())
                    .build();
            this.putUser(user);

            /* Adds the user as an observer */
            this.addObserver(user);
        }

        for (MovieInput movieInput : movieInputs) {
            Movie movie = new Movie.MovieBuilder(movieInput)
                    .build();
            this.putMovie(movie);
        }
    }
}
