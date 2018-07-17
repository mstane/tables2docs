package sm.t2d.outbound.mongo;

import sm.t2d.transformer.Document;

import java.util.Map;

public interface Outbound {

    int process(Map<String, Document> documents);

}
