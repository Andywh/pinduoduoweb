package com.pinduoduo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-12-13
 */
@Data
public class Response<T> implements Serializable {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;

}
