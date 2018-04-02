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
