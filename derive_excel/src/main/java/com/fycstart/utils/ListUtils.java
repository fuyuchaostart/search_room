package com.fycstart.utils;

import com.fycstart.entity.dto.DeptDto;
import com.fycstart.entity.dto.TempDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22上午 11:44
 */
public class ListUtils {


    private static final Logger logger = LoggerFactory.getLogger(ListUtils.class);

    /**
     * 将entityList转换成objlList
     *
     * @param fromList
     * @param <F>
     * @return
     */
    public static <F> List<List<Object>> entityListToModelList(List<F> fromList) {
        if (fromList.isEmpty() || fromList == null) {
            return null;
        }
        List<List<Object>> tList = new ArrayList<>();
        for (F f : fromList) {
            List<Object> objects = HandleBeanUtils.entityToList(f);
            tList.add(objects);
        }
        return tList;
    }


    public static void main(String[] args) {
        DeptDto dept = new DeptDto();
        dept.setAddress("北京");
        dept.setCeo(123);
        dept.setDeptName("运输部");
        List<DeptDto> depts = new ArrayList<>();
        depts.add(dept);
        List<List<Object>> lists = entityListToModelList(depts);

        System.out.println(lists);
    }

    @Test
    public void testFun1() {

        TempDto tempDto = new TempDto();
        tempDto.setAge(34);
        tempDto.setDeptId(1200);
        tempDto.setId(12);
        tempDto.setName("张三");
        TempDto tempDto2 = new TempDto();
        tempDto2.setEmpno(32);
        tempDto2.setDeptId(1234);
        tempDto2.setName("李四");

        List<TempDto> tempDtos = new ArrayList<>();
        tempDtos.add(tempDto);
        tempDtos.add(tempDto2);
        List<List<Object>> lists = entityListToModelList(tempDtos);
        System.out.println(lists);

    }
}
