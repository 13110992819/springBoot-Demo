package com.es.elasticsearch;

import com.es.util.ReflectHelper;
import com.es.util.StringUtil;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author chenshan
 * @version v1.0
 * @date 2019/6/26 19:14
 * @since v1.0
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class XbbElasticsearchRestTemplate {
    private static final Logger LOG = LoggerFactory.getLogger(XbbElasticsearchRestTemplate.class);

    @Autowired
    private ElasticsearchProperties properties;
    /**
     * 通用业务主连接
     */
    private RestClient lowLevelRestClient;

    private RestHighLevelClient restHighLevelClient;

    private static final String COLON = ":";
    private static final String COMMA = ",";
    
    @Bean
    public RestClient lowLevelRestClient() throws Exception {
        RestClient lowLevelRestClient = getLowLevelRestClient();
        this.lowLevelRestClient = lowLevelRestClient;
        return lowLevelRestClient;
    }

    /**
     * 同一个类的@Bean是按照上下顺序加载的，所以一定要保证lowLevelRestClient在restHighLevelClient钱
     * @return
     * @throws Exception
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() throws Exception {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());
        this.restHighLevelClient = restHighLevelClient;
        return restHighLevelClient;
    }

    public RestClient getRestClient(String indexName) throws Exception {
            return getNormalLowLevelRestClient();
    }

    /**
     * 获取通用业务主连接
     * @return
     * @throws Exception
     */
    private RestClient getNormalLowLevelRestClient() throws Exception {
        try {
            CloseableHttpAsyncClient closeableHttpAsyncClient = (CloseableHttpAsyncClient) ReflectHelper.valueGet(lowLevelRestClient, "client", false);
            if(lowLevelRestClient==null || closeableHttpAsyncClient==null || ( !closeableHttpAsyncClient.isRunning())){
                RestClient lowLevelRestClient = getLowLevelRestClient();
                this.lowLevelRestClient = lowLevelRestClient;
            }
        } catch (Exception e) {
           throw e;
        }
        return lowLevelRestClient;
    }

    public RestHighLevelClient getRestHighLevelClient(String indexName) throws Exception {
        return getNormalRestHighLevelClient();
    }

    private RestHighLevelClient getNormalRestHighLevelClient() throws Exception {
        RestClient restClient = (RestClient) ReflectHelper.valueGet(restHighLevelClient, "client", true);
        CloseableHttpAsyncClient closeableHttpAsyncClient = (CloseableHttpAsyncClient) ReflectHelper.valueGet(restClient, "client", true);
        if(restHighLevelClient==null || restClient==null || closeableHttpAsyncClient==null || ( !closeableHttpAsyncClient.isRunning())){
            RestHighLevelClient restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());
            this.restHighLevelClient = restHighLevelClient;
        }
        return restHighLevelClient;
    }
    private RestClient getLowLevelRestClient() throws Exception {
        RestClientBuilder restClientBuilder = getRestClientBuilder();
        return restClientBuilder.build();
    }
    private RestClientBuilder getRestClientBuilder() throws Exception {
        String clusterNodes = properties.getClusterNodes();
        
        if ( StringUtil.isEmpty(clusterNodes)) {
            LOG.error("请配置cluster-nodes！");
            throw new Exception(" Need configure cluster-nodes！");
        }
        String[] nodes = StringUtils.delimitedListToStringArray(clusterNodes, COMMA);
        String finalClusterNodes = clusterNodes;
        List<HttpHost> list = Arrays.stream(nodes).map(node -> {
            String[] segments = StringUtils.delimitedListToStringArray(node, COLON);
            Assert.isTrue(segments.length == 2,
                    () -> String.format("Invalid cluster node %s in %s! Must be in the format host:port!", node, finalClusterNodes));
            String host = segments[0].trim();
            String port = segments[1].trim();
            Assert.hasText(host, () -> String.format("No host name given cluster node %s!", node));
            Assert.hasText(port, () -> String.format("No port given in cluster node %s!", node));
//            return new HttpHost(toInetAddress(host), Integer.valueOf(port), "http");
            return new HttpHost(toInetAddress(host), 9200, "http");
        }).collect(Collectors.toList());
        HttpHost[] hostArray = new HttpHost[list.size()];
        RestClientBuilder restClientBuilder = RestClient.builder(list.toArray(hostArray));
        //连接时间   参照 AbstractHttpTransport
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.
                //10000
                setConnectTimeout(60000)
                //10000
                .setConnectionRequestTimeout(60000)
                //600000
                .setSocketTimeout(60000))
        ;
        //Xpack
        if (properties.getXpack() != null && StringUtil.isNotEmpty(properties.getXpack().getUser()) && StringUtil.isNotEmpty(properties.getXpack().getPassword())) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(properties.getXpack().getUser(), properties.getXpack().getPassword()));
            restClientBuilder .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                    //是整个连接池的大小   AbstractHttpTransport  1000 （默认30，暂时设置成数据库连接池一致50）
                    .setMaxConnTotal(10000)
                    //是单个路由连接的最大数  AbstractHttpTransport  500
//                    .setMaxConnPerRoute(500)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultIOReactorConfig(
                            IOReactorConfig.custom()
                                    .build()));
        }

        return restClientBuilder;
    }
    

    private static InetAddress toInetAddress(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 查询日志默认关闭
     */
    public boolean getPrintLog (){
        String booleanStr = "true";
        if(StringUtil.isEmpty(booleanStr)){
            return  false;
        }
        try {
            Boolean printEsLog = Boolean.valueOf(booleanStr);
            return  printEsLog;
        } catch (Exception e) {
            LOG.error("",e);
            return false;
        }
    }


    private void printEsInfo(SearchRequest searchRequest) {
        if(!getPrintLog()){
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n GET  " );
        stringBuilder.append(searchRequest.indices()[0]);
        stringBuilder.append("/");
        stringBuilder.append(searchRequest.types()[0]);
        stringBuilder.append("/_search\n" );
        stringBuilder.append(searchRequest.source());
        LOG.info(stringBuilder.toString());
    }

}
