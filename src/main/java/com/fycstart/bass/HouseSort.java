package com.fycstart.bass;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 排序生成器
 * Created by 瓦力.
 */
public class HouseSort {
    public static final String DEFAULT_SORT_KEY = "last_update_time";
    public static final String ES_DEFAULT_SORT_KEY = "lastUpdateTime";

    public static final String DISTANCE_TO_SUBWAY_KEY = "distance_to_subway";


    private static final Set<String> SORT_KEYS = Sets.newHashSet(
            DEFAULT_SORT_KEY,
            "create_Time",
            "price",
            "area",
            DISTANCE_TO_SUBWAY_KEY,
            ES_DEFAULT_SORT_KEY
    );

    public static String getSortKey(String key) {
        if (!SORT_KEYS.contains(key)) {
            key = DEFAULT_SORT_KEY;
        }

        return key;
    }
}
