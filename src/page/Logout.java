package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class Logout extends Page implements Accepter {
    public Logout() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.LOGOUT);
        this.pageConnections.add(Constants.Page.UNAUTH);

        this.pageFeatures.add(Constants.Feature.LOGOUT);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
