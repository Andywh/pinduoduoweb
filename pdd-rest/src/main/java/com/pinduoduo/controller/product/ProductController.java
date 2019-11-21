package com.pinduoduo.controller.product;

import com.pinduoduo.response.ResultVO;
import com.pinduoduo.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SongLiang on 2019-11-14
 */
@RestController
@RequestMapping("/product")
public class ProductController {


    @RequestMapping("/list")
    public ResultVO list() {
        return ResultVOUtil.success();
    }
}
