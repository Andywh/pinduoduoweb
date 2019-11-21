package com.pinduoduo.response;

import lombok.Data;

/**
 * Created by SongLiang on 2019-11-14
 */
@Data
public class ResultVO<T> {

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
