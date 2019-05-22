package com.fycstart.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/4/30上午 9:34
 * <p>
 * 静态资源加载配置
 * @return org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
 * @Author fyc
 * @Description //配置模板引擎
 * @Date 上午 9:43 2019/4/30
 * @Param []
 * @return org.thymeleaf.spring5.SpringTemplateEngine
 * @Author fyc
 * @Description // Thymeleaf标准方言解释器
 * @Date 上午 9:45 2019/4/30
 * @Param []
 * @return org.thymeleaf.spring5.view.ThymeleafViewResolver
 * @Author fyc
 * @Description //配置视图解析器
 * @Date 上午 9:48 2019/4/30
 * @Param []
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    @Value("${spring.thymeleaf.cache}")
    private boolean thymeleafCacheEnable = true;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 静态资源加载配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/templates/admin/**")
                .addResourceLocations("classpath:/static/", "classpath:/templates/admin/");
    }


    /**
     * @return org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
     * @Author fyc
     * @Description //配置模板引擎
     * @Date 上午 9:43 2019/4/30
     * @Param []
     **/

    @Bean
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(thymeleafCacheEnable);
        return templateResolver;
    }


    /**
     * @return org.thymeleaf.spring5.SpringTemplateEngine
     * @Author fyc
     * @Description // Thymeleaf标准方言解释器
     * @Date 上午 9:45 2019/4/30
     * @Param []
     **/

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        springTemplateEngine.setTemplateResolver(templateResolver());
        //支持el表达式
        springTemplateEngine.setEnableSpringELCompiler(true);

        // 支持SpringSecurity方言
        SpringSecurityDialect securityDialect = new SpringSecurityDialect();
        springTemplateEngine.addDialect(securityDialect);
        return springTemplateEngine;
    }

    /**
     * @return org.thymeleaf.spring5.view.ThymeleafViewResolver
     * @Author fyc
     * @Description //配置视图解析器
     * @Date 上午 9:48 2019/4/30
     * @Param []
     **/
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine());
        thymeleafViewResolver.setCharacterEncoding("UTF-8");
        return thymeleafViewResolver;
    }
}

