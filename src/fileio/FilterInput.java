package fileio;

public final class FilterInput {
    private SortInput sort;
    private ContainsInput contains;

    public FilterInput() {
    }

    public SortInput getSort() {
        return sort;
    }

    public ContainsInput getContains() {
        return contains;
    }
}
