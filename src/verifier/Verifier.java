package verifier;

import page.AuthenticatedPage;
import page.Login;
import page.Logout;
import page.Movies;
import page.Register;
import page.SeeDetails;
import page.UnauthenticatedPage;
import page.Upgrades;

/**
 * Visitor
 */
public interface Verifier {
    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param authenticatedPage
     * @param string
     * @return
     */
    boolean verify(AuthenticatedPage authenticatedPage, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param login
     * @param string
     * @return
     */
    boolean verify(Login login, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param logout
     * @param string
     * @return
     */
    boolean verify(Logout logout, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param movies
     * @param string
     * @return
     */
    boolean verify(Movies movies, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param register
     * @param string
     * @return
     */
    boolean verify(Register register, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param seeDetails
     * @param string
     * @return
     */
    boolean verify(SeeDetails seeDetails, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param unauthenticatedPage
     * @param string
     * @return
     */
    boolean verify(UnauthenticatedPage unauthenticatedPage, String string);

    /**
     * Visits the page and checks if the given String is included in the connections or features
     * The string is either another page or a feature
     * @param upgrades
     * @param string
     * @return
     */
    boolean verify(Upgrades upgrades, String string);
}
