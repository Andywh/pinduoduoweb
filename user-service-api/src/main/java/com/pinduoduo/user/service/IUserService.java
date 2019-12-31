package com.pinduoduo.user.service;

import com.pinduoduo.user.dto.UserDTO;

/**
 * Created by SongLiang on 2019-12-25
 */
public interface IUserService {
    /**
     * 测试接口
     */
    String sayHello();

    /**
     * 微信小程序
     * 登录接口
     */
    void login(String code);

    /**
     * 通过 openid 来查找
     */
    UserDTO findByOpenid(String openid);

    /**
     * 更新用户信息
     */
    void updateUserInfo(UserDTO userDTO);

}
