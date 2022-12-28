package fileio;

import java.util.ArrayList;

public final class Input {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionInput> actions;
    public Input() {
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public ArrayList<ActionInput> getActions() {
        return actions;
    }
}
