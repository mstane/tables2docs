package sm.t2d.transformer.inputconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class InputConfigTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Test
    public void testLoadConfigObject() {
        ClassLoader classLoader = InputConfigTest.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("input-config/input-config.json")) {
            if (inputStream != null) {
                InputConfig inputConfig = objectMapper.readValue(inputStream, InputConfig.class);
                Assert.assertEquals("inputTableName", inputConfig.getMappings().getInputTableName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
