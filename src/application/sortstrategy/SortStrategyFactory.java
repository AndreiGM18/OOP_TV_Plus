package application.sortstrategy;

import constants.Constants;
import fileio.SortInput;

public final class SortStrategyFactory {
    private SortStrategyFactory() {
    }

    /**
     * Creates a strategy that sorts the currentMoviesList in different ways
     * @param sortInput how the sorting should be done
     * @return
     */
    public static SortStrategy createStrat(final SortInput sortInput) {
        /* The duration field may be null */
        if (sortInput.getDuration() == null) {
            switch (sortInput.getRating()) {
                case Constants.Action.Filter.INC -> {
                    return new RatingInc();
                }
                case Constants.Action.Filter.DEC -> {
                    return new RatingDec();
                }
                default -> {
                    return null;
                }
            }
        }

        switch (sortInput.getDuration()) {
            case Constants.Action.Filter.INC -> {
                switch (sortInput.getRating()) {
                    case Constants.Action.Filter.INC -> {
                        return new DurationIncRatingInc();
                    }
                    case Constants.Action.Filter.DEC -> {
                        return new DurationIncRatingDec();
                    }
                    default -> {
                        return null;
                    }
                }
            }

            case Constants.Action.Filter.DEC -> {
                switch (sortInput.getRating()) {
                    case Constants.Action.Filter.INC -> {
                        return new DurationDecRatingInc();
                    }
                    case Constants.Action.Filter.DEC -> {
                        return new DurationDecRatingDec();
                    }
                    default -> {
                        return null;
                    }
                }
            }

            default -> {
                return null;
            }
        }
    }
}
