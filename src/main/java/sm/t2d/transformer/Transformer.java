package sm.t2d.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sm.t2d.transformer.inputconfig.InputConfig;

import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;


public class Transformer {

    private static final Logger logger = LoggerFactory.getLogger(Transformer.class);

    private final InputConfig inputConfig;

    public Transformer(InputConfig inputConfig) {
        this.inputConfig = inputConfig;
    }

    public void execute(
            Collection<String> keys,
            Function<Map<String, Document>, Integer> processChunkFunction,
            DataSource dataSource
    ) {
        if (processChunkFunction == null)
            throw new IllegalArgumentException("ProcessChunkFunction is null");
        if (dataSource == null)
            throw new IllegalArgumentException("DataSource is null");

    }


}
