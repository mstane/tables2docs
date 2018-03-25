package sm.t2d.transformer.inputconfig;

import java.util.List;

public class Column {
    private String inputName;
    private String outputName;
    private List<ValueMapping> valueMappings;

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public List<ValueMapping> getValueMappings() {
        return valueMappings;
    }

    public void setValueMappings(List<ValueMapping> valueMappings) {
        this.valueMappings = valueMappings;
    }
}
