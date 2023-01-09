package notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.Constants;
import implementation.Movie;
import implementation.User;

import java.util.ArrayList;

public class Notification {
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

    public static ObjectNode createObjectNode(Notification notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        if (notification.getMovie() == null)
            objectNode.put("movieName", "No recommendation");
        else
            objectNode.put("movieName", notification.getMovie().getName());

        objectNode.put("message", notification.getMessage());

        return objectNode;
    }

    public static ArrayNode createArrayNode(ArrayList<Notification> notifications) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();

        for (Notification notification : notifications) {
            arrayNode.add(Notification.createObjectNode(notification));
        }

        return arrayNode;
    }
}
