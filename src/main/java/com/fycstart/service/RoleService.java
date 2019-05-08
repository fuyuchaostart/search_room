package com.fycstart.service;

import com.fycstart.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface RoleService extends IService<Role> {

    List<Role> findRolesByUserId(Integer id);
}
