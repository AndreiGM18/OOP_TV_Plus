package page;

import java.util.ArrayList;

public abstract class Page implements Accepter {
    protected ArrayList<String> pageConnections;
    protected ArrayList<String> pageFeatures;

    /**
     * @return the pages that can be reached from this page
     */
    public ArrayList<String> getPageConnections() {
        return pageConnections;
    }

    /**
     *
     * @return the features that can be done on this page
     */
    public ArrayList<String> getPageFeatures() {
        return pageFeatures;
    }
}
