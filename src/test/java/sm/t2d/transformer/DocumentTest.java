package sm.t2d.transformer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentTest {

    @Test
    public void testAddOneSubDocument() {
        Document rootDocument = new Document();
        rootDocument.put("id", 1).put("attribute1", "value1").put("attribute2", "value2");

        Document subDocument = new Document();
        int subDocumentId = 11;
        subDocument.put("subId", subDocumentId).put("attr1", "v1").put("attr2", "v2");

        rootDocument.pushDocument(
                "subDocument",
                String.valueOf(subDocumentId),
                subDocument
        );

        Document retrievedDocument = rootDocument.getDocument(
                "subDocument",
                String.valueOf(subDocumentId)
        );

        assertEquals(subDocument, retrievedDocument);

    }

    @Test
    public void testAddMoreSubDocuments() {
        Document rootDocument = new Document();
        rootDocument.put("id", 1).put("attribute1", "value1").put("attribute2", "value2");

        Document subDocument1 = new Document();
        int subDocumentId1 = 11;
        subDocument1.put("subId", subDocumentId1).put("attr11", "v11").put("attr12", "v12");

        Document subDocument2 = new Document();
        int subDocumentId2 = 22;
        subDocument1.put("subId", subDocumentId2).put("attr21", "v21").put("attr22", "v22");

        Document subDocument3 = new Document();
        int subDocumentId3 = 33;
        subDocument1.put("subId", subDocumentId3).put("attr1", "v31").put("attr2", "v32");


        rootDocument.pushDocument(
                "subDocument",
                String.valueOf(subDocumentId1),
                subDocument1
        );

        rootDocument.pushDocument(
                "subDocument",
                String.valueOf(subDocumentId2),
                subDocument2
        );

        rootDocument.pushDocument(
                "subDocument",
                String.valueOf(subDocumentId3),
                subDocument3
        );

        assertEquals(3, rootDocument.getMap("subDocument").entrySet().size());

    }


}