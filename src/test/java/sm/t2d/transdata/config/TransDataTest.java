package sm.t2d.transdata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sm.t2d.transdata.Column;
import sm.t2d.transdata.Entity;
import sm.t2d.transdata.Params;
import sm.t2d.transdata.TransData;
import sm.t2d.transdata.TransDataBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TransDataTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private EmbeddedDatabase db;

    @Before
    public void setUp() throws Exception {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("dataSource")
                .addScript("db/create-film-db.sql")
                .addScript("db/film-data.sql")
                .build();

    }

    @Test
    public void testOneTable() {
        try {

            List<Column> columnList = new ArrayList<>();
            columnList.add(new Column("film_film_id", "film_id"));
            columnList.add(new Column("film_title", "title"));
            columnList.add(new Column("film_description", "description"));
            columnList.add(new Column("film_release_year", "release_year"));

            List<List<String>> keyTableFieldNamesPathList = new ArrayList<>();
            keyTableFieldNamesPathList.add(Collections.singletonList("film_film_id"));

            Entity entity = new Entity
                    ("film",
                            new ArrayList<>(Collections.singletonList("film_id")),
                            columnList,
                            null,
                            true,
                            "SELECT film.film_id AS film_film_id, film.title AS film_title, film.description AS film_description, film.release_year AS film_release_year FROM film WHERE film.film_id IN ",
                            new ArrayList<>(Collections.singletonList("")),
                            keyTableFieldNamesPathList
                    );
            Params params = new Params(10000, 250, 250);
            TransData transDataExpected = new TransData(entity, params);

            TransData transData = new TransDataBuilder("input-config/one-table.json").build();

            Assert.assertTrue(transDataExpected.equals(transData));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTwoTablesInRow() {
        try {
            List<Column> columnListRoot = new ArrayList<>();
            columnListRoot.add(new Column("film_film_id", "film_id"));
            columnListRoot.add(new Column("film_title", "title"));
            columnListRoot.add(new Column("film_description", "description"));
            columnListRoot.add(new Column("film_release_year", "release_year"));
            columnListRoot.add(new Column("film_language_id", "language_id"));

            List<Column> columnListChild = new ArrayList<>();
            columnListChild.add(new Column("language_language_id", "language_id"));
            columnListChild.add(new Column("language_name", "name"));

            List<List<String>> keyTableFieldNamesPathListNested = new ArrayList<>();
            keyTableFieldNamesPathListNested.add(Collections.singletonList("film_film_id"));
            keyTableFieldNamesPathListNested.add(Collections.singletonList("language_language_id"));

            Entity entityChild = new Entity
                    ("language",
                            new ArrayList<>(Collections.singletonList("language_id")),
                            columnListChild,
                            null,
                            false,
                            "SELECT film.film_id AS film_film_id, language.language_id AS language_language_id, language.name AS language_name FROM film,language WHERE film.language_id = language.language_id AND film.film_id IN ",
                            new ArrayList<>(Arrays.asList("", "language")),
                            keyTableFieldNamesPathListNested
                    );
            List<Entity> nestedEntityList = new ArrayList<>();
            nestedEntityList.add(entityChild);

            List<List<String>> keyTableFieldNamesPathList = new ArrayList<>();
            keyTableFieldNamesPathList.add(Collections.singletonList("film_film_id"));

            Entity entity = new Entity
                    ("film",
                            new ArrayList<>(Collections.singletonList("film_id")),
                            columnListRoot,
                            nestedEntityList,
                            true,
                            "SELECT film.film_id AS film_film_id, film.title AS film_title, film.description AS film_description, film.release_year AS film_release_year, film.language_id AS film_language_id FROM film WHERE film.film_id IN ",
                            new ArrayList<>(Collections.singletonList("")),
                            keyTableFieldNamesPathList
                    );
            Params params = new Params(10000, 250, 250);
            TransData transData = new TransData(entity, params);

            TransData transData1 = new TransDataBuilder("input-config/two-tables-in-row.json").build();

            Assert.assertTrue(transData.equals(transData1));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThreeTablesInRow() {
        try {
            String queryRoot = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id FROM film_category WHERE film_category.film_id IN ";
            String queryNestedLevelOne = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id, film.film_id AS film_film_id, film.title AS film_title, film.description AS film_description, film.release_year AS film_release_year, film.language_id AS film_language_id FROM film_category,film WHERE film_category.film_id = film.film_id AND film_category.film_id IN ";
            String queryNestedLevelTwo = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id, film.film_id AS film_film_id, language.language_id AS language_language_id, language.name AS language_name FROM film_category,film,language WHERE film_category.film_id = film.film_id AND film.language_id = language.language_id AND film_category.film_id IN ";
            List<String> docAttributePathListRoot = new ArrayList<>(Collections.singletonList(""));
            List<String> docAttributePathListLevelOne = new ArrayList<>(Arrays.asList("", "film"));
            List<String> docAttributePathListLevelTwo = new ArrayList<>(Arrays.asList("", "film", "language"));
            List<List<String>> keyTableFieldNamesPathListLevelTwo = new ArrayList<>();
            keyTableFieldNamesPathListLevelTwo.add(Arrays.asList("film_category_film_id","film_category_category_id"));
            keyTableFieldNamesPathListLevelTwo.add(Collections.singletonList("film_film_id"));
            keyTableFieldNamesPathListLevelTwo.add(Collections.singletonList("language_language_id"));

            TransData transData = new TransDataBuilder("input-config/three-tables-in-row.json").build();

            Assert.assertTrue(queryRoot.equals(transData.getMappings().getQuery()));
            Assert.assertTrue(queryNestedLevelOne.equals(transData.getMappings().getNested().get(0).getQuery()));
            Assert.assertTrue(queryNestedLevelTwo.equals(transData.getMappings().getNested().get(0).getNested().get(0).getQuery()));
            Assert.assertTrue(docAttributePathListRoot.equals(transData.getMappings().getDocAttributePath()));
            Assert.assertTrue(docAttributePathListLevelOne.equals(transData.getMappings().getNested().get(0).getDocAttributePath()));
            Assert.assertTrue(docAttributePathListLevelTwo.equals(transData.getMappings().getNested().get(0).getNested().get(0).getDocAttributePath()));
            Assert.assertTrue(keyTableFieldNamesPathListLevelTwo.equals(transData.getMappings().getNested().get(0).getNested().get(0).getKeyTableFieldNamesPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTableWithTwoChildren() {
        try {
            String queryRoot = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id FROM film_category WHERE film_category.film_id IN ";
            String queryFirstChild = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id, film.film_id AS film_film_id, film.title AS film_title, film.description AS film_description, film.release_year AS film_release_year, film.language_id AS film_language_id FROM film_category,film WHERE film_category.film_id = film.film_id AND film_category.film_id IN ";
            String querySecondChild = "SELECT film_category.film_id AS film_category_film_id,film_category.category_id AS film_category_category_id, category.category_id AS category_category_id, category.name AS category_name FROM film_category,category WHERE film_category.category_id = category.category_id AND film_category.film_id IN ";
            List<String> docAttributePathListRoot = new ArrayList<>(Collections.singletonList(""));
            List<String> docAttributePathListFirstChild = new ArrayList<>(Arrays.asList("", "film"));
            List<String> docAttributePathListLevelSecondChild = new ArrayList<>(Arrays.asList("", "category"));
            List<List<String>> keyTableFieldNamesPathListFirstChild = new ArrayList<>();
            keyTableFieldNamesPathListFirstChild.add(Arrays.asList("film_category_film_id","film_category_category_id"));
            keyTableFieldNamesPathListFirstChild.add(Collections.singletonList("film_film_id"));
            List<List<String>> keyTableFieldNamesPathListSecondChild = new ArrayList<>();
            keyTableFieldNamesPathListSecondChild.add(Arrays.asList("film_category_film_id","film_category_category_id"));
            keyTableFieldNamesPathListSecondChild.add(Collections.singletonList("category_category_id"));

            TransData transData = new TransDataBuilder("input-config/table-with-two-children.json").build();

            Assert.assertTrue(queryRoot.equals(transData.getMappings().getQuery()));
            Assert.assertTrue(queryFirstChild.equals(transData.getMappings().getNested().get(0).getQuery()));
            Assert.assertTrue(querySecondChild.equals(transData.getMappings().getNested().get(1).getQuery()));
            Assert.assertTrue(docAttributePathListRoot.equals(transData.getMappings().getDocAttributePath()));
            Assert.assertTrue(docAttributePathListFirstChild.equals(transData.getMappings().getNested().get(0).getDocAttributePath()));
            Assert.assertTrue(docAttributePathListLevelSecondChild.equals(transData.getMappings().getNested().get(1).getDocAttributePath()));
            Assert.assertTrue(keyTableFieldNamesPathListFirstChild.equals(transData.getMappings().getNested().get(0).getKeyTableFieldNamesPath()));
            Assert.assertTrue(keyTableFieldNamesPathListSecondChild.equals(transData.getMappings().getNested().get(1).getKeyTableFieldNamesPath()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

}
