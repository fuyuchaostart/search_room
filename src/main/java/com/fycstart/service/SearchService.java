package com.fycstart.service;

import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.search.HouseIndexTemplate;
import com.fycstart.web.form.RentSearch;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/6上午 11:56
 */
public interface SearchService {
    ServiceMultiResult<Integer> query(RentSearch rentSearch) throws IOException;

    Boolean create(HouseIndexTemplate houseIndexTemplate) throws IOException;

    Boolean deleteOrCreate(HouseIndexTemplate houseIndexTemplate) throws IOException;

    Boolean update(HouseIndexTemplate houseIndexTemplate) throws IOException;

    Boolean remove(String houseId) throws IOException;

    Boolean paddingIndex(String houseId) throws IOException;

    List<HouseIndexTemplate> queryHouseIndexTemplate(Set<QueryBuilder> set, Map<String, SortOrder> sortMap) throws IOException;

}
