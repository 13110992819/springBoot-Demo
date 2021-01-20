//package com.example.demo.elasticsearch;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Configuration properties for Elasticsearch.
// *添加xpack的处理
// */
//@ConfigurationProperties(prefix = "spring.data.elasticsearch")
//@ToString
//@Getter
//@Setter
//public class ElasticsearchProperties {
//    private XPack xpack;
//    /**
//     * Elasticsearch cluster name.
//     */
//    private String clusterName = "elasticsearch";
//
//    /**
//     * Comma-separated list of cluster node addresses.
//     */
//    private String clusterNodes;
//
//    /**
//     * Additional properties used to configure the client.
//     */
//    private Map<String, String> properties = new HashMap<>();
//
//}
