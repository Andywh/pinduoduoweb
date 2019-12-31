package com.pinduoduo.convert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
        JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, clazz));
        }
        return list;
    }
}
