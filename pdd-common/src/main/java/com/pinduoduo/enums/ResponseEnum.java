package com.pinduoduo.enums;

import lombok.Getter;

/**
 * Created by SongLiang on 2019-12-13
 */
@Getter
public enum ResponseEnum {

    PARAMS_IS_INVALID(1000, "参数错误"),
    PARENTID_IS_REQUIRED(1001, "parent_id is required"),
    LOGIN_FAIL(1002, "登录失败"),
    UN_LOGIN(1003, "用户未登录"),
    PARSE_ERROR(1004, "缓存解析错误"),
    ;

    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
