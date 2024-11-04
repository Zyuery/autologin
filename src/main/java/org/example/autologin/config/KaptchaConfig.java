package org.example.autologin.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
// 验证码配置类
@Configuration
// 配置类，用于生成验证码
public class KaptchaConfig {
    @Bean
    public Producer kaptcha() {
        // 配置验证码参数
        Properties properties = new Properties();
        // 验证码图片的宽度和高度
        properties.setProperty("kaptcha.image.width", "150");
        // 验证码图片的宽度和高度
        properties.setProperty("kaptcha.image.height", "50");
        // 验证码图片的字体
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        // 验证码图片的字体大小
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 创建验证码生成器
        Config config = new Config(properties);
        // 生成验证码
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 配置验证码生成器
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
