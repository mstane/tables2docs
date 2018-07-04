package sm.t2d.transformer;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlConsumer {
    void accept(ResultSet resultSet) throws SQLException;

}
