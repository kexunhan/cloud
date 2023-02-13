package cn.kolmap;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.json.SimpleJsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @author kxhan
 * @version 1.0
 * @date 2022/11/29 15:56
 */
public class Test {


    public static void main(String[] args) throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "9dvHHBUYEPdLmG8eg8hY"));

        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200)).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        });
        RestClient restClient = builder.build();




        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(new JsonMapper()));
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest.Builder().index("myindex").build();
        final CreateIndexResponse createIndexResponse = client.indices().create(request);
        System.out.println("创建索引成功：" + createIndexResponse.acknowledged());
        // 查询索引
        GetIndexRequest getIndexRequest = new GetIndexRequest.Builder().index("myindex").build();
        final GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest);
        System.out.println("索引查询成功：" + getIndexResponse.result());
        // 删除索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest.Builder().index("myindex").build();
        final DeleteIndexResponse delete = client.indices().delete(deleteIndexRequest);
        final boolean acknowledged = delete.acknowledged();
        System.out.println("删除索引成功：" + acknowledged);

    }
}
