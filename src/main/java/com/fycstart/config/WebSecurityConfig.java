package com.fycstart.config;

import com.fycstart.security.AuthProvider;
import com.fycstart.security.LoginAuthFailHandler;
import com.fycstart.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


/**
 * @author fyc
 * @description: TODO
 * @date 2019/4/30下午 3:17
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //管理员登录入口
                .antMatchers("/admin/login").permitAll()
                //静态资源
                .antMatchers("/static/**").permitAll()
                //用户登录入口
                .antMatchers("/user/login").permitAll()
                //管理员用户才可以访问
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/user/api/**").hasAnyRole("ADMIN", "USER")
                .and()
                // 配置角色登录处理入口
                .formLogin()
                .loginProcessingUrl("/login")
                .failureHandler(authFailHandler())
                .and()
                //登出
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                //区分用户登录和管理员登录
                .exceptionHandling()
                .authenticationEntryPoint(urlEntryPoint())
                .accessDeniedPage("/403");

        ;
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

    }

    /**
     * 登录失败处理
     *
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authFailHandler() {
        LoginAuthFailHandler loginAuthFailHandler = new LoginAuthFailHandler(urlEntryPoint());
        return loginAuthFailHandler;
    }

    @Bean
    public LoginUrlEntryPoint urlEntryPoint() {

        return new LoginUrlEntryPoint("/user/login");
    }

    /**
     * 自定义认证策略
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder builder) {
        //验证和擦除密码
        builder.authenticationProvider(authProvide()).eraseCredentials(true);
    }

    /**
     * 自定义认证个过程
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authProvide() {
        return new AuthProvider();
    }
}
