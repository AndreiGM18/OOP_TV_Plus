package implementation;

import obs.Observer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import fileio.CredentialsInput;
import notification.Notification;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class implements the Builder and Observer design patterns
 */
public final class User implements Observer {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    private ArrayList<Notification> notifications;
    private HashMap<String, Integer> genreNumLikes;
    private ArrayList<String> subscribedGenres;

    private User(final UserBuilder builder) {
        this.credentials = builder.credentials;
        this.tokensCount = builder.tokensCount;
        this.numFreePremiumMovies = builder.numFreePremiumMovies;
        this.purchasedMovies = builder.purchasedMovies;
        this.watchedMovies = builder.watchedMovies;
        this.likedMovies = builder.likedMovies;
        this.ratedMovies = builder.ratedMovies;
        this.notifications = builder.notifications;
        this.genreNumLikes = builder.genreNumLikes;
        this.subscribedGenres = builder.subscribedGenres;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Adds the notification to the user's notifications, if it is relevant to the user
     * In case of the "ADD" notification, if a movie that has a genre that the user subscribed to,
     * the notification is considered relevant.
     * In case of the "DELETE" notification, if a movie was purchased by the user, the notification
     * is considered relevant. The movie is removed from the user's lists and a refund is given.
     * @param o the notification
     */
    @Override
    public void update(final Object o) {
        Notification notification = (Notification) o;

        if (notification.getMessage().equals(Constants.Notification.ADD)) {
            /* If the movie has a subscribed genre, the notification is relevant */
            for (String genre : notification.getMovie().getGenres()) {
                if (this.subscribedGenres.contains(genre)) {
                    this.notifications.add(notification);
                    return;
                }
            }
        } else {
            /* If the movie was purchased, the notification is relevant */
            if (this.purchasedMovies.contains(notification.getMovie())) {
                this.notifications.add(notification);

                /* Removes the movie from all lists */
                this.purchasedMovies.remove(notification.getMovie());
                this.watchedMovies.remove(notification.getMovie());
                this.likedMovies.remove(notification.getMovie());
                this.ratedMovies.remove(notification.getMovie());

                /* Refunds the user */
                if (this.credentials.getAccountType().equals(Constants.User.Credentials.PREMIUM)) {
                    ++this.numFreePremiumMovies;
                } else {
                    this.tokensCount += 2;
                }
            }
        }
    }

    public static final class UserBuilder {
        private Credentials credentials;
        private int tokensCount = 0;
        private int numFreePremiumMovies = Constants.Integers.FREE_MOVIES;
        private ArrayList<Movie> purchasedMovies = new ArrayList<>();
        private ArrayList<Movie> watchedMovies = new ArrayList<>();
        private ArrayList<Movie> likedMovies = new ArrayList<>();
        private ArrayList<Movie> ratedMovies = new ArrayList<>();
        private ArrayList<Notification> notifications = new ArrayList<>();
        private HashMap<String, Integer> genreNumLikes = new HashMap<>();
        private ArrayList<String> subscribedGenres = new ArrayList<>();

        public UserBuilder(final CredentialsInput credentialsGiven) {
            this.credentials = new Credentials(credentialsGiven);
        }

        /**
         * @param tokensCountGiven the number of tokens the user has
         * @return the changed Builder instance
         */
        public UserBuilder tokensCount(final int tokensCountGiven) {
            this.tokensCount = tokensCountGiven;
            return this;
        }

        /**
         * @param numFreePremiumMoviesGiven the number of free movies left (can be decreased only
         *                                  if the account is premium)
         * @return the changed Builder instance
         */
        public UserBuilder numFreePremiumMovies(final int numFreePremiumMoviesGiven) {
            this.numFreePremiumMovies = numFreePremiumMoviesGiven;
            return this;
        }

        /**
         * @param purchasedMoviesGiven the purchased movies list
         * @return the changed Builder instance
         */
        public UserBuilder purchasedMovies(final ArrayList<Movie> purchasedMoviesGiven) {
            this.purchasedMovies = purchasedMoviesGiven;
            return this;
        }

        /**
         * @param watchedMoviesGiven the watched movies list
         * @return the changed Builder instance
         */
        public UserBuilder watchedMovies(final ArrayList<Movie> watchedMoviesGiven) {
            this.watchedMovies = watchedMoviesGiven;
            return this;
        }

        /**
         * @param likedMoviesGiven the liked movies list
         * @return the changed Builder instance
         */
        public UserBuilder likedMovies(final ArrayList<Movie> likedMoviesGiven) {
            this.likedMovies = likedMoviesGiven;
            return this;
        }

        /**
         * @param ratedMoviesGiven the rated movies list
         * @return the changed Builder instance
         */
        public UserBuilder ratedMovies(final ArrayList<Movie> ratedMoviesGiven) {
            this.ratedMovies = ratedMoviesGiven;
            return this;
        }

        /**
         * @param notificationsGiven the notifications
         * @return the changed Builder instance
         */
        public UserBuilder notifications(final ArrayList<Notification> notificationsGiven) {
            this.notifications = notificationsGiven;
            return this;
        }

        /**
         * @param genreNumLikesGiven the number of likes for each genre, in a HashMap
         * @return the changed Builder instance
         */
        public UserBuilder genreNumLikes(final HashMap<String, Integer> genreNumLikesGiven) {
            this.genreNumLikes = genreNumLikesGiven;
            return this;
        }

        /**
         * @param subscribedGenresGiven the genres that the user subscribed to
         * @return the changed Builder instance
         */
        public UserBuilder subscribedGenres(final ArrayList<String> subscribedGenresGiven) {
            this.subscribedGenres = subscribedGenresGiven;
            return this;
        }

        /**
         * @return a User based on everything specified to the Builder
         */
        public User build() {
            return new User(this);
        }
    }

    public HashMap<String, Integer> getGenreNumLikes() {
        return genreNumLikes;
    }

    /**
     * Adds a specified number of tokens, decreasing the user's balance
     * @param count
     * @return true, if successful, or false, otherwise
     */
    public boolean buyTokens(final String count) {
        int cnt = Integer.parseInt(count);
        int bal = Integer.parseInt(credentials.getBalance());

        if (bal >= cnt) {
            bal -= cnt;
            tokensCount += cnt;
            String newBal = String.valueOf(bal);
            credentials.setBalance(newBal);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Turns a standard account into a premium account, decreasing the user's number of tokens
     * @return true, if successful, or false, otherwise
     */
    public boolean buyPremium() {
        if (tokensCount >= Constants.Integers.PREMIUM_ACC_PRICE
                && credentials.getAccountType().equals(Constants.User.Credentials.STANDARD)) {
            tokensCount -= Constants.Integers.PREMIUM_ACC_PRICE;
            credentials.setAccountType(Constants.User.Credentials.PREMIUM);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a movie to the purchasedMovieList, decreasing the user's number of tokens or their
     * number of free movies (based on the account's type)
     * @param movie
     * @return true, if successful, or false, otherwise
     */
    public boolean purchase(final Movie movie) {
        /* Standard users have to spend tokens */
        if (credentials.getAccountType().equals(Constants.User.Credentials.STANDARD)
                && tokensCount >= Constants.Integers.MOVIE_PRICE) {
            tokensCount -= Constants.Integers.MOVIE_PRICE;
            purchasedMovies.add(movie);
            return true;
        }

        /* Premium users can also use up their free movies, before spending tokens */
        if (credentials.getAccountType().equals(Constants.User.Credentials.PREMIUM)) {
            if (numFreePremiumMovies > 0) {
                --numFreePremiumMovies;
                purchasedMovies.add(movie);
                return true;
            } else if (tokensCount >= Constants.Integers.MOVIE_PRICE) {
                tokensCount -= Constants.Integers.MOVIE_PRICE;
                purchasedMovies.add(movie);
                return true;
            }
        }

        return false;
    }

    /**
     * Creates an ObjectNode based on the user's fields
     * @return ObjectNode
     */
    public ObjectNode createObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        ObjectNode credentialsNode = objectMapper.createObjectNode();
        credentialsNode.put(Constants.User.Credentials.NAME, credentials.getName());
        credentialsNode.put(Constants.User.Credentials.PASSWORD, credentials.getPassword());
        credentialsNode.put(Constants.User.Credentials.ACC_TYPE, credentials.getAccountType());
        credentialsNode.put(Constants.User.Credentials.COUNTRY, credentials.getCountry());
        credentialsNode.put(Constants.User.Credentials.BALANCE, credentials.getBalance());

        objectNode.set(Constants.User.CREDENTIALS, credentialsNode);
        objectNode.put(Constants.User.TOKENS_CNT, tokensCount);
        objectNode.put(Constants.User.FREE_MOVIES, numFreePremiumMovies);
        objectNode.set(Constants.User.PURCHASED, Movie.createMoviesArrayNode(purchasedMovies));
        objectNode.set(Constants.User.WATCHED, Movie.createMoviesArrayNode(watchedMovies));
        objectNode.set(Constants.User.LIKED, Movie.createMoviesArrayNode(likedMovies));
        objectNode.set(Constants.User.RATED, Movie.createMoviesArrayNode(ratedMovies));
        objectNode.set(Constants.User.NOTIFS, Notification.createArrayNode(notifications));

        return objectNode;
    }
}
