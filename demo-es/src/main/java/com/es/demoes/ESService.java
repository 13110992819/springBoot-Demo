//package com.es.demoes;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.entity.ContentType;
//import org.apache.http.nio.entity.NStringEntity;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.search.Scroll;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Objects;
//
//import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termQuery;
//
///**
// * 应用模块名称<p>
// * 代码描述<p>
// * Company: 逍邦网络科技有限公司<p>
// *
// * @author chenshan
// * @version v1.0
// * @since 2020/6/11 4:13 下午
// */
//@Service
//public class ESService {
//    @Resource
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//    public void test(){
//        SearchRequest searchRequest = new SearchRequest("11").types("SearchRequest searchRequest = new SearchRequest();");
//        BoolQueryBuilder boolQueryBuilder = boolQuery();
//        boolQueryBuilder.filter(termQuery("corpid","corpid"));
////        elasticsearchRestTemplate
//        SearchSourceBuilder sourceBuilder = searchRequest.source();
//
//    }
//    private <T> AggregatedPage<T> getResult(PageRequest pageRequest, SearchRequest searchRequest, Class<T> clazz, SearchSourceBuilder searchSourceBuilder) throws IOException {
//        SearchResponse searchResponse = search(searchRequest, searchSourceBuilder);
//        DefaultResultMapper defaultResultMapper = new DefaultResultMapper();
//        return defaultResultMapper.mapResults(searchResponse, clazz,pageRequest);
//    }
//
//    private SearchResponse search(SearchRequest searchRequest, SearchSourceBuilder searchSourceBuilder) throws IOException {
//        String searchSource = searchSourceBuilder.toString();
//        SearchResponse searchResponse = null;
//        // 判断有没有scrpit
//        String source = "\"source\"";
//        HttpEntity httpEntity = null;
//        try {
//            if(searchSource.contains(source)){
//                searchSource = searchSource.replaceAll("\"source\"", "\"inline\"");
//                String index = searchRequest.indices()[0];
//                String indexType = index +"/" +searchRequest.types()[0];
//                String endpoint = indexType + "/_search";
//                Scroll scroll = searchRequest.scroll();
//                if(scroll  !=null){
//                    endpoint =  endpoint + "?scroll=1m";
//                }
//                httpEntity = new NStringEntity(searchSource, ContentType.APPLICATION_JSON);
//                Response response = getRestClient(index).performRequest("GET", endpoint, Collections.emptyMap(), httpEntity);
//                //TODO  聚合会报错  2019年6月29日09:26:25
//                searchResponse = getSearchResponse(response1 -> parseEntity(response.getEntity(), SearchResponse::fromXContent),response);
//            }else {
//                searchResponse = getRestHighLevelClient(searchRequest.indices()[0]).search(searchRequest);
//            }
//        } catch (Exception e) {
//            LOG.error(ErrorCodeEnum.API_ERROR_100071.getAlias(),e);
//            throw  new XbbException(ErrorCodeEnum.API_ERROR_100071);
//        } finally {
//            if ( Objects.nonNull(httpEntity)) {
//                ((NStringEntity) httpEntity).close();
//            }
//        }
//        return searchResponse;
//    }
//}
