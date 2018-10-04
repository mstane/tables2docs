package sm.t2d.transdata;

public final class Column {
    private final String inputName;
    private final String outputName;

    public Column(String inputName, String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;

        Column column = (Column) o;

        if (inputName != null ? !inputName.equals(column.inputName) : column.inputName != null) return false;
        return outputName != null ? outputName.equals(column.outputName) : column.outputName == null;
    }

    @Override
    public int hashCode() {
        int result = inputName != null ? inputName.hashCode() : 0;
        result = 31 * result + (outputName != null ? outputName.hashCode() : 0);
        return result;
    }

    public String getInputName() {
        return inputName;
    }

    public String getOutputName() {
        return outputName;
    }
}
