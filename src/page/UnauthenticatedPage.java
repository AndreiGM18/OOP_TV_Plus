package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class UnauthenticatedPage extends Page implements Accepter {
    public UnauthenticatedPage() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.LOGIN);
        this.pageConnections.add(Constants.Page.REGISTER);
        this.pageConnections.add(Constants.Page.UNAUTH);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
