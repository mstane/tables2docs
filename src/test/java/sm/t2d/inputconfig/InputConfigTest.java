package sm.t2d.inputconfig;

import org.junit.Assert;
import org.junit.Test;


public class InputConfigTest {

    @Test
    public void testLoadConfigObject() throws Exception {
        InputConfig inputConfig = new InputConfig.Builder("input-config/input-config.json").build();
        Assert.assertEquals("inputTableName", inputConfig.getMappings().getInputTableName());
    }

}
