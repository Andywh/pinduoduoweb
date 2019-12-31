package com.pinduoduo.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by SongLiang on 2019-12-05
 */
@SpringBootApplication
@EnableZuulProxy
@EnableConfigurationProperties
public class ZuulApplication {

    public static void main(String args[]) {
        SpringApplication.run(ZuulApplication.class, args);
    }

}
