package implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import fileio.MovieInput;

import java.util.ArrayList;

/**
 * Class implements the Builder design pattern
 */
public final class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private int sumRatings;

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public double getRating() {
        return rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public int getSumRatings() {
        return sumRatings;
    }

    public Movie(final MovieBuilder builder) {
        this.name = builder.name;
        this.year = builder.year;
        this.duration = builder.duration;
        this.genres = builder.genres;
        this.actors = builder.actors;
        this.countriesBanned = builder.countriesBanned;
        this.numLikes = builder.numLikes;
        this.rating = builder.rating;
        this.numRatings = builder.numRatings;
        this.sumRatings = builder.sumRatings;
    }

    public static final class MovieBuilder {
        private String name;
        private int year;
        private int duration;
        private ArrayList<String> genres;
        private ArrayList<String> actors;
        private ArrayList<String> countriesBanned;
        private int numLikes = 0;
        private double rating = 0;
        private int numRatings = 0;
        private int sumRatings = 0;

        public MovieBuilder(final MovieInput movie) {
            this.name = movie.getName();
            this.year = movie.getYear();
            this.duration = movie.getDuration();
            this.genres = movie.getGenres();
            this.actors = movie.getActors();
            this.countriesBanned = movie.getCountriesBanned();
        }

        /**
         * @param numLikesGiven the number of likes the movie has
         * @return the changed Builder instance
         */
        public MovieBuilder numLikes(final int numLikesGiven) {
            this.numLikes = numLikesGiven;
            return this;
        }

        /**
         * @param ratingGiven the movie's rating
         * @return the changed Builder instance
         */
        public MovieBuilder rating(final double ratingGiven) {
            this.rating = ratingGiven;
            return this;
        }

        /**
         * @param numRatingsGiven the number of ratings the movie has
         * @return the changed Builder instance
         */
        public MovieBuilder numRatings(final int numRatingsGiven) {
            this.numRatings = numRatingsGiven;
            return this;
        }

        /**
         * @param sumRatingsGiven the total rating scores combined
         * @return the changed Builder instance
         */
        public MovieBuilder sumRatings(final int sumRatingsGiven) {
            this.sumRatings = sumRatingsGiven;
            return this;
        }

        /**
         * @return a Movie based on everything specified to the Builder
         */
        public Movie build() {
            return new Movie(this);
        }
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }

    public void setSumRatings(final int sumRatings) {
        this.sumRatings = sumRatings;
    }

    /**
     * Static method that returns an ArrayNode based on an ArrayList
     * @param movies ArrayList
     * @return ArrayNode
     */
    public static ArrayNode createMoviesArrayNode(final ArrayList<Movie> movies) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (Movie movie : movies) {
            arrayNode.add(movie.createObjectNode());
        }

        return arrayNode;
    }

    /**
     * Creates an ObjectNode based on the movie's fields
     * @return ObjectNode
     */
    public ObjectNode createObjectNode() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put(Constants.Movie.NAME, name);
        objectNode.put(Constants.Movie.YEAR, year);
        objectNode.put(Constants.Movie.DURATION, duration);
        objectNode.putPOJO(Constants.Movie.GENRES, genres);
        objectNode.putPOJO(Constants.Movie.ACTORS, actors);
        objectNode.putPOJO(Constants.Movie.COUNTRIES_BANNED, countriesBanned);
        objectNode.put(Constants.Movie.NUM_LIKES, numLikes);
        objectNode.put(Constants.Movie.RATING, rating);
        objectNode.put(Constants.Movie.NUM_RATINGS, numRatings);

        return objectNode;
    }
}
