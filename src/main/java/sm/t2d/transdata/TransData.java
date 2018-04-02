package sm.t2d.transdata;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import sm.t2d.transdata.config.InputConfig;

import java.io.IOException;
import java.io.InputStream;

public final class TransData {
    private final Entity mappings;
    private final Params params;

    public TransData(Entity mappings, Params params) {
        this.mappings = mappings;
        this.params = params;
    }

    public Entity getMappings() {
        return mappings;
    }

    public Params getParams() {
        return params;
    }

    public static class Builder {

        private final String fileName;

        private final ObjectMapper objectMapper = new ObjectMapper();

        public Builder(String fileName) {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.fileName = fileName;
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
            return null;

        }

        public TransData build() throws IOException {
            InputConfig inputConfig = loadConfig();
            return compile(inputConfig);
        }

    }



}
