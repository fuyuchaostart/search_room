package com.fycstart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.entity.Dept;
import com.fycstart.entity.req.DeptReq;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-05-21
 */
public interface DeptService extends IService<Dept> {
    /**
     * @return com.fycstart.bass.Result<java.lang.Void>
     * @Author fyc
     * @Description //导出数据
     * @Date 下午 2:52 2019/5/22
     * @Param [dept, response]
     **/
    void export(DeptReq dept, HttpServletResponse response);
}
