package fileio;

public final class ActionInput {
    private String type;
    private String page;
    private String movie;
    private String feature;
    private String subscribedGenre;
    private MovieInput addedMovie;
    private String deletedMovie;
    private CredentialsInput credentials;
    private String startsWith;
    private FilterInput filters;
    private String count;
    private int rate;

    public ActionInput() {
    }

    public String getType() {
        return type;
    }

    public String getPage() {
        return page;
    }

    public String getMovie() {
        return movie;
    }

    public String getFeature() {
        return feature;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public MovieInput getAddedMovie() {
        return addedMovie;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public FilterInput getFilters() {
        return filters;
    }

    public String getCount() {
        return count;
    }

    public int getRate() {
        return rate;
    }
}
