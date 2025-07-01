package org.example.tlias.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 阿里云OSS配置类
// 读取配置文件中的阿里云OSS配置信息
// 并将其注入到AliyunOSSProperties对象中
// 通过@ConfigurationProperties注解，可以将配置文件中的属性映射到Java对象的属性上
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}