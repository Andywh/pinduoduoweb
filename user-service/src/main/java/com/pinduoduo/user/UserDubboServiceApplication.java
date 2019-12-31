package com.pinduoduo.user;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by SongLiang on 2019-12-25
 */
@SpringBootApplication
@EnableDubboConfiguration
@MapperScan(basePackages = "com.pinduoduo.user.dao")
public class UserDubboServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDubboServiceApplication.class, args);
    }

}
