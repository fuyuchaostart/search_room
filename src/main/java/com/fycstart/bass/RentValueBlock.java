package com.fycstart.bass;

import com.google.common.collect.ImmutableMap;

/**
 * @author fyc
 * @description: 处理区间
 * @date 2019/5/6上午 11:03
 */
public class RentValueBlock {
    /**
     * 价格区间
     */
    public static final ImmutableMap<String, RentValueBlock> PRICE_BLOCK;


    /**
     * 区域区间
     */
    public static final ImmutableMap<String, RentValueBlock> AREA_BLOCK;

    /**
     * 无限制区间
     */
    public static final RentValueBlock ALL = new RentValueBlock("*", -1, -1);

    /**
     * 初始化数据
     */
    static {
        PRICE_BLOCK = ImmutableMap.<String, RentValueBlock>builder()
                .put("*-1000", new RentValueBlock("*-1000", -1, 1000))
                .put("1000-3000", new RentValueBlock("1000-3000", 1000, 3000))
                .put("3000-*", new RentValueBlock("3000-*", 3000, -1))
                .put("*", ALL)
                .build();

        AREA_BLOCK = ImmutableMap.<String, RentValueBlock>builder()
                .put("*-30", new RentValueBlock("*-30", -1, 30))
                .put("30-50", new RentValueBlock("30-50", 30, 50))
                .put("50-*", new RentValueBlock("50-*", 50, -1))
                .put("*", ALL)
                .build();
    }


    private String key;
    private int min;
    private int max;

    public RentValueBlock() {
    }

    public RentValueBlock(String key, int min, int max) {
        this.key = key;
        this.min = min;
        this.max = max;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * 获取价格区间实体
     *
     * @param key
     * @return
     */
    public static RentValueBlock matchPrice(String key) {
        if (key == null) {
            return ALL;
        }
        return PRICE_BLOCK.get(key);
    }

    /**
     * 获取区域区间
     *
     * @param key
     * @return
     */
    public static RentValueBlock matchArea(String key) {
        if (key == null) {
            return ALL;
        }
        return AREA_BLOCK.get(key);
    }
}
