package sm.t2d.transdata;

public final class Params {
    private final int rowsFetchSize;
    private final int chunkListSize;
    private final int numberOfKeysPerQuery;

    public Params(int rowsFetchSize, int chunkListSize, int numberOfKeysPerQuery) {
        this.rowsFetchSize = rowsFetchSize;
        this.chunkListSize = chunkListSize;
        this.numberOfKeysPerQuery = numberOfKeysPerQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Params)) return false;

        Params params = (Params) o;

        if (rowsFetchSize != params.rowsFetchSize) return false;
        if (chunkListSize != params.chunkListSize) return false;
        return numberOfKeysPerQuery == params.numberOfKeysPerQuery;
    }

    @Override
    public int hashCode() {
        int result = rowsFetchSize;
        result = 31 * result + chunkListSize;
        result = 31 * result + numberOfKeysPerQuery;
        return result;
    }

    public int getRowsFetchSize() {
        return rowsFetchSize;
    }

    public int getChunkListSize() {
        return chunkListSize;
    }

    public int getNumberOfKeysPerQuery() {
        return numberOfKeysPerQuery;
    }
}
