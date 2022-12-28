package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class Movies extends Page implements Accepter {
    public Movies() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.AUTH);
        this.pageConnections.add(Constants.Page.MOVIES);
        this.pageConnections.add(Constants.Page.DETAILS);
        this.pageConnections.add(Constants.Page.LOGOUT);

        this.pageFeatures.add(Constants.Feature.FILTER);
        this.pageFeatures.add(Constants.Feature.SEARCH);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
