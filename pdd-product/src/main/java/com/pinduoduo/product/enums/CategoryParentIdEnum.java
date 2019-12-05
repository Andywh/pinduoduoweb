package com.pinduoduo.product.enums;

import lombok.Getter;

/**
 * Created by SongLiang on 2019-11-28
 */
@Getter
public enum CategoryParentIdEnum {

    TOP(0, "一级品类的上级id");

    private Integer code;

    private String message;

    CategoryParentIdEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
