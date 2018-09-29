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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity entity = (Entity) o;

        if (root != entity.root) return false;
        if (inputTableName != null ? !inputTableName.equals(entity.inputTableName) : entity.inputTableName != null)
            return false;
        if (inputTableKey != null ? !inputTableKey.equals(entity.inputTableKey) : entity.inputTableKey != null)
            return false;
        if (columns != null ? !columns.equals(entity.columns) : entity.columns != null) return false;
        if (nested != null ? !nested.equals(entity.nested) : entity.nested != null) return false;
        if (query != null ? !query.equals(entity.query) : entity.query != null) return false;
        if (docAttributePath != null ? !docAttributePath.equals(entity.docAttributePath) : entity.docAttributePath != null)
            return false;
        return keyTableFieldNamesPath != null ? keyTableFieldNamesPath.equals(entity.keyTableFieldNamesPath) : entity.keyTableFieldNamesPath == null;
    }

    @Override
    public int hashCode() {
        int result = inputTableName != null ? inputTableName.hashCode() : 0;
        result = 31 * result + (inputTableKey != null ? inputTableKey.hashCode() : 0);
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        result = 31 * result + (nested != null ? nested.hashCode() : 0);
        result = 31 * result + (root ? 1 : 0);
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + (docAttributePath != null ? docAttributePath.hashCode() : 0);
        result = 31 * result + (keyTableFieldNamesPath != null ? keyTableFieldNamesPath.hashCode() : 0);
        return result;
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
