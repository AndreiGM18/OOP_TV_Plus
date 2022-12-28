package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class Upgrades extends Page implements Accepter {
    public Upgrades() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.AUTH);
        this.pageConnections.add(Constants.Page.MOVIES);
        this.pageConnections.add(Constants.Page.UPGRADES);

        this.pageFeatures.add(Constants.Feature.BUY_PREMIUM);
        this.pageFeatures.add(Constants.Feature.BUY_TOKENS);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
