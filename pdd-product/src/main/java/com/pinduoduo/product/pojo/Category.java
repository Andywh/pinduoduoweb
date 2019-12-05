package com.pinduoduo.product.pojo;

import lombok.Data;

@Data
public class Category {
    private Integer id;

    private String name;

    private Integer parentId;

    private Boolean ifParent;

    private Integer sort;

    private String imgUrl;
}