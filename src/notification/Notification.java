package notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import implementation.Movie;

import java.util.ArrayList;

public final class Notification {
    private Movie movie;
    private String message;

    public Notification(final String message, final Movie movie) {
        this.message = message;
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Static method that creates an ObjectNode based on the notification's fields
     * @param notification the notification
     * @return ObjectNode
     */
    public static ObjectNode createObjectNode(final Notification notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        /* If the movie is null, then no recommendation was found */
        if (notification.getMovie() == null) {
            objectNode.put(Constants.Notification.MOVIE_NAME, Constants.Notification.NO_REC);
        } else {
            objectNode.put(Constants.Notification.MOVIE_NAME, notification.getMovie().getName());
        }

        objectNode.put(Constants.Notification.MESSAGE, notification.getMessage());

        return objectNode;
    }

    /**
     * Static method that returns an ArrayNode based on an ArrayList
     * @param notifications the notifications list
     * @return ArrayNode
     */
    public static ArrayNode createArrayNode(final ArrayList<Notification> notifications) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (Notification notification : notifications) {
            arrayNode.add(Notification.createObjectNode(notification));
        }

        return arrayNode;
    }
}
