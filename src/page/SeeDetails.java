package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class SeeDetails extends Page implements Accepter {
    public SeeDetails() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.AUTH);
        this.pageConnections.add(Constants.Page.DETAILS);
        this.pageConnections.add(Constants.Page.LOGOUT);
        this.pageConnections.add(Constants.Page.MOVIES);
        this.pageConnections.add(Constants.Page.UPGRADES);

        this.pageFeatures.add(Constants.Feature.PURCHASE);
        this.pageFeatures.add(Constants.Feature.WATCH);
        this.pageFeatures.add(Constants.Feature.LIKE);
        this.pageFeatures.add(Constants.Feature.RATE);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
