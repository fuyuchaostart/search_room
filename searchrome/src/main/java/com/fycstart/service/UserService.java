package com.fycstart.service;

import com.fycstart.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户基本信息表 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface UserService extends IService<User> {

    /**
     * @return com.fycstart.entity.User
     * @Author fyc
     * @Description // 获取用户信息
     * @Date 下午 4:36 2019/4/30
     * @Param [name]
     **/
    User findUserByUserName(String name);
}
