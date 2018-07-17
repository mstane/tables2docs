package sm.t2d.query;

import sm.t2d.transdata.Key;

import java.util.Set;

public interface QueryLoader {

    Set<Key> process(String query);

}
