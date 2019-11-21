package com.pinduoduo.product.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinduoduo.facade.order.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by SongLiang on 2019-11-14
 */
@Slf4j
@Component
@Service(interfaceClass = IProductService.class, loadbalance = "roundrobin")
public class ProductService implements IProductService {

    @Override
    public String sayHello() {
        return "product say hello";
    }

}
