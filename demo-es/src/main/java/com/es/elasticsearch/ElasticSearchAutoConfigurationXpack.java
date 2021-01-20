package com.es.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lease.Releasable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 *  创建包含XPack验证的client
 * @author 梁鲁江
 * @since
 * @version
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticSearchAutoConfigurationXpack implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchAutoConfigurationXpack.class);

    @Autowired
    private ElasticsearchProperties properties;

    private Releasable releasable;
    @Bean
    public TransportClient transportClient() {
        return null;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(){
        return null;
    }

//    @Bean
//    public TransportClient transportClient() {
//        TransportClient client = null;
//        if (StringUtil.isNotEmpty(properties.getClusterName()) && StringUtil.isNotEmpty(properties.getClusterNodes())) {
//            Settings.Builder builder = Settings.builder();
//            builder.put("cluster.name", properties.getClusterName());
//            // 创建client
//            ClusterNodes clusterNodes = ClusterNodes.of(properties.getClusterNodes());
//            List<InetSocketTransportAddress> clusterNodesList = clusterNodes.getClusterNodes();
//            TransportAddress[] transportAddresses = new TransportAddress[clusterNodesList.size()];
//            clusterNodesList.toArray(transportAddresses);
//            if (properties.getXpack() != null && StringUtil.isNotEmpty(properties.getXpack().getUser()) && StringUtil.isNotEmpty(properties.getXpack().getPassword())) {
//                builder.put("xpack.security.user", properties.getXpack().getUser() + ":" + properties.getXpack().getPassword());
//                client = new PreBuiltXPackTransportClient(builder.build()).addTransportAddresses(transportAddresses);
//            }else{
//                client = new PreBuiltXPackTransportClient(builder.build()).addTransportAddresses(transportAddresses);
//            }
//            releasable = client;
//        }
//        return client;
//    }

    @Override
    public void destroy() throws Exception {
//       if (this.releasable != null) {
//            try {
//                if (logger.isInfoEnabled()) {
//                    logger.info("Closing Elasticsearch client");
//                }
//                try {
//                    this.releasable.close();
//                } catch (NoSuchMethodError ex) {
//                    // Earlier versions of Elasticsearch had a different method
//                    // name
//                    ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(Releasable.class, "release"), this.releasable);
//                }
//            } catch (final Exception ex) {
//                if (logger.isErrorEnabled()) {
//                    logger.error("Error closing Elasticsearch client: ", ex);
//                }
//            }
//        }
    }
}
