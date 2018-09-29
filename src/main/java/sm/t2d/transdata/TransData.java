package sm.t2d.transdata;

public final class TransData {
    private final Entity mappings;
    private final Params params;

    public TransData(Entity mappings, Params params) {
        this.mappings = mappings;
        this.params = params;
    }

    public Entity getMappings() {
        return mappings;
    }

    public Params getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransData)) return false;

        TransData transData = (TransData) o;

        if (mappings != null ? !mappings.equals(transData.mappings) : transData.mappings != null) return false;
        return params != null ? params.equals(transData.params) : transData.params == null;
    }

    @Override
    public int hashCode() {
        int result = mappings != null ? mappings.hashCode() : 0;
        result = 31 * result + (params != null ? params.hashCode() : 0);
        return result;
    }

}
