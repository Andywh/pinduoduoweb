package com.pinduoduo.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SongLiang on 2019-11-28
 */
@Data
public class SearchCategoryVO implements Serializable {
    String imageUrl;
    List<CategoryVO> children;
    String optName;
    Integer optId;
}
