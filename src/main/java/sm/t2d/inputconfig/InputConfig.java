package sm.t2d.inputconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;

public class InputConfig {

    private Entity mappings;
    private Params params;

    public Entity getMappings() {
        return mappings;
    }

    public void setMappings(Entity mappings) {
        this.mappings = mappings;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public static class Builder {

        private final String fileName;

        private final ObjectMapper objectMapper = new ObjectMapper();

        public Builder(String fileName) {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            this.fileName = fileName;
        }

        public InputConfig build() throws IOException {
            InputConfig inputConfig = loadConfig();
            compile(inputConfig);

            return inputConfig;
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

        private void compile(InputConfig inputConfig) {

        }


    }

}
