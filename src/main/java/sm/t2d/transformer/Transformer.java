package sm.t2d.transformer;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import sm.t2d.transdata.Column;
import sm.t2d.transdata.Entity;
import sm.t2d.transdata.Key;
import sm.t2d.transdata.TransData;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static sm.t2d.transformer.TransformerUtils.*;


public class Transformer {

    private static final Logger logger = LoggerFactory.getLogger(Transformer.class);

    private final TransData transData;
    private DataSource dataSource;

    public Transformer(DataSource dataSource, TransData transData) {
        if (dataSource == null)
            throw new IllegalArgumentException("DataSource is null");
        if (transData == null)
            throw new IllegalArgumentException("TransData is null");

        this.dataSource = dataSource;
        this.transData = transData;
    }

    public void execute(
            Set<Key> keys,
            Function<Map<String, Document>, Integer> processChunkFunction
    ) {
        if (processChunkFunction == null)
            throw new IllegalArgumentException("ProcessChunkFunction is null");

        if (!CollectionUtils.isEmpty(keys)) {
            for (List<Key> keyChunk : Iterables.partition(keys, transData.getParams().getChunkListSize())) {
                executeChunk(keyChunk, processChunkFunction);
            }
        }
    }

    private void executeChunk(List<Key> keyChunk, Function<Map<String, Document>, Integer> processChunkFunction) {
        List<String> stringifiedKeysList = stringifyKeys(keyChunk, transData.getParams().getNumberOfKeysPerQuery());
        for (String stringifiedKeys : stringifiedKeysList) {

            Entity rootEntity = transData.getMappings();
            Map<String, Document> chunkResult = new HashMap<>();

            loadEntities(stringifiedKeys, rootEntity, chunkResult);
            processChunkFunction.apply(chunkResult);

            logger.info("Processed {} items", chunkResult.size());
        }
    }

    private void loadEntities(String stringifiedKeys, Entity entity, Map<String, Document> documentMap) {
        query(
                dataSource,
                transData.getParams().getRowsFetchSize(),
                entity.getQuery() + stringifiedKeys,
                row -> pushInDocumentMap(row, entity, documentMap)

        );

        List<Entity> nestedEnitities = entity.getNested();
        if (!CollectionUtils.isEmpty(nestedEnitities)) {
            for (Entity nestedEntity : nestedEnitities) {
                loadEntities(stringifiedKeys, nestedEntity, documentMap);
            }
        }
    }

    private void pushInDocumentMap(
            ResultSet row,
            Entity entity,
            Map<String, Document> documentMap
    ) throws SQLException {

        Document document = loadEntityFromRow(row, entity);
        List<String> keyTableFieldValuesPath = getKeyTableFieldValuesPath(row, entity);
        List<String> docAttributePath = entity.getDocAttributePath();

        Document parentDocument = null;
        for(int i = 0; i < docAttributePath.size() - 1; i++) {
            if (i == 0) {
                parentDocument = documentMap.get(keyTableFieldValuesPath.get(i));
            } else {
                parentDocument = parentDocument.getDocument(
                        docAttributePath.get(i),
                        keyTableFieldValuesPath.get(i)
                );
            }
        }
        parentDocument.pushDocument(
                docAttributePath.get(docAttributePath.size() - 1),
                keyTableFieldValuesPath.get(docAttributePath.size() - 1),
                document
        );
    }

    private Document loadEntityFromRow(ResultSet row, Entity entity) throws SQLException {
        Document document = new Document();
        for (Column column: entity.getColumns()) {
            Object value = row.getObject(updateColumnName(column, entity));
            if (value != null) {
                if (value instanceof BigDecimal) {
                    BigDecimal valueBigDecimal = (BigDecimal)value;
                    try {
                        document.put(column.getOutputName(), valueBigDecimal.longValueExact());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    document.put(column.getOutputName(), value);
                }
            }
        }
        return document;
    }

    private String updateColumnName(Column column, Entity entity) {
        if (entity.isRoot() && isKeyColumn(column.getInputName(), entity.getInputTableKey())) {
            return entity.getInputTableName() + "_" + column.getInputName();
        }
        return column.getInputName();
    }

    private boolean isKeyColumn(String columnName, List<String> keyColumnNames) {
        return keyColumnNames.stream().anyMatch(n -> n.equals(columnName));
    }

    private List<String> getKeyTableFieldValuesPath(ResultSet row, Entity entity) throws SQLException {
        List<String> keyPathValues = new ArrayList<>();
        List<List<String>> keyNamePath = entity.getKeyTableFieldNamesPath();

        for (int i = 0; i < keyNamePath.size(); i++) {
            keyPathValues.add(composeKeyValue(row, keyNamePath.get(i)));
        }
        return keyPathValues;
    }

    private String composeKeyValue(ResultSet row, List<String> keyNames) throws SQLException {
        int keySize = keyNames.size();
        Object[] keyValues = new Object[keySize];
        for (int i = 0; i < keySize; i++) {
            keyValues[i] = row.getObject(keyNames.get(i));
        }
        return Key.of(keyValues).toString();
    }

}
