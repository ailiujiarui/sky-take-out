package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
用于创建aliossuitl对象
* */
@Configuration
@Slf4j
public class OssConfiguration {
    @Autowired
    private AliOssProperties aliOssProperties;

    @Bean
    @ConditionalOnMissingBean(AliOssUtil.class)//适用于上传不频繁的情况
    public AliOssUtil getAliOssUtil() {
        log.info("初始化阿里云对象");
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }

}
