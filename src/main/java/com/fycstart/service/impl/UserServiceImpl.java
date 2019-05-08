package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.fycstart.entity.Role;
import com.fycstart.entity.User;
import com.fycstart.mapper.UserMapper;
import com.fycstart.service.RoleService;
import com.fycstart.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RoleService roleService;

    @Override
    public User findUserByUserName(String name) {
        QueryWrapper query = new QueryWrapper();

        query.eq(name != null, "name", name);

        User user = userMapper.selectOne(query);
        if (user == null) {
            return null;
        }

        List<Role> roles = roleService.findRolesByUserId(user.getId());

        if (roles == null || roles.isEmpty()) {
            throw new DisabledException("权限非法");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        user.setGrantedAuthorities(authorities);
        return user;
    }
}
