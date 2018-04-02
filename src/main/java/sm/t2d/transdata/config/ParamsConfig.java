package sm.t2d.transdata.config;

public class ParamsConfig {
    private int rowsFetchSize;
    private int chunkListSize;
    private int numberOfKeysPerQuery;

    public int getRowsFetchSize() {
        return rowsFetchSize;
    }

    public void setRowsFetchSize(int rowsFetchSize) {
        this.rowsFetchSize = rowsFetchSize;
    }

    public int getChunkListSize() {
        return chunkListSize;
    }

    public void setChunkListSize(int chunkListSize) {
        this.chunkListSize = chunkListSize;
    }

    public int getNumberOfKeysPerQuery() {
        return numberOfKeysPerQuery;
    }

    public void setNumberOfKeysPerQuery(int numberOfKeysPerQuery) {
        this.numberOfKeysPerQuery = numberOfKeysPerQuery;
    }
}
