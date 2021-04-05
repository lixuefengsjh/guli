package com.lxf.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class OssConfig {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    @Value("${oss.endPoint}")
    private String endpoint;

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    @Value("${oss.accessKeyId}")
    private String accessKeyId ;

    @Value("${oss.accessKeyScret}")
    private String accessKeySecret ;

    @Value("${oss.bucketName}")
    private String bucketName ;
}
