package com.pinduoduo.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinduoduo.user.dao.UserMapper;
import com.pinduoduo.user.dto.UserDTO;
import com.pinduoduo.user.pojo.User;
import com.pinduoduo.user.pojo.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by SongLiang on 2019-12-25
 */
@Slf4j
@Component
@Service(interfaceClass = IUserService.class, loadbalance = "roundrobin")
public class UserService implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public String sayHello() {
        return "user service";
    }

    @Override
    public void login(String code) {
        return;
    }

    @Override
    public UserDTO findByOpenid(String openid) {
        log.info("findByOpenid, openid: {}", openid);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andOpenidEqualTo(openid);
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(users.get(0), userDTO);
        return userDTO;
    }

    @Override
    public void updateUserInfo(UserDTO userDTO) {
        log.info("userDTO: {}", userDTO);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        log.info("copied User: {}", user);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andOpenidEqualTo(user.getOpenid());
        userMapper.updateByExampleSelective(user, userExample);
    }
}
