package com.example.demo.es.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.RabbitEntity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 应用模块名称<p>
 * 代码描述<p>
 * Company: 逍邦网络科技有限公司<p>
 *
 * @author chenshan
 * @version v1.0
 * @since 2019-07-24 16:05
 */
@Slf4j
@Component
public class ESHelper {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	public Map<String, Object> test(String corpid, String id, String str, String keyword) throws Exception {
		Map<String, Object> modelMap = new HashMap<>();
		Client client = elasticsearchTemplate.getClient();
		id = id + "_" + corpid;
		GetRequest getRequest = get(str, id, corpid);
		GetResponse ooo = client.get(getRequest).get();
		Boolean flag = ooo.isExists();
		if ( flag && ooo.getSource().get("str").equals(keyword) ) {
			modelMap.put("get", ooo);
			return modelMap;
		}
//		Map<String, Object> insert = insert(str, keyword);
		IndexRequest indexRequest = index(str, keyword, id,corpid);
		IndexResponse o = client.index(indexRequest).get();
		UpdateRequest updateRequest = update(str, id, keyword,corpid);
		UpdateResponse oo = client.update(updateRequest).get();
		ooo = client.get(getRequest).get();
		modelMap.put("insert", o);
		modelMap.put("update", oo);
		modelMap.put("get", ooo);
//		modelMap.put("setting",insert);
//		modelMap.put("search", search());
		return modelMap;
	}
	
	public Map<String, Object> insert(String str, String keyword) throws Exception {
		Map<String, Object> modelMap = new HashMap<>();
		Boolean flag = elasticsearchTemplate.createIndex(str + "_" + keyword);
		Map<String, Object> model = elasticsearchTemplate.getSetting(str + "_" + keyword);
		modelMap.put("setting", model);
		modelMap.put("flag", flag);
		return modelMap;
	}
	
	public static Map<String, Object> param(String s, String keyword) {
		Map<String, Object> param = new HashMap<>(2);
		param.put(s, keyword);
		return param;
	}
	
	public static IndexRequest index(String s, String keyword, String id,String corpid) {
		IndexRequest indexRequest = new IndexRequest();
		indexRequest.index(s).type(corpid).setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL).source(new JSONObject(param(s, keyword)).toJSONString(), Requests.INDEX_CONTENT_TYPE).id(id);
		return indexRequest;
	}
	
	public static UpdateRequest update(String s, String id, String keyword,String corpid) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.doc(param(s, keyword)).index(s).type(corpid).doc(new JSONObject(param(s, keyword)).toJSONString(), Requests.INDEX_CONTENT_TYPE).id(id).setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL).retryOnConflict(5);
		return updateRequest;
	}
	
	public static GetRequest get(String s, String id, String keyword) {
		GetRequest getRequest = new GetRequest();
		getRequest.index(s).type(keyword).id(id);
		return getRequest;
	}
	
	public String search() throws Exception {

//		SearchRequest searchRequest=new SearchRequest();
//		searchRequest.routing("");
//		BoolQueryBuilder builder=new BoolQueryBuilder();
		BoolQueryBuilder builder = boolQuery();
		builder.filter(termQuery("str1", "str1"));
		Script script = new Script("doc['num'].value >= doc['stockUpperLimit'].value");
		builder.filter(scriptQuery(script));
		System.out.println(script);
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(builder);
		System.out.println(sourceBuilder);
		
		SearchRequest searchRequest = new SearchRequest("1");
		searchRequest.scroll(TimeValue.timeValueMillis(60000));
		elasticsearchTemplate.setSearchTimeout("60000");
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
		
		Long ex=elasticsearchTemplate.count(searchQuery,Object.class);
		return null;
	}
}

