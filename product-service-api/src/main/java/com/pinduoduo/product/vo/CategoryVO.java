package com.pinduoduo.product.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-11-28
 */
@Data
public class CategoryVO implements Serializable {
    String imageUrl;
    String linkUrl;
    String optName;
    Integer optId;
}
