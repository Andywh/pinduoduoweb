package com.pinduoduo.product.service;

import com.pinduoduo.product.vo.CategoryVO;
import com.pinduoduo.product.vo.SearchCategoryVO;

import java.util.List;

/**
 * Created by SongLiang on 2019-12-05
 */
public interface IProductService {
    /**
     * 测试接口
     */
    String sayHello();

    /**
     * 获取搜索商品的一级及一级下钻的列表
     * @return
     */
    List<SearchCategoryVO> querySearchCategoryList();

    /**
     * 首页按0级品类 id 获取 一级下钻列表
     */
    List<CategoryVO> queryCategoryListAndPicByParentId(Integer parenetId);

    /**
     * 根据父类 id 获取下一级品类列表
     */
    List<CategoryVO> queryCategoryListByParentId(Integer parentId);
}
