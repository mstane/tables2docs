package sm.t2d.transdata;

import java.util.List;

public final class Entity {
    private final String inputTableName;
    private final List<String> inputTableKey;
    private final List<Column> columns;
    private final List<Entity> nested;

    private final boolean root;
    private final String query;
    private final List<String> docAttributePath;
    private final List<List<String>> keyTableFieldNamesPath;

    public Entity(String inputTableName, List<String> inputTableKey, List<Column> columns, List<Entity> nested, boolean root, String query, List<String> docAttributePath, List<List<String>> keyTableFieldNamesPath) {
        this.inputTableName = inputTableName;
        this.inputTableKey = inputTableKey;
        this.columns = columns;
        this.nested = nested;
        this.root = root;
        this.query = query;
        this.docAttributePath = docAttributePath;
        this.keyTableFieldNamesPath = keyTableFieldNamesPath;
    }

    public String getInputTableName() {
        return inputTableName;
    }

    public List<String> getInputTableKey() {
        return inputTableKey;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Entity> getNested() {
        return nested;
    }

    public boolean isRoot() {
        return root;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getDocAttributePath() {
        return docAttributePath;
    }

    public List<List<String>> getKeyTableFieldNamesPath() {
        return keyTableFieldNamesPath;
    }
}
