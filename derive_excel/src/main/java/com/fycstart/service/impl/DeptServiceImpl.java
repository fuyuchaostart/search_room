package com.fycstart.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.entity.Dept;
import com.fycstart.entity.dto.DeptExDto;
import com.fycstart.entity.req.DeptReq;
import com.fycstart.mapper.DeptMapper;
import com.fycstart.service.DeptService;
import com.fycstart.utils.PoiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-05-21
 */
@Service

public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    @Autowired
    private DeptMapper deptMapper;

    @Override
    @Async
    public void export(DeptReq dept, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        Integer count = 0;
        ExcelWriter writer;
        try {
            writer = PoiUtil.getExcelWriter("test", response);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取ExcelWriter实例错误 {}", e.getMessage());
            return;
        }

        //查询条件
        QueryWrapper<Dept> queryWrapper = new QueryWrapper();
        queryWrapper.lt("id", 1000000);
        //count = deptMapper.selectCount(queryWrapper);

        Integer number = 1;
        while (true) {
            long startQuery = System.currentTimeMillis();
            Page page = new Page(number, 1000000);
            IPage iPage = deptMapper.selectPage(page, queryWrapper);
            long endQuery = System.currentTimeMillis();
            logger.info("查询数据使用时间 {}", (endQuery - startQuery));
            if (iPage.getRecords().size() < 1) {
                break;
            }
            List<Dept> records = iPage.getRecords();

            //转换
            List<DeptExDto> collect = records.stream().map(item -> {
                DeptExDto deptDto = new DeptExDto();
                BeanUtils.copyProperties(item, deptDto);
                return deptDto;
            }).collect(Collectors.toList());
            try {

                //处理数据
//                List<List<Object>> dataList = ListUtils.entityListToModelList(collect);
//                if (number == 1) {
//                    dataList.add(0, ExcelConstant.titleNames);
//                }
//                //导出
//                PoiUtil.easyExcelDerive(writer, dataList);
                PoiUtil.easyExcelBeanDerive(writer, collect);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("导出数据发生异常 {}", e.getMessage());
            }
            number++;
        }
        //刷缓存池
        writer.finish();
        long end = System.currentTimeMillis();
        logger.info("导出完成：数据量:{} 用时:{}", count, (end - start));
    }
}
