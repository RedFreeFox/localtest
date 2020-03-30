package com.bob.elasticsearch.local;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wb-jjb318191
 * @create 2019-04-19 15:20
 */
public class TransportClientTest {

    private TransportClient client;

    private final static String article = "lanboal";
    // 一个索引映射两种类型, 但6.x只支持一种 映射类型了
    private final static String PYTHON = "python";
    private final static String JAVA = "java";

    @Before
    public void getClient() throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();// 集群名
        //创建client
        client = new PreBuiltTransportClient(settings)
            .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    /**
     * 创建索引并添加映射
     *
     * @throws IOException
     */
    @Test
    public void CreateIndexAndMapping() throws Exception {

        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(article);

        XContentBuilder mapping = XContentFactory.jsonBuilder()
            .startObject()
            .startObject("properties") //设置之定义字段
            .startObject("author")
            .field("type", "text") //设置数据类型
            .endObject()
            .startObject("title")
            .field("type", "text")
            .endObject()
            .startObject("content")
            .field("type", "text")
            .endObject()
            .startObject("price")
            .field("type", "text")
            .endObject()
            .startObject("view")
            .field("type", "text")
            .endObject()
            .startObject("tag")
            .field("type", "text")
            .endObject()
            .startObject("date")
            .field("type", "date")  //设置Date类型
            .field("format", "yyyy-MM-dd HH:mm:ss") //设置Date的格式
            .endObject()
            .endObject()
            .endObject();

        String json = XContentHelper.convertToJson(BytesReference.bytes(mapping), false, false, XContentType.JSON);

        System.out.println(json);

        cib.addMapping(PYTHON, mapping);

        CreateIndexResponse res = cib.execute().actionGet();

        System.out.println(new Gson().toJson(res));
    }

    /**
     * 创建索引并添加文档
     *
     * @throws Exception
     */
    @Test
    public void addIndexAndDocument() throws Exception {

        Date time = new Date();

        IndexResponse response = client.prepareIndex(article, JAVA)
            .setSource(XContentFactory.jsonBuilder().startObject()
                .field("id", "123")
                .field("author", "jiangjibo")
                .field("title", "192.138.1.2")
                .field("content", "这是C++有关的书籍")
                .field("price", "30")
                .field("view", "80")
                .field("tag", "a,b,c,d,e,f")
                .field("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time))
                .endObject())
            .get();
        System.out.println(new Gson().toJson(response));
    }

    @Test
    public void testSearch() {
        SearchResponse searchResponse = client.prepareSearch("tag").setTypes("add")
            //.setPreference("_local")
            //.setPreference("_primary")
            //.setPreference("_primary_first")
            //.setPreference("_only_node:ZYYWXGZCSkSL7QD0bDVxYA")
            //.setPreference("_prefer_node:ZYYWXGZCSkSL7QD0bDVxYA")
            .setPreference("_shards:0,1,2")
            .setQuery(QueryBuilders.matchAllQuery()).setExplain(true).get();
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits());
        SearchHit[] hits2 = hits.getHits();
        for (SearchHit h : hits2) {
            System.out.println(h.getSourceAsString());
        }

    }

}
