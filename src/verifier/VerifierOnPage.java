package verifier;

import page.AuthenticatedPage;
import page.Login;
import page.Logout;
import page.Movies;
import page.Register;
import page.SeeDetails;
import page.UnauthenticatedPage;
import page.Upgrades;

public final class VerifierOnPage implements Verifier {
    @Override
    public boolean verify(final AuthenticatedPage authenticatedPage, final String string) {
        if (authenticatedPage.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Login login, final String string) {
        if (login.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Logout logout, final String string) {
        if (logout.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Movies movies, final String string) {
        if (movies.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Register register, final String string) {
        if (register.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final SeeDetails seeDetails, final String string) {
        if (seeDetails.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final UnauthenticatedPage unauthenticatedPage, final String string) {
        if (unauthenticatedPage.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Upgrades upgrades, final String string) {
        if (upgrades.getPageFeatures().contains(string)) {
            return true;
        }

        return false;
    }
}
