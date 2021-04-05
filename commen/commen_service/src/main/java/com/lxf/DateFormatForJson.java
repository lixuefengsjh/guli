//package com.lxf;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//import static javax.print.attribute.standard.MediaPrintableArea.MM;
//
///**
// * @Description 解决springboot高版本下日期转json时jackson方式不生效问题
// */
//@Configuration
//public class DateFormatForJson extends WebMvcConfigurationSupport {
//    /**
//     * 使用此方法, 以下 spring-boot: jackson时间格式化 配置 将会失效
//     * spring.jackson.time-zone=GMT+8
//     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
//     * 原因: 会覆盖 @EnableAutoConfiguration 关于 WebMvcAutoConfiguration 的配置
//     */
//    @Bean//使用@Bean注入fastJsonHttpMessageConvert
//    public HttpMessageConverter fastJsonHttpMessageConverters(){
//        //1.需要定义一个Convert转换消息的对象
//        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
//        //2.添加fastjson的配置信息，比如是否要格式化返回的json数据
////
//        FastJsonConfig fastJsonConfig=new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        //3.在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter=fastConverter;
//        return converter;
//    }
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(fastJsonHttpMessageConverters());
//    }
//
//}
