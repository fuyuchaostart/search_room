package com.fycstart.security;

import com.fycstart.entity.User;
import com.fycstart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author fyc
 * @description: 自定义认证
 * @date 2019/4/30下午 3:47
 */
public class AuthProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(AuthProvider.class);


    @Autowired
    private UserService userService;

    private final PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    //验证
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String inPassword = (String) authentication.getCredentials();

        String encode = delegatingPasswordEncoder.encode(inPassword);
        LOG.info("原始密码:{} 加密后的密码:{}", inPassword, encode);

        User user = userService.findUserByUserName(name);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("authError");
        }

        boolean matches = delegatingPasswordEncoder.matches(inPassword, user.getPassword());
        if (matches) {
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }

        throw new BadCredentialsException("authError");
    }

    /**
     * 支持所有的认证类
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
