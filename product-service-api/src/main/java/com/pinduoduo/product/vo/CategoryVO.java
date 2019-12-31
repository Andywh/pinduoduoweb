package com.pinduoduo.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-11-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO implements Serializable {
    String imageUrl;
    String linkUrl;
    String optName;
    Integer optId;
}
