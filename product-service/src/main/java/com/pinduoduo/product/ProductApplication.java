package com.pinduoduo.product;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by SongLiang on 2019-11-14
 */
@SpringBootApplication
@EnableDubboConfiguration
@MapperScan(basePackages = "com.pinduoduo.product.dao")
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
