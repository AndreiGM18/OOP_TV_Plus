package obs;

public interface Observer {
    /**
     * Uses the given object in order to do something internally or externally
     * @param o the given object
     */
    void update(Object o);
}
