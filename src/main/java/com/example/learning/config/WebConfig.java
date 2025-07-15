package com.example.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 用于配置Spring MVC的一些全局设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置全局的日期时间格式化器
     * 这样所有的日期时间字段都会使用统一的格式进行转换
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        // 设置日期格式为 yyyy-MM-dd
        registrar.setDateFormatter(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // 设置时间格式为 HH:mm:ss
        registrar.setTimeFormatter(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        // 设置日期时间格式为 yyyy-MM-dd HH:mm:ss
        registrar.setDateTimeFormatter(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        registrar.registerFormatters(registry);
    }
} 