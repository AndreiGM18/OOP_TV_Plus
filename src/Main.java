import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import database.Database;
import fileio.ActionInput;
import fileio.Input;
import implementation.Movie;
import application.App;
import constants.Constants;
import verifier.VerifierChangePage;
import verifier.VerifierOnPage;
import implementation.User;

public final class Main {
    private Main() {
    }

    /**
     * Adds an ObjectNode to the output ArrayNode
     * It is used for successful actions, as well as for errors
     * @param output the ArrayNode that is to be written in the result file
     * @param app the current app session
     * @param error signifies whether an error has occurred or not
     */
    private static void createOut(final ArrayNode output, final App app, final String error) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        if (error == null) {
            node.set(Constants.Output.ERR, null);
            node.set(Constants.Output.CURR_MOVIES,
                    Movie.createMoviesArrayNode(app.getCurrentMoviesList()));
            node.set(Constants.Output.CURR_USER, app.getCurrentUser().createObjectNode());
        } else {
            node.put(Constants.Output.ERR, error);
            node.putPOJO(Constants.Output.CURR_MOVIES, new ArrayList<>());
            node.set(Constants.Output.CURR_USER, null);
        }

        output.add(node);
    }

    /**
     * Sets up the app session for the current test
     * Clears the database and the app session
     * Recreates the database, based on the test's input
     * @param database holds all users and movies
     * @param app the current app session
     * @param input
     */
    private static void setup(final Database database, final App app, final Input input) {
        database.clearDatabase();
        database.createDatabase(input.getUsers(), input.getMovies());
        app.clearApp();
    }

    /**
     * Handles all actions that changes the current page
     * @param database holds all users and movies
     * @param app the current app session
     * @param action the given action to perform
     * @param output the ArrayNode that is to be written in the result file
     */
    private static void changePageHandler(final Database database, final App app,
                                          final ActionInput action, final ArrayNode output) {
        /* Verifies if the given page can be reached from the current page */
        if (!app.getCurrentPage().accept(new VerifierChangePage(), action.getPage())) {
            Main.createOut(output, app, Constants.Output.ERROR);
        } else {
            /* Sets the new current page */
            app.setCurrentPage(app.getPages().get(action.getPage()));

            /* The Logout page immediately logs the user out, as such the app session is
             * reset and the current page is set to UnauthenticatedPage
             */
            if (action.getPage().equals(Constants.Page.LOGOUT)) {
                app.clearApp();
                app.setCurrentPage(app.getPages().get(Constants.Page.UNAUTH));
            }

            /* The Movies page always resets the currentMoviesList back to available movies
             * in the user's country and creates an output
             */
            if (action.getPage().equals(Constants.Page.MOVIES)) {
                app.setCurrentMoviesList(new ArrayList<>(database.getMovieDatabase()));
                Main.createOut(output, app, null);
            }

            /* When changing to the SeeDetails page, the movie may not be found due to various
             * reasons. As such, if the action is successful, the currentMoviesList becomes
             * only the movie whose details are being seen. If the action fails, the page is reset
             * back to the Movies page (internally, this helps the currentMoviesList to be reset
             * as well). It also ensures that actions such as purchase or rate cannot be performed.
             * An output is created either way.
             */
            if (action.getPage().equals((Constants.Page.DETAILS))) {
                if (app.getMovie(action.getMovie()) != null) {
                    ArrayList<Movie> currentMovie = new ArrayList<>();
                    currentMovie.add(app.getMovie(action.getMovie()));
                    app.setCurrentMoviesList(new ArrayList<>(currentMovie));
                    Main.createOut(output, app, null);
                } else {
                    Main.createOut(output, app, Constants.Output.ERROR);
                    app.setCurrentPage(app.getPages().get(Constants.Page.MOVIES));
                }
            }
        }
    }

    private static void login(final Database database, final App app, final ActionInput action,
                              final ArrayNode output) {
        /* Tries to get the user from the database */
        User user = database.getUser(action.getCredentials());

        /* If the user is not found, an error output is created
         * If the user is found, the page is switched to the AuthenticatedPage
         * and the user is set as the current one
         */
        if (user == null) {
            Main.createOut(output, app, Constants.Output.ERROR);
            app.setCurrentPage(app.getPages().get(Constants.Page.UNAUTH));
        } else {
            app.setCurrentUser(user);
            app.setCurrentPage(app.getPages().get(Constants.Page.AUTH));
            Main.createOut(output, app, null);
        }
    }

    private static void register(final Database database, final App app, final ActionInput action,
                                 final ArrayNode output) {
        /* Tries to get the user from the database */
        User user = database.getUser(action.getCredentials());

        /* If the user is found, an error output is created
         * If the user is not found, the page is switched to the AuthenticatedPage,
         * the user is added to the database and is set as the current one
         */
        if (user != null) {
            Main.createOut(output, app, Constants.Output.ERROR);
            app.setCurrentPage(app.getPages().get(Constants.Page.UNAUTH));
        } else {
            User newUser = database.addUser(action.getCredentials());
            app.setCurrentUser(newUser);
            app.setCurrentPage(app.getPages().get(Constants.Page.AUTH));
            Main.createOut(output, app, null);
        }
    }

    private static void search(final Database database, final App app, final ActionInput action,
                               final ArrayNode output) {
        /* The currentMoviesList is reset and the app's search method is called */
        app.setCurrentMoviesList(new ArrayList<>(database.getMovieDatabase()));
        app.search(action.getStartsWith());
        Main.createOut(output, app, null);
    }

    private static void filter(final Database database, final App app, final ActionInput action,
                               final ArrayNode output) {
        /* The currentMoviesList is reset and the app's filter method is called */
        app.setCurrentMoviesList(new ArrayList<>(database.getMovieDatabase()));
        app.filter(action.getFilters());
        Main.createOut(output, app, null);
    }

    private static void buyTokens(final App app, final ActionInput action,
                                  final ArrayNode output) {
        /* The current user's buyTokens method is called and based on whether the
         * action is successful or not, an error output may be created
         */
        if (!app.getCurrentUser().buyTokens(action.getCount())) {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    private static void buyPremium(final App app, final ArrayNode output) {
        /* The current user's buyPremium method is called and based on whether the
         * action is successful or not, an error output may be created
         */
        if (!app.getCurrentUser().buyPremium()) {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    private static void purchase(final Movie movie, final App app, final ArrayNode output) {
        /* The current user's purchase method is called, and an output is created based
         * whether the action was successful or not
         */
        if (app.getCurrentUser().purchase(movie)) {
            Main.createOut(output, app, null);
        } else {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    private static void watch(final Movie movie, final App app, final ArrayNode output) {
        /* If the movie has been purchased, it is added in the user's watchedMovies
         * An output is created whether an error has occurred or not
         */
        if (app.getCurrentUser().getPurchasedMovies().contains(movie)) {
            app.getCurrentUser().getWatchedMovies().add(movie);
            Main.createOut(output, app, null);
        } else {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    private static void like(final Movie movie, final App app, final ArrayNode output) {
        /* If the movie has been watched, it is added in the user's likedMovies
         * Then, the movie's number of likes increases
         * An output is created whether an error has occurred or not
         */
        if (app.getCurrentUser().getWatchedMovies().contains(movie)) {
            app.getCurrentUser().getLikedMovies().add(movie);
            movie.setNumLikes(movie.getNumLikes() + 1);
            Main.createOut(output, app, null);
        } else {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    private static void rate(final Movie movie, final App app, final ActionInput action,
                             final ArrayNode output) {
        /* If the movie has been watched, it is added in the user's ratedMovies
         * Then, the movie's number of ratings increases
         * Finally, the movie's new rating is calculated
         * An output is created whether an error has occurred or not
         */
        if (app.getCurrentUser().getWatchedMovies().contains(movie)) {
            if (action.getRate() > Constants.Integers.MAX_RATING) {
                Main.createOut(output, app, Constants.Output.ERROR);
            } else {
                movie.setNumRatings(movie.getNumRatings() + 1);
                movie.setSumRatings(movie.getSumRatings()
                        + action.getRate());
                movie.setRating((double) movie.getSumRatings() / movie.getNumRatings());
                app.getCurrentUser().getRatedMovies().add(movie);
                Main.createOut(output, app, null);
            }
        } else {
            Main.createOut(output, app, Constants.Output.ERROR);
        }
    }

    /**
     * Handles all on-page actions
     * @param database holds all users and movies
     * @param app the current app session
     * @param action the given action to perform
     * @param output the ArrayNode that is to be written in the result file
     */
    private static void onPageHandler(final Database database, final App app,
                                      final ActionInput action, final ArrayNode output) {
        /* Verifies if the given feature can be performed from the current page */
        if (!app.getCurrentPage().accept(new VerifierOnPage(), action.getFeature())) {
            Main.createOut(output, app, Constants.Output.ERROR);
        } else {
            switch (action.getFeature()) {
                case Constants.Feature.LOGIN -> {
                    login(database, app, action, output);
                }

                case Constants.Feature.REGISTER -> {
                    register(database, app, action, output);
                }

                case Constants.Feature.SEARCH -> {
                    search(database, app, action, output);
                }

                case Constants.Feature.FILTER -> {
                    filter(database, app, action, output);
                }

                case Constants.Feature.BUY_TOKENS -> {
                    buyTokens(app, action, output);
                }

                case Constants.Feature.BUY_PREMIUM -> {
                    buyPremium(app, output);
                }

                /* For the following, currentMoviesList only contains one movie */
                case Constants.Feature.PURCHASE -> {
                    purchase(app.getCurrentMoviesList().get(0), app, output);
                }

                case Constants.Feature.WATCH -> {
                    watch(app.getCurrentMoviesList().get(0), app, output);
                }

                case Constants.Feature.LIKE -> {
                    like(app.getCurrentMoviesList().get(0), app, output);
                }

                case Constants.Feature.RATE -> {
                    rate(app.getCurrentMoviesList().get(0), app, action, output);
                }

                /* Default error */
                default -> {
                    Main.createOut(output, app, Constants.Output.ERROR);
                }
            }
        }
    }

    /**
     * Runs every test
     * Uses the database, the app session and the pages in tandem
     * @param args args[0] is the input file's path and args[1] is the output file's path
     */
    public static void main(final String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();

        File in = new File(args[0]);
        File out = new File(args[1]);

        /* Reading the input from the input file */
        Input input;
        try {
            input = objectMapper.readValue(in, Input.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /* Initializing the database and the app session */
        Database database = Database.getDatabase();
        App app = App.getUI();
        setup(database, app, input);

        for (ActionInput action : input.getActions()) {
            switch (action.getType()) {
                case Constants.Action.CHANGE -> {
                    changePageHandler(database, app, action, output);
                }

                case Constants.Action.ON -> {
                    onPageHandler(database, app, action, output);
                }

                /* Default error */
                default -> {
                    Main.createOut(output, app, Constants.Output.ERROR);
                }
            }
        }

        /* Writing the output to the output file */
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectWriter.writeValue(out, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
