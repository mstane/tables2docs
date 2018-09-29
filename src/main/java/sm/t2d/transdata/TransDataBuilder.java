package sm.t2d.transdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import sm.t2d.transdata.config.ColumnConfig;
import sm.t2d.transdata.config.EntityConfig;
import sm.t2d.transdata.config.InputConfig;
import sm.t2d.transdata.config.ParamsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransDataBuilder {

    private static final String AND = " AND ";
    private final String fileName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransDataBuilder(String fileName) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.fileName = fileName;
    }

    public TransData build() throws IOException {
        InputConfig inputConfig = loadConfig();
        return compile(inputConfig);
    }

    private InputConfig loadConfig() throws IOException {
        InputConfig inputConfig = null;
        ClassLoader classLoader = InputConfig.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream != null) {
                inputConfig = objectMapper.readValue(inputStream, InputConfig.class);
            }
        }
        return inputConfig;
    }

    private TransData compile(InputConfig inputConfig) {
        ParamsConfig paramsConfig = inputConfig.getParams();
        return new TransData(
                loadEntity(inputConfig.getMappings(), null, null, null, null, null, null, null, true),
                new Params(
                        paramsConfig.getRowsFetchSize(),
                        paramsConfig.getChunkListSize(),
                        paramsConfig.getNumberOfKeysPerQuery()
                )
        );
    }

    private Entity loadEntity(
            EntityConfig entityConfig,
            List<String> docAttributeParentPathList,
            List<List<String>> keyTableFieldNamesParentPathList,
            String parentTableName,
            String selectPartParentKeys,
            List<String> fromPartParent,
            String wherePartParent,
            String whereRootPartParent,
            boolean isRoot
    ) {

        List<String> docAttributePath = getDocAttributePathList(entityConfig, docAttributeParentPathList, isRoot);

        List<List<String>> keyTableFieldNamesPath =
                getKeyTableFieldNamesPathList(entityConfig, keyTableFieldNamesParentPathList);

        String selectPartKeys = getSelectPartKeys(entityConfig, selectPartParentKeys, isRoot);

        List<String> fromPart = getFromPart(entityConfig, fromPartParent);

        String wherePart = null;
        String whereRootPart = whereRootPartParent;
        if (isRoot) {
            whereRootPart = getWhereRootPart(entityConfig);
        } else {
            wherePart = getWherePart(entityConfig, parentTableName, wherePartParent);
        }

        List<Entity> nestedEntityList = null;
        List<EntityConfig> nestedEntityConfigList = entityConfig.getNested();

        if (!CollectionUtils.isEmpty(nestedEntityConfigList)) {
            nestedEntityList = new ArrayList<>();
            for (EntityConfig nestedEntityConfig : nestedEntityConfigList) {
                nestedEntityList.add(
                        loadEntity(
                                nestedEntityConfig,
                                docAttributePath,
                                keyTableFieldNamesPath,
                                entityConfig.getInputTableName(),
                                selectPartKeys,
                                fromPart,
                                wherePart,
                                whereRootPart,
                                false
                        )
                );
            }
        }

        return new Entity(
                entityConfig.getInputTableName(),
                entityConfig.getInputTableKey(),
                getColumnList(entityConfig),
                nestedEntityList,
                isRoot,
                getQuery(entityConfig, wherePart, whereRootPart, isRoot, selectPartKeys, fromPart),
                docAttributePath,
                keyTableFieldNamesPath
        );
    }

    private List<String> getDocAttributePathList(EntityConfig entityConfig, List<String> docAttributeParentPathList, boolean isRoot) {
        List<String> docAttributePath = new ArrayList<>();
        if (docAttributeParentPathList != null) {
            docAttributePath.addAll(docAttributeParentPathList);
        }
        if (isRoot) {
            docAttributePath.add("");
        } else {
            docAttributePath.add(entityConfig.getOutputTableName());
        }
        return docAttributePath;
    }

    private List<List<String>> getKeyTableFieldNamesPathList(EntityConfig entityConfig, List<List<String>> keyTableFieldNamesParentPathList) {
        List<List<String>> keyTableFieldNamesPathList = new ArrayList<>();
        if (keyTableFieldNamesParentPathList != null) {
            keyTableFieldNamesPathList.addAll(keyTableFieldNamesParentPathList);
        }
        keyTableFieldNamesPathList.add(
                entityConfig.getInputTableKey()
                        .stream()
                        .map(k -> entityConfig.getInputTableName() + "_" + k)
                        .collect(Collectors.toList()));

        return keyTableFieldNamesPathList;
    }

    private List<String> getFromPart(EntityConfig entityConfig, List<String> fromPartParent) {
        List<String> fromPart = new ArrayList<>();
        if (fromPartParent != null) {
            fromPart.addAll(fromPartParent);
        }
        fromPart.add(entityConfig.getInputTableName());
        return fromPart;
    }

    private String getQuery(EntityConfig entityConfig, String wherePart, String whereRootPart, boolean isRoot, String selectPartKeys, List<String> fromPart) {
        return "SELECT " + selectPartKeys + getSelectPartValues(entityConfig)
                + " FROM " + String.join(",", fromPart)
                + " WHERE " + (isRoot ? "" : wherePart + AND) + whereRootPart + " IN ";
    }

    private String getWherePart(EntityConfig entityConfig, String parentTableName, String wherePartParent) {
        return (wherePartParent == null ? "" : wherePartParent + AND) + whereForeignKeysPart(entityConfig, parentTableName);
    }

    private String getWhereRootPart(EntityConfig entityConfig) {
        return entityConfig.getInputTableName() + "." + entityConfig.getInputTableKey().get(0);
    }

    private List<Column> getColumnList(EntityConfig entityConfig) {
        List<Column> columnList = new ArrayList<>();
        for (ColumnConfig columnConfig : entityConfig.getColumns()) {
            columnList.add(
                    new Column(
                            entityConfig.getInputTableName() + "_" + columnConfig.getInputName(),
                            columnConfig.getOutputName()
                    )
            );
        }
        return columnList;
    }

    private String getSelectPartValues(EntityConfig entityConfig) {
        String selectPartValues = "";
        for (ColumnConfig columnConfig : entityConfig.getColumns()) {
            if (!isKeyColumn(entityConfig, columnConfig)) {
                selectPartValues += ", " + entityConfig.getInputTableName()
                        + "." + columnConfig.getInputName() + " AS " + entityConfig.getInputTableName()
                        + "_" + columnConfig.getInputName();
            }
        }
        return selectPartValues;
    }

    private String getSelectPartKeys(EntityConfig entityConfig, String selectPartParentKeys, boolean isRoot) {
        return (isRoot ? "" : selectPartParentKeys + ", ") + getKeysForSelect(entityConfig);
    }

    private String getKeysForSelect(EntityConfig entityConfig) {
        String keysForSelect = "";
        for (String keyColumnName : entityConfig.getInputTableKey()) {
            if (StringUtils.isNotBlank(keysForSelect)) keysForSelect += ",";
            keysForSelect += entityConfig.getInputTableName() + "." + keyColumnName + " AS " + entityConfig.getInputTableName() + "_" + keyColumnName;
        }
        return keysForSelect;
    }

    private boolean isKeyColumn(EntityConfig entityConfig, ColumnConfig columnConfig) {
        return entityConfig.getInputTableKey().stream().anyMatch(c -> c.equals(columnConfig.getInputName()));
    }

    private String whereForeignKeysPart(EntityConfig entityConfig, String parent) {
        String keysForWhere = "";
        for (int i = 0; i < entityConfig.getForeignTableKey().size(); i++) {
            if (StringUtils.isNotBlank(keysForWhere)) keysForWhere += AND;
            keysForWhere += parent + "." + entityConfig.getInputTableKey().get(i) + " = " + entityConfig.getInputTableName() + "." + entityConfig.getForeignTableKey().get(i);
        }
        return keysForWhere;
    }


}
