package sm.t2d.transformer;

import com.google.common.collect.Lists;
import sm.t2d.transdata.Key;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class TransformerUtils {

    public static List<String> stringifyKeys(List<Key> keyList, int partitionSize) {
        return Lists.partition(keyList, partitionSize).stream()
                .map(l -> joinKeys(l))
                .collect(Collectors.toList());
    }

    private static String joinKeys(List<Key> keyList) {
        return keyList.stream()
                .map(k -> k.toString())
                .collect(Collectors.joining(",", "(", ")"));
    }

    public static void query(DataSource dataSource, int fetchSize, String sql, SqlConsumer consumer) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.setFetchSize(fetchSize);
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        consumer.accept(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
