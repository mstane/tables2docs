package sm.t2d.transformer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Document {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Map<String, Object> data = new HashMap<>();

    public Document() {
    }

    public Document put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void pushDocument(String attributeName, String documentKey, Document document) {
        Map<String, Document> documentMap = getMap(attributeName);
        documentMap.put(documentKey, document);
    }

    public Document getDocument(String attributeName, String documentKey) {
        Map<String, Document> documentMap = (Map<String, Document>)data.get(attributeName);
        if (documentMap != null) {
            return documentMap.get(documentKey);
        }
        return null;
    }

    public Map<String, Document> getMap(String attributeName) {
        return (Map<String, Document>)data.computeIfAbsent(attributeName, v -> new HashMap<>());
    }

    @Override
    public String toString() {
        return gson.toJson(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(data, document.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
