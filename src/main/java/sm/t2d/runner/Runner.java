package sm.t2d.runner;

import sm.t2d.outbound.mongo.Outbound;
import sm.t2d.query.QueryLoader;
import sm.t2d.transdata.Key;
import sm.t2d.transdata.TransData;
import sm.t2d.transformer.Transformer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Set;

/*
 - Outbound plugins: MongoDB, http response, file (stream)
 - QueryLoader: id list, query
 - Runners: batch, web


 - crete datasource
 - initialize Outbound plugin
 - load id list (via some query strings too, for start only per root table fields??) - QueryLoader
   - id:1,2,3
   - query:name='john'
   - query:age>20&date1<date2
 - start loading TransData (with input-config)
 - implement processChunkFunction (in Transformer) and call Outbound plugin for storing data


 */
public class Runner {

    private static QueryLoader queryLoader;
    private static Outbound outbound;
    private static Transformer transformer;

    public static void main(String[] args) throws IOException {
        init();
        process();
    }

    private static void init() throws IOException {
        DataSource dataSource = getDataSource();
        outbound = getOutbound();
        queryLoader = getQueryLoader();
        TransData transData = new TransData.Builder("input-config.json").build();
        transformer = new Transformer(dataSource, transData);
    }

    private static void process() {
        Set<Key> keys = queryLoader.process("1,2,3");
        transformer.execute(keys, outbound::process);
    }

    private static QueryLoader getQueryLoader() {
        return null;
    }

    private static Outbound getOutbound() {
        return null;
    }

    private static DataSource getDataSource() {
        return null;
    }

}
