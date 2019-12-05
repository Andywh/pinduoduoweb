package com.pinduoduo.product.service;

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

}
