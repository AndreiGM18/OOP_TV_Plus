package application.sortstrategy;

import implementation.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class SortStrategy {
    /**
     * Sorts the list based on what type of strategy it is
     * @param currentMoviesList
     */
    public abstract void sort(ArrayList<Movie> currentMoviesList);
}

final class DurationIncRatingInc extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getDuration() == o2.getDuration()) {
                    if (o1.getRating() >= o2.getRating()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDuration() >= o2.getDuration()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }
}

final class DurationIncRatingDec extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getDuration() == o2.getDuration()) {
                    if (o1.getRating() <= o2.getRating()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDuration() >= o2.getDuration()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }
}

final class DurationDecRatingInc extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getDuration() == o2.getDuration()) {
                    if (o1.getRating() >= o2.getRating()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDuration() <= o2.getDuration()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }
}

final class DurationDecRatingDec extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getDuration() == o2.getDuration()) {
                    if (o1.getRating() <= o2.getRating()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (o1.getDuration() <= o2.getDuration()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }
}

final class RatingInc extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getRating() >= o2.getRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}

final class RatingDec extends SortStrategy {
    @Override
    public void sort(final ArrayList<Movie> currentMoviesList) {
        currentMoviesList.sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getRating() >= o2.getRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
