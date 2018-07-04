package sm.t2d.transformer;

import org.junit.Test;
import sm.t2d.transdata.Key;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static sm.t2d.transformer.TransformerUtils.stringifyKeys;

public class TransformerUtilsTest {

    @Test
    public void testStringifyOneValueKey() {
        List<Key> keyList = Arrays.asList(
                Key.of(1), Key.of(2), Key.of(3),
                Key.of(4), Key.of(5), Key.of(6),
                Key.of(7), Key.of(8)
        );
        assertThat(stringifyKeys(keyList, 3),
                is(Arrays.asList("(1,2,3)", "(4,5,6)", "(7,8)"))
        );
    }

    @Test
    public void testStringifyOneValueKeyOneElement() {
        List<Key> keyList = Arrays.asList(Key.of(1));
        assertThat(stringifyKeys(keyList, 3),
                is(Arrays.asList("(1)"))
        );
    }

    @Test
    public void testStringifyTwoValuesKey() {
        List<Key> keyList = Arrays.asList(
                Key.of("a", 1), Key.of("a", 2), Key.of("a", 3),
                Key.of("b", 4), Key.of("b", 5), Key.of("b", 6),
                Key.of("c", 7), Key.of("c", 8)
        );
        assertThat(stringifyKeys(keyList, 3),
                is(Arrays.asList("((a,1),(a,2),(a,3))", "((b,4),(b,5),(b,6))", "((c,7),(c,8))"))
        );
    }


}
