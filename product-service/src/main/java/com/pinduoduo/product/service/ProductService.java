package com.pinduoduo.product.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.pinduoduo.convert.BeanStrToList;
import com.pinduoduo.product.dao.CategoryMapper;
import com.pinduoduo.product.enums.CategoryParentIdEnum;
import com.pinduoduo.product.pojo.Category;
import com.pinduoduo.product.pojo.CategoryExample;
import com.pinduoduo.product.redis.RedisClient;
import com.pinduoduo.product.vo.CategoryVO;
import com.pinduoduo.product.vo.SearchCategoryVO;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SongLiang on 2019-11-14
 */
@Slf4j
@Component
@Service(interfaceClass = IProductService.class, loadbalance = "roundrobin")
public class ProductService implements IProductService {

    private static final int expireSeconds = 60 * 60 * 24 * 2;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public String sayHello() {
        return "product say hello";
    }

    @Override
    public List<SearchCategoryVO> querySearchCategoryList() {
        String key = "search:category_list";
        // 1.查询 reids 缓存
        String categoryListStr = redisClient.get(key);
        log.info("categoryListStr redisValue: {}",categoryListStr);
        List<Category> categoryList = new ArrayList<>();
        if (StringUtils.isNotBlank(categoryListStr)) {
            categoryList = BeanStrToList.convert(categoryListStr, Category.class);
        } else {
            categoryList = categoryMapper.findCategorySearchList();
            redisClient.set(key, JSON.toJSONString(categoryList), expireSeconds);
        }
        List<SearchCategoryVO> result = new ArrayList<>();
        Map<Integer, List<CategoryVO>> m = new HashMap<>();
        for (Category category : categoryList) {
            if (!category.getParentId().equals(CategoryParentIdEnum.TOP.getCode())) {
                CategoryVO categoryVO = new CategoryVO();
                categoryVO.setOptId(category.getId());
                categoryVO.setOptName(category.getName());
                categoryVO.setImageUrl(category.getImgUrl());
                categoryVO.setLinkUrl(null);
                if (m.containsKey(category.getParentId())) {
                    m.get(category.getParentId()).add(categoryVO);
                } else if (!m.containsKey(category.getParentId())) {
                    List<CategoryVO> categoryVOList = new ArrayList<>();
                    categoryVOList.add(categoryVO);
                    m.put(category.getParentId(), categoryVOList);
                }
            }
        }
        log.info("m: {}", m);
        for (Category category : categoryList) {
            if (category.getParentId().equals(CategoryParentIdEnum.TOP.getCode())) {
                SearchCategoryVO searchCategoryVO = new SearchCategoryVO();
                searchCategoryVO.setOptId(category.getId());
                searchCategoryVO.setOptName(category.getName());
                searchCategoryVO.setImageUrl(category.getImgUrl());
                searchCategoryVO.setChildren(m.get(category.getId()));
                result.add(searchCategoryVO);
            }
        }
        log.info("result: {}", result);
        return result;
    }

    @Override
    public List<CategoryVO> queryCategoryListAndPicByParentId(Integer parentId) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId).andSortEqualTo(0);
        List<Category> categoryList = categoryMapper.selectByExample(example);
        List<CategoryVO> categoryVOList = new ArrayList<>();
        categoryList.forEach(i -> categoryVOList.add(
                new CategoryVO(i.getImgUrl(), null, i.getName(), i.getId())
        ));
        return categoryVOList;
    }

    @Override
    public List<CategoryVO> queryCategoryListByParentId(Integer parentId) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<Category> categoryList = categoryMapper.selectByExample(example);
        List<CategoryVO> categoryVOList = new ArrayList<>();
        categoryList.forEach(i -> categoryVOList.add(
                new CategoryVO(i.getImgUrl(), null, i.getName(), i.getId())
        ));
        return categoryVOList;
    }


}
