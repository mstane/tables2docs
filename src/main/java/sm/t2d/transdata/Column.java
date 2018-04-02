package sm.t2d.transdata;

public final class Column {
    private final String inputName;
    private final String outputName;

    public Column(String inputName, String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }

    public String getInputName() {
        return inputName;
    }

    public String getOutputName() {
        return outputName;
    }
}
