package page;

import verifier.Verifier;

/**
 * Visitable
 */
public interface Accepter {
    /**
     * Accepts a Visitor
     * @param verifier the Visitor
     */
    boolean accept(Verifier verifier, String string);
}
