package com.etoak.config;

import com.etoak.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.mapping}")
    private String imgMapping;

    @Value("${upload.dir}")
    private String imgLocation;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imgMapping)
                .addResourceLocations("file:" + imgLocation);

    }
    //添加拦截器
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**") // 不拦截跳转到登录页面的请求、登录请求、跳转到注册页面、注册、校验用户名
                .excludePathPatterns("/code") // 不拦截验证码
                .excludePathPatterns("/lib/**", "/imgs/**"); // 不拦截静态资源


    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/") // 访问contextPath
                .setViewName("index");
    }
}
