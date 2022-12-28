package verifier;

import page.AuthenticatedPage;
import page.Login;
import page.Logout;
import page.Movies;
import page.Register;
import page.SeeDetails;
import page.UnauthenticatedPage;
import page.Upgrades;

public final class VerifierChangePage implements Verifier {
    @Override
    public boolean verify(final AuthenticatedPage authenticatedPage, final String string) {
        if (authenticatedPage.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Login login, final String string) {
        if (login.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Logout logout, final String string) {
        if (logout.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Movies movies, final String string) {
        if (movies.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Register register, final String string) {
        if (register.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final SeeDetails seeDetails, final String string) {
        if (seeDetails.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final UnauthenticatedPage unauthenticatedPage, final String string) {
        if (unauthenticatedPage.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean verify(final Upgrades upgrades, final String string) {
        if (upgrades.getPageConnections().contains(string)) {
            return true;
        }

        return false;
    }
}
