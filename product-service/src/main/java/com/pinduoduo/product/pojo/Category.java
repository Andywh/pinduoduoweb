package com.pinduoduo.product.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private Integer id;

    private String name;

    private Integer parentId;

    private Boolean ifParent;

    private Integer sort;

    private String imgUrl;
}