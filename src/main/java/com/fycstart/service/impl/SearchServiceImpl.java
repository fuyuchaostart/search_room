
package com.fycstart.service.impl;
/*
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fycstart.bass.HouseSort;
import com.fycstart.bass.RentValueBlock;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.entity.House;
import com.fycstart.service.SearchService;
import com.fycstart.web.form.RentSearch;
import com.google.common.primitives.Longs;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

*/

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fycstart.bass.HouseSort;
import com.fycstart.bass.RentValueBlock;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.StatusEnum;
import com.fycstart.entity.House;
import com.fycstart.entity.HouseDetail;
import com.fycstart.entity.HouseTag;
import com.fycstart.mapper.HouseDetailMapper;
import com.fycstart.mapper.HouseMapper;
import com.fycstart.mapper.HouseTagMapper;
import com.fycstart.search.HouseIndexKey;
import com.fycstart.search.HouseIndexTemplate;
import com.fycstart.service.SearchService;
import com.fycstart.web.form.RentSearch;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author fyc
 * @description: 搜索引擎
 * @date 2019/5/6上午 11:57
 */

@Service
public class SearchServiceImpl implements SearchService {
    private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseDetailMapper houseDetailMapper;

    @Autowired
    private HouseTagMapper houseTagMapper;

    /**
     * 插入数据
     *
     * @param houseIndexTemplate
     * @return
     * @throws IOException
     */
    @Override
    public Boolean create(HouseIndexTemplate houseIndexTemplate) throws IOException {

        boolean isFlag = false;
        String source = JSON.toJSONString(houseIndexTemplate);
        //设置索引和id
        IndexRequest request = new IndexRequest("xunwu").id(String.valueOf(houseIndexTemplate.getHouseId()));
        request.source(source, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        response.getShardInfo().getSuccessful();
        int status = response.status().getStatus();
        if (RestStatus.CREATED.equals(status)) {
            isFlag = true;
        }
        return isFlag;
    }


    /**
     * @return java.lang.Boolean
     * @Author fyc
     * @Description // 删除文档并新建文档(相当于更新)
     * @Date 上午 9:48 2019/5/7
     * @Param [houseIndexTemplate]
     **/
    @Override
    public Boolean deleteOrCreate(HouseIndexTemplate houseIndexTemplate) throws IOException {
        boolean isFlag;
        DeleteRequest request = new DeleteRequest("xunwu", String.valueOf(houseIndexTemplate.getHouseId()));

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);

        int status = response.status().getStatus();

        if (StatusEnum.SUCCESS.getStatus().equals(status)) {
            isFlag = create(houseIndexTemplate);
        } else {
            throw new RuntimeException("删除失败 index:xunwu id: " + houseIndexTemplate.getHouseId());
        }
        return isFlag;
    }

    /**
     * @return java.lang.Boolean
     * @Author fyc
     * @Description // 更新文档
     * @Date 上午 10:51 2019/5/7
     * @Param [houseIndexTemplate]
     **/
    @Override
    public Boolean update(HouseIndexTemplate houseIndexTemplate) throws IOException {
        boolean isFlag = false;
        String source = JSON.toJSONString(houseIndexTemplate);
        UpdateRequest updateRequest = new UpdateRequest("", "");
        updateRequest.doc(source, XContentType.JSON);

        UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);

        int status = response.status().getStatus();
        if (StatusEnum.SUCCESS.getStatus().equals(status)) {
            isFlag = true;
        }
        return isFlag;
    }

    /**
     * @return java.lang.Boolean
     * @Author fyc
     * @Description // 删除文档数据
     * @Date 上午 10:43 2019/5/7
     * @Param [houseId]
     **/
    @Override
    public Boolean remove(String houseId) throws IOException {
        boolean isFlag = false;
        DeleteRequest deleteRequest = new DeleteRequest("xunwu", houseId);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);

        int status = response.status().getStatus();
        if (StatusEnum.SUCCESS.getStatus().equals(status)) {
            isFlag = true;
        }
        return isFlag;
    }

    /**
     * @return java.lang.Boolean
     * @Author fyc
     * @Description // 更新es中的书
     * @Date 下午 6:58 2019/5/7
     * @Param [houseId]
     **/
    @Override
    public Boolean paddingIndex(String houseId) throws IOException {

        HouseIndexTemplate houseIndexTemplate = new HouseIndexTemplate();
        House house = houseMapper.selectById(houseId);
        if (house != null) {
            logger.error("更新es数据,查询house出现错误");
        }
        BeanUtils.copyProperties(house, houseIndexTemplate);

        QueryWrapper<HouseDetail> detailQueryWrapper = new QueryWrapper<>();
        detailQueryWrapper.eq("house_id", houseId);
        HouseDetail houseDetail = houseDetailMapper.selectOne(detailQueryWrapper);
        BeanUtils.copyProperties(houseDetail, houseIndexTemplate);

        QueryWrapper<HouseTag> houseTagQueryWrapper = new QueryWrapper<>();
        houseTagQueryWrapper.eq("house_id", houseId);
        List<HouseTag> houseTags = houseTagMapper.selectList(houseTagQueryWrapper);
        BeanUtils.copyProperties(houseDetail, houseIndexTemplate);
        List<String> tags = new ArrayList<>();

        houseTags.forEach(houseTag -> tags.add(houseTag.getName()));
        houseIndexTemplate.setTags(tags);

        Set<QueryBuilder> queryBuilders = new HashSet<>(1);
        queryBuilders.add(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId));

        List<HouseIndexTemplate> houseIndexTemplates = queryHouseIndexTemplate(queryBuilders, null);
        Boolean isFlag;
        if (houseIndexTemplates.size() == 0) {
            isFlag = create(houseIndexTemplate);
        } else if (houseIndexTemplates.size() == 1) {
            isFlag = update(houseIndexTemplate);
        } else {
            isFlag = deleteOrCreate(houseIndexTemplate);
        }
        return isFlag;
    }

    /**
     * es搜索并返回数据
     *
     * @param set
     * @param sortMap
     * @return
     * @throws IOException
     */
    @Override
    public List<HouseIndexTemplate> queryHouseIndexTemplate(Set<QueryBuilder> set, Map<String, SortOrder> sortMap) {
        try {
            logger.info("es查询出的数据参数 {}", JSON.toJSONString(set));
            //实例化查询实例
            SearchRequest searchRequest = new SearchRequest("xunwu");
            // 封装查询参数
            SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(set, sortMap);

            searchRequest.source(searchSourceBuilder);

            SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("es查询出的数据 {}", JSON.toJSONString(search));
            //获取数据集合
            SearchHits hits = search.getHits();
            SearchHit[] hitsHits = hits.getHits();
            //转换数据
            List<HouseIndexTemplate> houseIndexTemplates = new ArrayList<>(hitsHits.length);
            Arrays.stream(hitsHits).forEach(searchHit -> {
                String asString = searchHit.getSourceAsString();
                HouseIndexTemplate houseIndexTemplate = JSON.parseObject(asString, HouseIndexTemplate.class);
                houseIndexTemplates.add(houseIndexTemplate);
            });

            return houseIndexTemplates;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("es 搜索出现异常", e);
        }
    }

    /**
     * 整理分页和排序参数
     *
     * @param queryBuilders
     * @param sortBuilders
     * @return
     */
    private SearchSourceBuilder getSearchSourceBuilder(Set<QueryBuilder> queryBuilders, Map<String, SortOrder> sortBuilders) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        //填充搜索参数
        queryBuilders.forEach((v) -> {
            boolQuery.filter(v);
        });
        //设置查询条件
        searchSourceBuilder.query(boolQuery)
                .from(0)
                //返回命中的数据
                .size(5)
                //查询类型
                .query(QueryBuilders.matchAllQuery());

        //排序
        if (sortBuilders != null) {
            sortBuilders.forEach((s, o) -> {
                searchSourceBuilder.sort(s, o);
            });
        }
        return searchSourceBuilder;
    }

    /**
     * @return com.fycstart.bass.ServiceMultiResult<java.lang.Integer>
     * @Author fyc
     * @Description // es  查询 house
     * @Date 下午 6:36 2019/5/7
     * @Param [rentSearch]
     **/
    @Override
    public ServiceMultiResult<Integer> query(RentSearch rentSearch) throws IOException {
        //处理排序
        Map<String, SortOrder> sortMap = new HashMap<>(2);
        Set<QueryBuilder> queryBuilders = new HashSet<>();
        //城市
        queryBuilders.add(QueryBuilders.termQuery(HouseIndexKey.CITY_EN_NAME, rentSearch.getCityEnName()));

        //区域
        if (rentSearch.getRegionEnName() != null && !"*".equals(rentSearch.getRegionEnName())) {
            queryBuilders.add(QueryBuilders.termQuery(HouseIndexKey.REGION_EN_NAME, rentSearch.getRegionEnName()));
        }

        //区域范围
        RentValueBlock area = RentValueBlock.matchArea(rentSearch.getAreaBlock());
        if (!RentValueBlock.ALL.equals(area)) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(HouseIndexKey.AREA);
            if (area.getMax() > 0) {
                rangeQueryBuilder.lte(area.getMax());
            }
            if (area.getMin() > 0) {
                rangeQueryBuilder.gte(area.getMin());
            }
            queryBuilders.add(rangeQueryBuilder);
        }
        //价格范围
        RentValueBlock price = RentValueBlock.matchPrice(rentSearch.getPriceBlock());
        if (!RentValueBlock.ALL.equals(price)) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(HouseIndexKey.PRICE);
            if (price.getMax() > 0) {
                rangeQuery.lte(price.getMax());
            }
            if (price.getMin() > 0) {
                rangeQuery.gte(price.getMin());
            }
            queryBuilders.add(rangeQuery);

        }
        //朝向
        if (rentSearch.getDirection() > 0) {
            queryBuilders.add(QueryBuilders.termQuery(HouseIndexKey.DIRECTION, rentSearch.getDirection()));
        }


        //出租方式
        if (rentSearch.getRentWay() > -1) {
            queryBuilders.add(QueryBuilders.termQuery(HouseIndexKey.RENT_WAY, rentSearch.getRentWay()));
        }

        //关键字查询
        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(rentSearch.getKeywords(),
                HouseIndexKey.TITLE,
                HouseIndexKey.TRAFFIC,
                HouseIndexKey.DISTRICT,
                HouseIndexKey.ROUND_SERVICE,
                HouseIndexKey.SUBWAY_LINE_NAME,
                HouseIndexKey.SUBWAY_STATION_NAME
        );
        queryBuilders.add(matchQueryBuilder);

        /**
         * 处理排序
         */
        sortMap.put(HouseSort.getSortKey(rentSearch.getEsOrderBy()), SortOrder.fromString(rentSearch.getOrderDirection()));

        List<HouseIndexTemplate> houseIndexTemplates = queryHouseIndexTemplate(queryBuilders, sortMap);
        List<Integer> houseIds = new ArrayList<>(houseIndexTemplates.size());
        houseIndexTemplates.forEach(houseIndexTemplate ->
                houseIds.add(houseIndexTemplate.getHouseId())
        );
        return new ServiceMultiResult<>(houseIds.size(), houseIds);

    }

}

