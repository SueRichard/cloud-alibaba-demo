package com.hh.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author HH
 * @version 1.0
 * @time 2024/02/01  Thu  14:23
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * nacos 内置了ribbon 负载均衡
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
