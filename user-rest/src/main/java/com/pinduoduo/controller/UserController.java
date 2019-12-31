package com.pinduoduo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinduoduo.dto.WeixinInfo;
import com.pinduoduo.enums.ResponseEnum;
import com.pinduoduo.redis.RedisClient;
import com.pinduoduo.response.Response;
import com.pinduoduo.user.dto.UserDTO;
import com.pinduoduo.user.service.IUserService;
import com.pinduoduo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;

/**
 * Created by SongLiang on 2019-12-26
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final int expireSeconds = 60 * 60 * 24 * 2;

    @Reference(interfaceClass = IUserService.class, check = false)
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/test")
    public Response test() {
        return ResponseUtil.success("这是一个测试接口");
    }

    @RequestMapping(value = "/list")
    public Response list() {
        return ResponseUtil.success(userService.sayHello());
    }

    @RequestMapping(value = "/login/{code}", method = RequestMethod.GET)
    public Response login(@PathVariable("code") String code) {
        Servlet servlet;
        GenericServlet genericServlet;
        HttpServlet httpServlet;
        if (StringUtils.isBlank(code)) {
            return ResponseUtil.error(ResponseEnum.PARENTID_IS_REQUIRED);
        }
        log.info("code: {}", code);
        // 1. 从微信处获取 openid
        String openid = getOpenId(code);
        log.info("openid: {}", openid);
        // 2. 从本地数据库获取该用户信息
        UserDTO userDTO = userService.findByOpenid(openid);
        if (userDTO == null) {
            return ResponseUtil.error(ResponseEnum.LOGIN_FAIL);
        }

        // 3. 生成 token
        String token = genToken();

        // 3. 缓存用户
        String key = "token:" + token;
        redisClient.set(key, JSON.toJSONString(userDTO), expireSeconds);
        return ResponseUtil.success(token);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updateUserInfo(HttpServletRequest request, UserDTO userDTO) {
        log.info("user: {}", userDTO);
        // 1. 获取 header 中携带的 token
        String token = request.getHeader("token");
        if (token == null) {
            return ResponseUtil.error(ResponseEnum.UN_LOGIN);
        }
        // 2. 获取用户信息
        String key = "token:" + token;
        String cachedUserStr = redisClient.get(key);
        log.info("cachedUserStr: {}", cachedUserStr);
        UserDTO cachedUser = null;
        try {
            cachedUser = objectMapper.readValue(cachedUserStr, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.error(ResponseEnum.PARSE_ERROR);
        }
        log.info("cacedUser: {}", cachedUser);
        userDTO.setOpenid(cachedUser.getOpenid());
        userService.updateUserInfo(userDTO);
        return ResponseUtil.success();
    }

    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx56909b1203a2fc03&secret=d1d7410bebaf95041584bc6de1dd41a7&js_code="+ code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);
        try {
            WeixinInfo weixinInfo = objectMapper.readValue(response, WeixinInfo.class);
            return weixinInfo.getOpenid();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }

}
