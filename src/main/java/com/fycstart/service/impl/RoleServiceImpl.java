package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fycstart.entity.Role;
import com.fycstart.entity.User;
import com.fycstart.mapper.RoleMapper;
import com.fycstart.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByUserId(Integer userId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(userId != null, "user_id", userId);
        List<Role> list = roleMapper.selectList(queryWrapper);
        return list;
    }
}
