//package com.example.demo.elasticsearch;
//
//import com.alibaba.fastjson.JSONObject;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.entity.ContentType;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.nio.reactor.IOReactorConfig;
//import org.apache.http.nio.entity.NStringEntity;
//import org.apache.http.util.EntityUtils;
//import org.elasticsearch.action.ActionFuture;
//import org.elasticsearch.action.ActionRequest;
//import org.elasticsearch.action.search.ClearScrollRequest;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchScrollRequest;
//import org.elasticsearch.client.Response;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.CheckedFunction;
//import org.elasticsearch.common.ParseField;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.ContextParser;
//import org.elasticsearch.common.xcontent.NamedXContentRegistry;
//import org.elasticsearch.common.xcontent.XContentParser;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.plugins.spi.NamedXContentProvider;
//import org.elasticsearch.search.Scroll;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.bucket.adjacency.AdjacencyMatrixAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.adjacency.ParsedAdjacencyMatrix;
//import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
//import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilters;
//import org.elasticsearch.search.aggregations.bucket.geogrid.GeoGridAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.geogrid.ParsedGeoHashGrid;
//import org.elasticsearch.search.aggregations.bucket.global.GlobalAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.global.ParsedGlobal;
//import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
//import org.elasticsearch.search.aggregations.bucket.histogram.ParsedHistogram;
//import org.elasticsearch.search.aggregations.bucket.missing.MissingAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.missing.ParsedMissing;
//import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
//import org.elasticsearch.search.aggregations.bucket.nested.ParsedReverseNested;
//import org.elasticsearch.search.aggregations.bucket.nested.ReverseNestedAggregationBuilder;
//import org.elasticsearch.search.aggregations.bucket.range.*;
//import org.elasticsearch.search.aggregations.bucket.sampler.InternalSampler;
//import org.elasticsearch.search.aggregations.bucket.sampler.ParsedSampler;
//import org.elasticsearch.search.aggregations.bucket.significant.ParsedSignificantLongTerms;
//import org.elasticsearch.search.aggregations.bucket.significant.ParsedSignificantStringTerms;
//import org.elasticsearch.search.aggregations.bucket.significant.SignificantLongTerms;
//import org.elasticsearch.search.aggregations.bucket.significant.SignificantStringTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.*;
//import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
//import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
//import org.elasticsearch.search.aggregations.metrics.geobounds.GeoBoundsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.geobounds.ParsedGeoBounds;
//import org.elasticsearch.search.aggregations.metrics.geocentroid.GeoCentroidAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.geocentroid.ParsedGeoCentroid;
//import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
//import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
//import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentileRanks;
//import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.InternalHDRPercentiles;
//import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.ParsedHDRPercentileRanks;
//import org.elasticsearch.search.aggregations.metrics.percentiles.hdr.ParsedHDRPercentiles;
//import org.elasticsearch.search.aggregations.metrics.percentiles.tdigest.InternalTDigestPercentileRanks;
//import org.elasticsearch.search.aggregations.metrics.percentiles.tdigest.InternalTDigestPercentiles;
//import org.elasticsearch.search.aggregations.metrics.percentiles.tdigest.ParsedTDigestPercentileRanks;
//import org.elasticsearch.search.aggregations.metrics.percentiles.tdigest.ParsedTDigestPercentiles;
//import org.elasticsearch.search.aggregations.metrics.scripted.ParsedScriptedMetric;
//import org.elasticsearch.search.aggregations.metrics.scripted.ScriptedMetricAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.stats.ParsedStats;
//import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.stats.extended.ParsedExtendedStats;
//import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
//import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
//import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
//import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
//import org.elasticsearch.search.aggregations.pipeline.InternalSimpleValue;
//import org.elasticsearch.search.aggregations.pipeline.ParsedSimpleValue;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.InternalBucketMetricValue;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.ParsedBucketMetricValue;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.percentile.ParsedPercentilesBucket;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.percentile.PercentilesBucketPipelineAggregationBuilder;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.ParsedStatsBucket;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.StatsBucketPipelineAggregationBuilder;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.extended.ExtendedStatsBucketPipelineAggregationBuilder;
//import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.stats.extended.ParsedExtendedStatsBucket;
//import org.elasticsearch.search.aggregations.pipeline.derivative.DerivativePipelineAggregationBuilder;
//import org.elasticsearch.search.aggregations.pipeline.derivative.ParsedDerivative;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.suggest.Suggest;
//import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
//import org.elasticsearch.search.suggest.phrase.PhraseSuggestion;
//import org.elasticsearch.search.suggest.term.TermSuggestion;
//import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.DefaultResultMapper;
//import org.springframework.data.elasticsearch.core.ResultsExtractor;
//import org.springframework.data.elasticsearch.core.ScrolledPage;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//
//@Configuration
//@EnableConfigurationProperties(ElasticsearchProperties.class)
//public class XbbElasticsearchRestTemplate {
//	private static final Logger LOG = LoggerFactory.getLogger(XbbElasticsearchRestTemplate.class);
//
//	@Autowired
//	private ElasticsearchProperties properties;
//
//	private RestClient lowLevelRestClient;
//
//	private RestHighLevelClient restHighLevelClient;
//
//	private static boolean PRINT_LOG = true;
//
////	TransportClient transportClient;
//	private static final String COLON = ":";
//	private static final String COMMA = ",";
//
//	@Bean
//	public RestClient lowLevelRestClient() throws Exception {
//		RestClient lowLevelRestClient = getLowLevelRestClient();
//		this.lowLevelRestClient = lowLevelRestClient;
//		return lowLevelRestClient;
//	}
//
//	/**
//	 * 同一个类的@Bean是按照上下顺序加载的，所以一定要保证lowLevelRestClient在restHighLevelClient钱
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//	public RestHighLevelClient restHighLevelClient() throws Exception {
//		RestHighLevelClient restHighLevelClient =
//				new RestHighLevelClient(lowLevelRestClient);
//		this.restHighLevelClient = restHighLevelClient;
//		return restHighLevelClient;
//	}
//
//
//	public RestClient getRestClient() {
//		return lowLevelRestClient;
//	}
//
//	public RestHighLevelClient getRestHighLevelClient() {
//		return restHighLevelClient;
//	}
////	@Bean
////	public TransportClient transportClient() throws UnknownHostException {
////		TransportClient client = new PreBuiltXPackTransportClient(Settings.builder()
////				.put("cluster.name", "es-cluster")
////				.put("xpack.security.user", "elastic:changeme")
////				.build())
////				.addTransportAddress(new TransportAddress(InetAddress.getByName("121.196.236.164"), 9300));
////		return client;
////	}
//
//	private RestClient getLowLevelRestClient() throws Exception {
//		String clusterNodes = properties.getClusterNodes();
//		if ( StringUtil.isEmpty(clusterNodes) ) {
//			LOG.error("请配置cluster-nodes！");
//			throw new Exception();//.API_ERROR_100001,"请配置cluster-nodes！");
//		}
//		String[] nodes = StringUtils.delimitedListToStringArray(clusterNodes, COMMA);
//		List<HttpHost> list = Arrays.stream(nodes).map(node -> {
//			String[] segments = StringUtils.delimitedListToStringArray(node, COLON);
//			Assert.isTrue(segments.length == 2,
//					() -> String.format("Invalid cluster node %s in %s! Must be in the format host:port!", node, clusterNodes));
//			String host = segments[0].trim();
//			String port = segments[1].trim();
//			Assert.hasText(host, () -> String.format("No host name given cluster node %s!", node));
//			Assert.hasText(port, () -> String.format("No port given in cluster node %s!", node));
////            return new HttpHost(toInetAddress(host), Integer.valueOf(port), "http");
//			return new HttpHost(toInetAddress(host), 9200, "http");
//		}).collect(Collectors.toList());
//		HttpHost[] hostArray = new HttpHost[list.size()];
//		RestClientBuilder restClientBuilder = RestClient.builder(list.toArray(hostArray));
//		//连接时间   参照 AbstractHttpTransport
//		restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.
//				//10000
//						setConnectTimeout(60000)
//				//10000
//				.setConnectionRequestTimeout(60000)
//				//600000
//				.setSocketTimeout(60000))
//				// 默认值  DEFAULT_MAX_RETRY_TIMEOUT_MILLIS  30000
//				.setMaxRetryTimeoutMillis(60000)
//		;
//		//Xpack
//		if ( properties.getXpack() != null && StringUtil.isNotEmpty(properties.getXpack().getUser()) && StringUtil.isNotEmpty(properties.getXpack().getPassword()) ) {
//			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//			credentialsProvider.setCredentials(AuthScope.ANY,
//					new UsernamePasswordCredentials(properties.getXpack().getUser(), properties.getXpack().getPassword()));
//			restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
//					//是整个连接池的大小   AbstractHttpTransport  1000 （默认30，暂时设置成数据库连接池一致50）
//					.setMaxConnTotal(10000)
//					//是单个路由连接的最大数  AbstractHttpTransport  500
////                    .setMaxConnPerRoute(500)
//					.setDefaultCredentialsProvider(credentialsProvider)
//					.setDefaultIOReactorConfig(
//							IOReactorConfig.custom()
//									.build()));
//		}
//
//		return restClientBuilder.build();
//	}
//
//	/**
//	 * Execute the query against elasticsearch and return result as {@link Page}
//	 *
//	 * @param pageRequest
//	 * @param searchRequest
//	 * @param clazz
//	 * @param <T>
//	 * @return
//	 * @throws IOException
//	 */
//	public <T> AggregatedPage<T> queryForPage(PageRequest pageRequest, SearchRequest searchRequest, Class<T> clazz) throws Exception {
//		try {
//			printEsInfo(searchRequest);
//			SearchSourceBuilder searchSourceBuilder = searchRequest.source();
//			return getResult(pageRequest, searchRequest, clazz, searchSourceBuilder);
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	public SearchResponse search(SearchRequest searchRequest) throws Exception {
//		try {
//			printEsInfo(searchRequest);
//			return search(searchRequest, searchRequest.source());
//		} catch (IOException e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	/**
//	 * 获取聚合结果的responseJson
//	 *
//	 * @param searchRequest
//	 * @return
//	 * @throws Exception
//	 */
//	public JSONObject getAggResponseJson(SearchRequest searchRequest) throws Exception {
//		try {
//			printEsInfo(searchRequest);
//			String searchSource = searchRequest.source().toString();
//			// 判断有没有scrpit
//			String source = "\"source\"";
//			if ( searchSource.contains(source) ) {
//				searchSource = searchSource.replaceAll("\"source\"", "\"inline\"");
//			}
//			String indexType = searchRequest.indices()[0] + "/" + searchRequest.types()[0];
//			String endpoint = indexType + "/_search";
//			HttpEntity httpEntity = new NStringEntity(searchSource, ContentType.APPLICATION_JSON);
//			Response response = getLowLevelRestClient().performRequest("GET", endpoint, Collections.emptyMap(), httpEntity);
//			JSONObject entityJson = (JSONObject) JSONObject.parse(EntityUtils.toString(response.getEntity()));
//			return entityJson;
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	public <T> List<T> queryForList(PageRequest pageRequest, SearchRequest searchRequest, Class<T> clazz) throws Exception {
//		return queryForPage(pageRequest, searchRequest, clazz).getContent();
//	}
//
//	public <T> long count(SearchRequest searchRequest, Class<T> clazz) throws Exception {
//		PageRequest pageRequest = PageRequest.of(1, 1);
//		searchRequest.source().size(0).from(0);
//		return queryForPage(pageRequest, searchRequest, clazz).getTotalElements();
//	}
//
//	public <T> ScrolledPage<T> startScroll(long scrollTimeInMillis, SearchRequest searchRequest, Class<T> clazz) throws Exception {
//		try {
//			printEsInfo(searchRequest);
//			// If set, will enable scrolling of the search request for the specified timeout.
//			searchRequest.scroll(TimeValue.timeValueMillis(scrollTimeInMillis));
//			SearchSourceBuilder sourceBuilder = searchRequest.source();
//			int from = sourceBuilder.from();
//			int size = sourceBuilder.size();
//			from = from == 0 ? 1 : from;
//			size = size == 0 ? 20 : size;
//			int page = from / size;
//			page = page < 0 ? 0 : page;
//			PageRequest pageRequest = PageRequest.of(page, size);
//			return getResult(pageRequest, searchRequest, clazz, sourceBuilder);
//            /*SearchResponse searchResponse = getRestHighLevelClient().search(searchRequest);
//            DefaultResultMapper defaultResultMapper = new DefaultResultMapper();
//            return defaultResultMapper.mapResults(searchResponse, clazz,pageRequest);*/
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	public <T> ScrolledPage<T> continueScroll(long scrollTimeInMillis, ScrolledPage scrolledPage, Class<T> clazz) throws Exception {
//		try {
//			SearchScrollRequest searchScrollRequest = new SearchScrollRequest();
//			// If set, will enable scrolling of the search request for the specified timeout.    continue也必须添加
//			searchScrollRequest.scrollId(scrolledPage.getScrollId());
//			searchScrollRequest.scroll(TimeValue.timeValueMillis(scrollTimeInMillis));
//			SearchResponse searchResponse = getRestHighLevelClient().searchScroll(searchScrollRequest);
//			DefaultResultMapper defaultResultMapper = new DefaultResultMapper();
//			return defaultResultMapper.mapResults(searchResponse, clazz, scrolledPage.getPageable());
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	public void clearScroll(String scrollId) throws Exception {
//		try {
//			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//			clearScrollRequest.addScrollId(scrollId);
//			//TODO  如果有需求可以解析返回值
////            ClearScrollResponse clearScrollResponse =
//			getRestHighLevelClient().clearScroll(clearScrollRequest);
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	public List findScrollList(Class clazz, SearchRequest searchRequest, long scrollTimeInMillis) throws Exception {
//		ScrolledPage scrolledPage = startScroll(scrollTimeInMillis, searchRequest, clazz);
//		List list = new ArrayList();
//		while (scrolledPage.hasContent()) {
//			List content = scrolledPage.getContent();
//			list.addAll(content);
//			scrolledPage = continueScroll(scrollTimeInMillis, scrolledPage, clazz);
//		}
//		clearScroll(scrolledPage.getScrollId());
//		return list;
//	}
//
//	/**
//	 * 导出的5W条限制，滚动读取
//	 *
//	 * @param clazz
//	 * @param searchRequest
//	 * @return
//	 * @throws Exception
//	 */
//	public List getScrollListForExport(Class clazz, SearchRequest searchRequest) throws Exception {
//		long scrollTimeInMillis = 6000L;
//		ScrolledPage scrolledPage = startScroll(scrollTimeInMillis, searchRequest, clazz);
//		List list = new ArrayList();
//		for ( int i = 0; i < 5; i++ ) {
//			if ( scrolledPage.hasContent() ) {
//				List content = scrolledPage.getContent();
//				list.addAll(content);
//				scrolledPage = continueScroll(scrollTimeInMillis, scrolledPage, clazz);
//			} else {
//				break;
//			}
//		}
//		clearScroll(scrolledPage.getScrollId());
//		return list;
//	}
//
//
//	public <T> T query(SearchRequest searchRequest, ResultsExtractor<T> resultsExtractor) throws Exception {
//		try {
//			printEsInfo(searchRequest);
//			SearchResponse searchResponse =getRestHighLevelClient().search(searchRequest);
//			return resultsExtractor.extract(searchResponse);
//		} catch (Exception e) {
//			throw new Exception();//.API_ERROR_100071);
//		}
//	}
//
//	/**
//	 * 删除索引（慎用）
//	 *
//	 * @param indexName
//	 * @return
//	 */
//	public boolean deleteIndex(String indexName) throws Exception {
//		try {
//			getRestClient().performRequest("DELETE", indexName);
//		} catch (IOException e) {
//			throw new Exception();//.API_ERROR_100073);
//		}
//		return true;
//	}
//
//	private static InetAddress toInetAddress(String host) {
//		try {
//			return InetAddress.getByName(host);
//		} catch (UnknownHostException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}
//
//	private <T> AggregatedPage<T> getResult(PageRequest pageRequest, SearchRequest searchRequest, Class<T> clazz, SearchSourceBuilder searchSourceBuilder) throws IOException {
//		SearchResponse searchResponse = search(searchRequest, searchSourceBuilder);
//		DefaultResultMapper defaultResultMapper = new DefaultResultMapper();
//		return defaultResultMapper.mapResults(searchResponse, clazz, pageRequest);
//	}
//
//	private SearchResponse search(SearchRequest searchRequest, SearchSourceBuilder searchSourceBuilder) throws IOException {
//		String searchSource = searchSourceBuilder.toString();
//		SearchResponse searchResponse;
//		// 判断有没有scrpit
//		String source = "\"source\"";
//		if ( searchSource.contains(source) ) {
//			searchSource = searchSource.replaceAll("\"source\"", "\"inline\"");
//			String indexType = searchRequest.indices()[0] + "/" + searchRequest.types()[0];
//			String endpoint = indexType + "/_search";
//			Scroll scroll = searchRequest.scroll();
//			if ( scroll != null ) {
//				endpoint = endpoint + "?scroll=1m";
//			}
//			HttpEntity httpEntity = new NStringEntity(searchSource, ContentType.APPLICATION_JSON);
//			Response response = lowLevelRestClient.performRequest("GET", endpoint, Collections.emptyMap(), httpEntity);
//			//TODO  聚合会报错  2019年6月29日09:26:25
//			searchResponse = getSearchResponse(response1 -> parseEntity(response.getEntity(), SearchResponse::fromXContent), response);
//		} else {
//			searchResponse = getRestHighLevelClient().search(searchRequest);
//		}
//		return searchResponse;
//	}
//
//	public void setPrintLog(boolean printLog) {
//		PRINT_LOG = printLog;
//	}
//
//	private void printEsInfo(SearchRequest searchRequest) {
//		if ( !PRINT_LOG ) {
//			return;
//		}
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("\n GET  ");
//		stringBuilder.append(searchRequest.indices()[0]);
//		stringBuilder.append("/");
//		stringBuilder.append(searchRequest.types()[0]);
//		stringBuilder.append("/_search\n");
//		stringBuilder.append(searchRequest.source());
//		LOG.info(stringBuilder.toString());
//	}
//
//	List<NamedXContentRegistry.Entry> namedXContentEntries = Collections.emptyList();
//	final NamedXContentRegistry registry = new NamedXContentRegistry(
//			Stream.of(getDefaultNamedXContents().stream(), getProvidedNamedXContents().stream(), namedXContentEntries.stream())
//					.flatMap(Function.identity()).collect(toList()));
//
//	protected <Resp> Resp parseEntity(final HttpEntity entity,
//	                                  final CheckedFunction<XContentParser, Resp, IOException> entityParser) throws IOException {
//		if ( entity == null ) {
//			throw new IllegalStateException("Response body expected but not returned");
//		}
//		if ( entity.getContentType() == null ) {
//			throw new IllegalStateException("Elasticsearch didn't return the [Content-Type] header, unable to parse response body");
//		}
//		XContentType xContentType = XContentType.fromMediaTypeOrFormat(entity.getContentType().getValue());
//		if ( xContentType == null ) {
//			throw new IllegalStateException("Unsupported Content-Type: " + entity.getContentType().getValue());
//		}
//
///*        List<NamedXContentRegistry.Entry> namedXContentEntries = Collections.emptyList();
//        final NamedXContentRegistry registry  = new NamedXContentRegistry(
//                Stream.of(getDefaultNamedXContents().stream(), getProvidedNamedXContents().stream(), namedXContentEntries.stream())
//                        .flatMap(Function.identity()).collect(toList()));*/
//		try (XContentParser parser = xContentType.xContent().createParser(registry,null,entity.getContent())) {
//			return entityParser.apply(parser);
//		}
//	}
//
//	static List<NamedXContentRegistry.Entry> getDefaultNamedXContents() {
//		Map<String, ContextParser<Object, ? extends Aggregation>> map = new HashMap<>(1 << 4);
//		map.put(CardinalityAggregationBuilder.NAME, (p, c) -> ParsedCardinality.fromXContent(p, (String) c));
//		map.put(InternalHDRPercentiles.NAME, (p, c) -> ParsedHDRPercentiles.fromXContent(p, (String) c));
//		map.put(InternalHDRPercentileRanks.NAME, (p, c) -> ParsedHDRPercentileRanks.fromXContent(p, (String) c));
//		map.put(InternalTDigestPercentiles.NAME, (p, c) -> ParsedTDigestPercentiles.fromXContent(p, (String) c));
//		map.put(InternalTDigestPercentileRanks.NAME, (p, c) -> ParsedTDigestPercentileRanks.fromXContent(p, (String) c));
//		map.put(PercentilesBucketPipelineAggregationBuilder.NAME, (p, c) -> ParsedPercentilesBucket.fromXContent(p, (String) c));
//		map.put(MinAggregationBuilder.NAME, (p, c) -> ParsedMin.fromXContent(p, (String) c));
//		map.put(MaxAggregationBuilder.NAME, (p, c) -> ParsedMax.fromXContent(p, (String) c));
//		map.put(SumAggregationBuilder.NAME, (p, c) -> ParsedSum.fromXContent(p, (String) c));
//		map.put(AvgAggregationBuilder.NAME, (p, c) -> ParsedAvg.fromXContent(p, (String) c));
//		map.put(ValueCountAggregationBuilder.NAME, (p, c) -> ParsedValueCount.fromXContent(p, (String) c));
//		map.put(InternalSimpleValue.NAME, (p, c) -> ParsedSimpleValue.fromXContent(p, (String) c));
//		map.put(DerivativePipelineAggregationBuilder.NAME, (p, c) -> ParsedDerivative.fromXContent(p, (String) c));
//		map.put(InternalBucketMetricValue.NAME, (p, c) -> ParsedBucketMetricValue.fromXContent(p, (String) c));
//		map.put(StatsAggregationBuilder.NAME, (p, c) -> ParsedStats.fromXContent(p, (String) c));
//		map.put(StatsBucketPipelineAggregationBuilder.NAME, (p, c) -> ParsedStatsBucket.fromXContent(p, (String) c));
//		map.put(ExtendedStatsAggregationBuilder.NAME, (p, c) -> ParsedExtendedStats.fromXContent(p, (String) c));
//		map.put(ExtendedStatsBucketPipelineAggregationBuilder.NAME,
//				(p, c) -> ParsedExtendedStatsBucket.fromXContent(p, (String) c));
//		map.put(GeoBoundsAggregationBuilder.NAME, (p, c) -> ParsedGeoBounds.fromXContent(p, (String) c));
//		map.put(GeoCentroidAggregationBuilder.NAME, (p, c) -> ParsedGeoCentroid.fromXContent(p, (String) c));
//		map.put(HistogramAggregationBuilder.NAME, (p, c) -> ParsedHistogram.fromXContent(p, (String) c));
//		map.put(DateHistogramAggregationBuilder.NAME, (p, c) -> ParsedDateHistogram.fromXContent(p, (String) c));
//		map.put(StringTerms.NAME, (p, c) -> ParsedStringTerms.fromXContent(p, (String) c));
//		map.put(LongTerms.NAME, (p, c) -> ParsedLongTerms.fromXContent(p, (String) c));
//		map.put(DoubleTerms.NAME, (p, c) -> ParsedDoubleTerms.fromXContent(p, (String) c));
//		map.put(MissingAggregationBuilder.NAME, (p, c) -> ParsedMissing.fromXContent(p, (String) c));
//		map.put(NestedAggregationBuilder.NAME, (p, c) -> ParsedNested.fromXContent(p, (String) c));
//		map.put(ReverseNestedAggregationBuilder.NAME, (p, c) -> ParsedReverseNested.fromXContent(p, (String) c));
//		map.put(GlobalAggregationBuilder.NAME, (p, c) -> ParsedGlobal.fromXContent(p, (String) c));
//		map.put(FilterAggregationBuilder.NAME, (p, c) -> ParsedFilter.fromXContent(p, (String) c));
//		map.put(InternalSampler.PARSER_NAME, (p, c) -> ParsedSampler.fromXContent(p, (String) c));
//		map.put(GeoGridAggregationBuilder.NAME, (p, c) -> ParsedGeoHashGrid.fromXContent(p, (String) c));
//		map.put(RangeAggregationBuilder.NAME, (p, c) -> ParsedRange.fromXContent(p, (String) c));
//		map.put(DateRangeAggregationBuilder.NAME, (p, c) -> ParsedDateRange.fromXContent(p, (String) c));
//		map.put(GeoDistanceAggregationBuilder.NAME, (p, c) -> ParsedGeoDistance.fromXContent(p, (String) c));
//		map.put(FiltersAggregationBuilder.NAME, (p, c) -> ParsedFilters.fromXContent(p, (String) c));
//		map.put(AdjacencyMatrixAggregationBuilder.NAME, (p, c) -> ParsedAdjacencyMatrix.fromXContent(p, (String) c));
//		map.put(SignificantLongTerms.NAME, (p, c) -> ParsedSignificantLongTerms.fromXContent(p, (String) c));
//		map.put(SignificantStringTerms.NAME, (p, c) -> ParsedSignificantStringTerms.fromXContent(p, (String) c));
//		map.put(ScriptedMetricAggregationBuilder.NAME, (p, c) -> ParsedScriptedMetric.fromXContent(p, (String) c));
//		map.put(IpRangeAggregationBuilder.NAME, (p, c) -> ParsedBinaryRange.fromXContent(p, (String) c));
//		map.put(TopHitsAggregationBuilder.NAME, (p, c) -> ParsedTopHits.fromXContent(p, (String) c));
//		List<NamedXContentRegistry.Entry> entries = map.entrySet().stream()
//				.map(entry -> new NamedXContentRegistry.Entry(Aggregation.class, new ParseField(entry.getKey()), entry.getValue()))
//				.collect(Collectors.toList());
//		entries.add(new NamedXContentRegistry.Entry(Suggest.Suggestion.class, new ParseField(TermSuggestion.NAME),
//				(parser, context) -> TermSuggestion.fromXContent(parser, (String) context)));
//		entries.add(new NamedXContentRegistry.Entry(Suggest.Suggestion.class, new ParseField(PhraseSuggestion.NAME),
//				(parser, context) -> PhraseSuggestion.fromXContent(parser, (String) context)));
//		entries.add(new NamedXContentRegistry.Entry(Suggest.Suggestion.class, new ParseField(CompletionSuggestion.NAME),
//				(parser, context) -> CompletionSuggestion.fromXContent(parser, (String) context)));
//		return entries;
//	}
//
//	static List<NamedXContentRegistry.Entry> getProvidedNamedXContents() {
//		List<NamedXContentRegistry.Entry> entries = new ArrayList<>();
//		for ( NamedXContentProvider service : ServiceLoader.load(NamedXContentProvider.class) ) {
//			entries.addAll(service.getNamedXContentParsers());
//		}
//		return entries;
//	}
//
//	private <Req extends ActionRequest, Resp> Resp getSearchResponse(
//			CheckedFunction<Response, Resp, IOException> responseConverter, Response response) throws IOException {
//		return responseConverter.apply(response);
//	}
//
//}
