package com.fycstart.constant;

import com.fycstart.entity.dto.DeptDto;
import com.fycstart.utils.HandleBeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fyc
 * @description: 常量类
 * @date 2019/5/22上午 10:38
 */
public class ExcelConstant {

    /**
     * 每个sheet存储的记录数 100W
     */
    public static final Integer PER_SHEET_ROW_COUNT = 1000000;

    /**
     * 每次向EXCEL写入的记录数(查询每页数据大小) 20W
     */
    public static final Integer PER_WRITE_ROW_COUNT = 200000;


    /**
     * 每个sheet的写入次数 5
     */
    public static final Integer PER_SHEET_WRITE_COUNT = PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;


    public static final List titleNames;

    static {

        titleNames = new ArrayList<>();
        List<String> attr = HandleBeanUtils.entityAttributeNamesToList(DeptDto.class);
        titleNames.addAll(attr);
    }
}
