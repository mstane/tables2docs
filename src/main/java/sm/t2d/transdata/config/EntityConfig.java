package sm.t2d.transdata.config;

import java.util.List;

public class EntityConfig {
    private String inputTableName;
    private String outputTableName;
    private List<String> inputTableKey;
    private List<String> foreignTableKey;
    private String inputCondition;
    private List<ColumnConfig> columns;
    private List<EntityConfig> nested;

    public String getInputTableName() {
        return inputTableName;
    }

    public void setInputTableName(String inputTableName) {
        this.inputTableName = inputTableName;
    }

    public String getOutputTableName() {
        return outputTableName;
    }

    public void setOutputTableName(String outputTableName) {
        this.outputTableName = outputTableName;
    }

    public List<String> getInputTableKey() {
        return inputTableKey;
    }

    public String[] getInputTableKeyArray() {
        return inputTableKey.toArray(new String[inputTableKey.size()]);
    }

    public void setInputTableKey(List<String> inputTableKey) {
        this.inputTableKey = inputTableKey;
    }

    public List<String> getForeignTableKey() {
        return foreignTableKey;
    }

    public String[] getForeignTableKeyArray() {
        return foreignTableKey.toArray(new String[foreignTableKey.size()]);
    }

    public void setForeignTableKey(List<String> foreignTableKey) {
        this.foreignTableKey = foreignTableKey;
    }

    public String getInputCondition() {
        return inputCondition;
    }

    public void setInputCondition(String inputCondition) {
        this.inputCondition = inputCondition;
    }

    public List<ColumnConfig> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    public List<EntityConfig> getNested() {
        return nested;
    }

    public void setNested(List<EntityConfig> nested) {
        this.nested = nested;
    }

}