package com.pinduoduo.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pinduoduo.enums.ResponseEnum;
import com.pinduoduo.exception.ProductException;
import com.pinduoduo.product.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongLiang on 2019-12-13
 */
@Slf4j
public class CategoryVOListStr2CategoryVOList {

    public static List<CategoryVO> convert(String str) {
        Gson gson = new Gson();

        List<CategoryVO> categoryVOList = new ArrayList<>();
        try {
            categoryVOList = gson.fromJson(str,
                    new TypeToken<List<CategoryVO>>(){
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", str);
            throw new ProductException(ResponseEnum.PARENTID_IS_REQUIRED);
        }
        return categoryVOList;

    }
}
