package constants;

/**
 * Constant class - used in order to handle magic numbers and strings
 */
public class Constants {
    public static final class User {
        public static final String CREDENTIALS = "credentials";
        public static class Credentials {
            public static final String NAME = "name";
            public static final String PASSWORD = "password";
            public static final String ACC_TYPE = "accountType";
            public static final String STANDARD = "standard";
            public static final String PREMIUM = "premium";
            public static final String COUNTRY  = "country";
            public static final String BALANCE = "balance";
            }
        public static final String TOKENS_CNT = "tokensCount";
        public static final String FREE_MOVIES = "numFreePremiumMovies";
        public static final String PURCHASED = "purchasedMovies";
        public static final String WATCHED = "watchedMovies";
        public static final String LIKED = "likedMovies";
        public static final String RATED = "ratedMovies";
    }

    public static class Movie {
        public static final String NAME = "name";
        public static final String YEAR = "year";
        public static final String DURATION = "duration";
        public static final String GENRES = "genres";
        public static final String ACTORS = "actors";
        public static final String COUNTRIES_BANNED = "countriesBanned";
        public static final String NUM_LIKES = "numLikes";
        public static final String RATING = "rating";
        public static final String NUM_RATINGS = "numRatings";
    }

    public static class Action {
        public static final String ON = "on page";
        public static final String CHANGE = "change page";
        public static class Filter {
            public static final String INC = "increasing";
            public static final String DEC = "decreasing";
        }
    }

    public static class Output {
        public static final String ERROR = "Error";
        public static final String ERR = "error";
        public static final String CURR_USER = "currentUser";
        public static final String CURR_MOVIES = "currentMoviesList";

    }

    public static class Page {
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String REGISTER = "register";
        public static final String MOVIES = "movies";
        public static final String DETAILS = "see details";
        public static final String UPGRADES = "upgrades";
        public static final String AUTH = "authenticated";
        public static final String UNAUTH = "unauthenticated";
    }

    public static class Integers {
        public static final int FREE_MOVIES = 15;
        public static final int PREMIUM_ACC_PRICE = 10;
        public static final int MAX_RATING = 5;
        public static final int MOVIE_PRICE = 2;
    }

    public static class Feature {
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String REGISTER = "register";
        public static final String SEARCH = "search";
        public static final String FILTER = "filter";
        public static final String PURCHASE = "purchase";
        public static final String WATCH = "watch";
        public static final String LIKE = "like";
        public static final String RATE = "rate";
        public static final String BUY_TOKENS = "buy tokens";
        public static final String BUY_PREMIUM = "buy premium account";
    }
}
