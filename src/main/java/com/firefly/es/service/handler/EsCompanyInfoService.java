package com.firefly.es.service.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.firefly.es.service.dto.CompanyDto;
import com.firefly.es.service.dto.EntityObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service("service")
public class EsCompanyInfoService {

    private TransportClient client = EsClientFactory.getClient();

    private static  String INDEX_NAME = "company_info";
    private static  String DOC_TYPE = "content";

    private static int START = 0;
    private static int END = 36;


    public EsCompanyInfoService(){
    }

    /**
     *
     * @return
     */
    public String queryAll(){
        JSONArray jsonArray = new JSONArray();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(DOC_TYPE).setFrom(0).setSize(36).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);
    }

    /**
     * 关键词搜索
     * @param keyword
     * @return
     */
    public String queryByKeyword(String keyword,String industry,int start,int end){

        JSONArray jsonArray = new JSONArray();

        if(StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(industry)){
            return "";
        }

        if(start < 0){
            start = START ;
        }
        if(end < 0 ){
            end = END;
        }



        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();


        if(StringUtils.isNotBlank(keyword)){
            queryBuilder.should(QueryBuilders.termQuery("bs",keyword));

        }

        if(StringUtils.isNotBlank(industry)){
            queryBuilder.filter(QueryBuilders.termQuery("industry",industry));
        }

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(DOC_TYPE).setFrom(start).setSize(end).setQuery(queryBuilder).get();

        for (SearchHit searchHit : response.getHits()) {
            jsonArray.add(searchHit.getSourceAsString());
        }
        return JSONObject.toJSONString(jsonArray);


    }


    /**
     *
     * @return bool
     */
    public String createIndexSchemaHandler(){

        CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(INDEX_NAME);

        XContentBuilder mapping = null;
        try {
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("id")
                    .field("type","keyword")
                    .endObject()
                    .startObject("industry")
                    .field("type","keyword") //设置数据类型
                    .endObject()
                    .startObject("bs")
                    .field("type","text")
                    .field("analyzer","ik_smart")
                    .field("search_analyzer","ik_smart")
                    .endObject()
                    .startObject("lpn")
                    .field("type","keyword")
                    .endObject()
                    .startObject("name")
                    .field("type","keyword")
                    .endObject()
                    .startObject("orgnumber")
                    .field("type","keyword")
                    .endObject()
                    .startObject("regcapital")
                    .field("type","keyword")
                    .endObject()
                    .endObject()
                    .endObject();

        } catch (IOException e) {
            e.printStackTrace();
            return "创建失败!";
        }

        cib.addMapping(DOC_TYPE, mapping);

        try {
            CreateIndexResponse res=cib.execute().actionGet();
        }catch (ResourceAlreadyExistsException e){
            return "已存在";
        }


        return "创建成功";
    }


    /**
     * delete index
     */
    public void deleIndex(){

        //可以根据DeleteIndexResponse对象的isAcknowledged()方法判断删除是否成功,返回值为boolean类型.
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(INDEX_NAME)
                .execute().actionGet();
        System.out.println("是否删除成功:"+dResponse.isAcknowledged());

        //如果传人的indexName不存在会出现异常.可以先判断索引是否存在：
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(INDEX_NAME);

        IndicesExistsResponse inExistsResponse = client.admin().indices()
                .exists(inExistsRequest).actionGet();

        //根据IndicesExistsResponse对象的isExists()方法的boolean返回值可以判断索引库是否存在.
        System.out.println("是否删除成功:"+inExistsResponse.isExists());

    }


    /**
     * storeDocument
     * @param dto
     * @return
     */
    public String storeDocument(CompanyDto dto){
        IndexResponse response = null;
        try {
            response = client.prepareIndex(INDEX_NAME, DOC_TYPE)
                    .setSource(XContentFactory.jsonBuilder().startObject()
                            .field("id",dto.getId())
                            .field("industry",dto.getIndustry())
                            .field("bs",dto.getBusinessscope())
                            .field("lpn",dto.getLegalpersonname())
                            .field("name",dto.getName())
                            .field("orgnumber",dto.getOrgnumber())
                            .field("regcapital",dto.getRegcapital())
                            .endObject())
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return response.getId();
    }
}
