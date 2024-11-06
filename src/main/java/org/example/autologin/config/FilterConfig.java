package org.example.autologin.config;

import org.example.autologin.utils.AutoLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置过滤器的配置类
@Configuration
public class FilterConfig {
    // 注册过滤器的 Bean
    @Bean
    public FilterRegistrationBean<AutoLoginFilter> autoLoginFilterRegistrationBean() {
        // 实例化过滤器
        FilterRegistrationBean<AutoLoginFilter> registrationBean = new FilterRegistrationBean<>();
        //设置过滤器的名称
        registrationBean.setFilter(new AutoLoginFilter());
        //设置过滤器的 URL 模式，这里设置为 /user/home
        registrationBean.addUrlPatterns("/user/home");
        //返回注册的过滤器 Bean
        return registrationBean;
    }
}