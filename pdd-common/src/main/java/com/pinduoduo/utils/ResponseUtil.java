package com.pinduoduo.utils;

import com.pinduoduo.enums.ResponseEnum;
import com.pinduoduo.response.Response;

/**
 * Created by SongLiang on 2019-12-13
 */
public class ResponseUtil {

    public static Response success() {
        Response res = new Response();
        res.setCode(0);
        res.setMsg("成功");
        return res;
    }

    public static Response success(Object obj) {
        Response res = new Response();
        res.setCode(0);
        res.setMsg("成功");
        res.setData(obj);
        return res;
    }

    public static Response error(ResponseEnum responseEnum) {
        Response res = new Response();
        res.setCode(responseEnum.getCode());
        res.setMsg(responseEnum.getMessage());
        return res;
    }
}
