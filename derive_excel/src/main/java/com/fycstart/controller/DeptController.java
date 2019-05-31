package com.fycstart.controller;


import com.fycstart.entity.req.DeptReq;
import com.fycstart.service.DeptService;
import com.fycstart.utils.PoiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fycstart
 * @since 2019-05-21
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    @Autowired
    private DeptService deptService;

    @GetMapping("/export")
    public void deriveExcel(DeptReq deptReq, HttpServletResponse httpServletResponse) {

        deptService.export(deptReq, httpServletResponse);

        logger.info("正在下载.请稍后");
    }
}

