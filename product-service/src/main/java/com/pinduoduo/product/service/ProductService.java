package com.pinduoduo.product.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinduoduo.facade.order.IProductService;
import com.pinduoduo.product.dao.CategoryMapper;
import com.pinduoduo.product.enums.CategoryParentIdEnum;
import com.pinduoduo.product.pojo.Category;
import com.pinduoduo.resp.product.CategoryVO;
import com.pinduoduo.resp.product.SearchCategoryVO;
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

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public String sayHello() {
        return "product say hello";
    }

    @Override
    public List<SearchCategoryVO> querySearchCategoryList() {
//        log.info("querySearchCategoryList");
//        CategoryExample categoryExample = new CategoryExample();
//        categoryExample.createCriteria().andParentIdEqualTo(0);
//        // 获取一级品类信息
//        List<Category> topCategoryList = categoryMapper.selectByExample(categoryExample);
//        log.info("topCategoryList: {}", topCategoryList);
//        //
//        List<SearchCategoryVO> result = new ArrayList<>();
//        for (Category category : topCategoryList) {
//            SearchCategoryVO searchCategoryVO = new SearchCategoryVO();
//            searchCategoryVO.setOptName(category.getName());
//            searchCategoryVO.setOptId(category.getId());
//            List<CategoryVO> categoryVOList = new ArrayList<>();
//            CategoryExample categoryExample2 = new CategoryExample();
//            categoryExample.createCriteria().andParentIdEqualTo(category.getId());
//            List<Category> level2 = categoryMapper.selectByExample(categoryExample2);
//            for (Category category2 : level2) {
//                CategoryExample categoryExample3 = new CategoryExample();
//                categoryExample3.createCriteria().andParentIdEqualTo(category2.getId());
//                List<Category> level3 = categoryMapper.selectByExample(categoryExample3);
//                for (Category category3 : level3) {
//                    CategoryVO categoryVO = new CategoryVO();
//                    categoryVO.setImageUrl(category3.getImgUrl());
//                    categoryVO.setOptId(category3.getId());
//                    categoryVO.setOptName(category3.getName());
//                    categoryVOList.add(categoryVO);
//                    log.info("categoryVO: {}", categoryVO);
//                }
//            }
//            searchCategoryVO.setChildren(categoryVOList);
//            result.add(searchCategoryVO);
//        }

        List<Category> categoryList = categoryMapper.findCategorySearchList();
//        log.info("categoryList: {}", categoryList);
        List<SearchCategoryVO> result = new ArrayList<>();
        Map<Integer, List<CategoryVO>> m = new HashMap<>();
        for (Category category : categoryList) {
            if (!category.getParentId().equals(CategoryParentIdEnum.TOP.getCode())) {
                CategoryVO categoryVO = new CategoryVO();
                categoryVO.setOptId(category.getId());
                categoryVO.setOptName(category.getName());
                categoryVO.setImageUrl(category.getImgUrl());
                categoryVO.setLinkUrl(null);
                if (m.containsKey(category.getParentId()) && m.get(category.getParentId()).size() < 11) {
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


}
