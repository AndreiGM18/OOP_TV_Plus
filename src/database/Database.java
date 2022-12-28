package database;

import fileio.CredentialsInput;
import fileio.MovieInput;
import fileio.UserInput;
import implementation.Movie;
import implementation.User;

import java.util.ArrayList;
import java.util.LinkedList;

public final class Database {
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
     * @param credentialsInput
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
     * @param credentialsInput
     * @return the newly created user
     */
    public User addUser(final CredentialsInput credentialsInput) {
        User user = new User.UserBuilder(credentialsInput)
                .build();
        this.putUser(user);

        return user;
    }
    /**
     * Creates both the user and the movie databases, by building each user and movie individually,
     * then adding them in their respective database
     * @param userInputs
     * @param movieInputs
     */
    public void createDatabase(final ArrayList<UserInput> userInputs,
                               final ArrayList<MovieInput> movieInputs) {
        for (UserInput userInput : userInputs) {
            User user = new User.UserBuilder(userInput.getCredentials())
                    .build();
            this.putUser(user);
        }

        for (MovieInput movieInput : movieInputs) {
            Movie movie = new Movie.MovieBuilder(movieInput)
                    .build();
            this.putMovie(movie);
        }
    }
}
