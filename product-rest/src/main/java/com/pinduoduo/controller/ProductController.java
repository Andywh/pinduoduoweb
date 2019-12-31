package com.pinduoduo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinduoduo.convert.BeanStrToList;
import com.pinduoduo.enums.ResponseEnum;
import com.pinduoduo.product.service.IProductService;
import com.pinduoduo.product.vo.CategoryVO;
import com.pinduoduo.redis.RedisClient;
import com.pinduoduo.response.Response;
import com.pinduoduo.response.ResultVO;
import com.pinduoduo.utils.ResponseUtil;
import com.pinduoduo.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by SongLiang on 2019-11-14
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private static final int expireSeconds = 60 * 60 * 24 * 2;

    @Autowired
    private RedisClient redisClient;

    @Reference(interfaceClass = IProductService.class, check = false)
    private IProductService productService;

    @RequestMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success(productService.sayHello());
    }

    @RequestMapping("/search")
    public ResultVO category() {
        return ResultVOUtil.success(productService.querySearchCategoryList());
    }

    @RequestMapping("/test")
    public ResultVO test() {
        return ResultVOUtil.success("这是一个测试接口ssss");
    }

    @RequestMapping(value = "/query_home/{id}", method = RequestMethod.GET)
    public Response queryWithImgByParentId(@PathVariable("id") Integer parentId) {
        if (parentId == null && parentId < 0) {
            return ResponseUtil.error(ResponseEnum.PARENTID_IS_REQUIRED);
        }
        log.info("id:{}", parentId);
        String key = "home_parent_id:" + String.valueOf(parentId);
        // 1.查询 reids 缓存
        String categoryList = redisClient.get(key);
        if (StringUtils.isNotBlank(categoryList)) {
            return ResponseUtil.success(BeanStrToList.convert(categoryList, CategoryVO.class));
        }
        // 2.查询数据库
        List<CategoryVO> categoryVOList = productService.queryCategoryListAndPicByParentId(parentId);
        // 3.
        redisClient.set(key, JSON.toJSONString(categoryVOList), expireSeconds);
        return ResponseUtil.success(productService.queryCategoryListAndPicByParentId(parentId));
    }

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Response queryByParentId(@PathVariable("id") Integer parentId) {
        if (parentId == null && parentId < 0) {
            return ResponseUtil.error(ResponseEnum.PARENTID_IS_REQUIRED);
        }
        log.info("id:{}", parentId);
        String key = "prent_id:" + String.valueOf(parentId);
        // 1.查询 reids 缓存
        String categoryList = redisClient.get(key);
        if (StringUtils.isNotBlank(categoryList)) {
            return ResponseUtil.success(BeanStrToList.convert(categoryList, CategoryVO.class));
        }
        // 2.查询数据库
        List<CategoryVO> categoryVOList = productService.queryCategoryListByParentId(parentId);
        // 3.
        if (!CollectionUtils.isEmpty(categoryVOList)) {
            redisClient.set(key, JSON.toJSONString(categoryVOList), expireSeconds);
        }
        return ResponseUtil.success(productService.queryCategoryListByParentId(parentId));
    }

}
