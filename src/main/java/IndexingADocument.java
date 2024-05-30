
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.cat.IndicesRequest;
import org.opensearch.client.opensearch.cat.IndicesResponse;
import org.opensearch.client.opensearch.cat.indices.IndicesRecord;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;


public class IndexingADocument {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String INDEX_NAME = "sample-index";

    static final HttpHost[] hosts = new HttpHost[]{
            new HttpHost("http", "localhost", 9200)
    };

    public static void main(String[] args) {

        boolean indexCreatedFlag = false;

        OpenSearchTransport transport = ApacheHttpClient5TransportBuilder
                .builder(hosts)
                .setMapper(new JacksonJsonpMapper())
                .build();
        OpenSearchClient client = new OpenSearchClient(transport);

        try {
            LOG.info("Does the client respond to a ping request? " + client.ping().value());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        IndicesRequest indicesRequest = new IndicesRequest.Builder()
                .headers("index,health,status,pri,rep,doc.count,creation.date,creation.date.string").sort("creation.date").build();
        try {
            IndicesResponse indicesResponse = client.cat().indices(indicesRequest);
            List<IndicesRecord> lr = indicesResponse.valueBody();
            for (IndicesRecord ir : lr) {
                LOG.info(ir.index());
                if (ir.index().contentEquals(INDEX_NAME)) {
                    LOG.debug("The sample index (%s) has already been created".formatted(INDEX_NAME));
                    indexCreatedFlag = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!indexCreatedFlag) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest.Builder().index(INDEX_NAME).build();
            try {
                client.indices().create(createIndexRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        /*
        IndexData indexData = new IndexData("first_name", "Bruce");
        IndexRequest<IndexData> indexRequest = new IndexRequest.Builder<IndexData>().index(index).id("1").document(indexData).build();
        client.index(indexRequest);
        */

    }
}
