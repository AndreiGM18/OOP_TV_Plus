package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class AuthenticatedPage extends Page implements Accepter {
    public AuthenticatedPage() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.AUTH);
        this.pageConnections.add(Constants.Page.MOVIES);
        this.pageConnections.add(Constants.Page.UPGRADES);
        this.pageConnections.add(Constants.Page.LOGOUT);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
