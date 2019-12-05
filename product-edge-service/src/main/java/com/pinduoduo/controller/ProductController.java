package com.pinduoduo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinduoduo.product.service.IProductService;
import com.pinduoduo.response.ResultVO;
import com.pinduoduo.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SongLiang on 2019-11-14
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Reference(interfaceClass = IProductService.class, check = false)
    private IProductService productService;

    @RequestMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success(productService.sayHello());
    }

    @RequestMapping("/search")
    public ResultVO category() {
        log.info("search");
        return ResultVOUtil.success(productService.querySearchCategoryList());
    }

    @RequestMapping("/test")
    public ResultVO test() {
        log.info("search");
        return ResultVOUtil.success("这是一个测试接口ssss");
    }


}
