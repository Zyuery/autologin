//package org.example.autologin.config;
//
//import org.example.autologin.utils.AutoLoginFilter;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//// 配置过滤器的配置类
//@Configuration
//public class FilterConfig {
//    // 注册过滤器的 Bean
//    @Bean
//    public FilterRegistrationBean<AutoLoginFilter> autoLoginFilterRegistrationBean() {
//        // 实例化过滤器
//        FilterRegistrationBean<AutoLoginFilter> registrationBean = new FilterRegistrationBean<>();
//        // 设置过滤器的名称
//        registrationBean.setFilter(new AutoLoginFilter());
//        // 设置过滤器的顺序
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
//}