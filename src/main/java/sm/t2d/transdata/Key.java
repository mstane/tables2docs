package sm.t2d.transdata;

import org.apache.commons.lang3.StringUtils;

public final class Key {

    private static final String COMMA = ",";

    private final String value;

    private Key(Object... values) {
        String v = "";
        for(Object keyValue: values) {
            if (StringUtils.isNotEmpty(v))
                v += COMMA;
            v += String.valueOf(keyValue);
        }
        this.value = values.length > 1 ? "(" + v + ")" : v;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Key of(Object... values) {
        return new Key(values);
    }

}
