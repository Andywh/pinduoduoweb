package com.pinduoduo.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pinduoduo.enums.ResponseEnum;
import com.pinduoduo.exception.ProductException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongLiang on 2019-12-13
 */
@Slf4j
public class BeanStrToList {

    public static <T> List<T> convert(String jsonStr, Class<T> clazz) {

        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            list = gson.fromJson(jsonStr,
                    new TypeToken<List<T>>(){
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", jsonStr);
            throw new ProductException(ResponseEnum.PARENTID_IS_REQUIRED);
        }
        return list;
    }
}
