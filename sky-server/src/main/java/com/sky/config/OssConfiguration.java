package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 */
/*@Configuration*/
/*
@Slf4j
public class OssConfiguration {
    @Bean //用来创建第三方的bean类
    public AliOssUtil aliOssProperties(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云上传文件工具类对象");
        return  new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}
*/
