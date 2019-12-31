package com.pinduoduo.exception;

import com.pinduoduo.enums.ResponseEnum;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-12-13
 */
public class ProductException extends RuntimeException implements Serializable {

    private Integer code;

    public ProductException() {
    }

    public ProductException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = code;
    }

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
