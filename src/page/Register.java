package page;

import constants.Constants;
import verifier.Verifier;

import java.util.ArrayList;

public final class Register extends Page implements Accepter {
    public Register() {
        this.pageFeatures = new ArrayList<>();
        this.pageConnections = new ArrayList<>();

        this.pageConnections.add(Constants.Page.AUTH);
        this.pageConnections.add(Constants.Page.REGISTER);
        this.pageConnections.add(Constants.Page.UNAUTH);

        this.pageFeatures.add(Constants.Feature.REGISTER);
    }

    @Override
    public boolean accept(final Verifier verifier, final String string) {
        return verifier.verify(this, string);
    }
}
