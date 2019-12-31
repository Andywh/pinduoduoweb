package com.pinduoduo.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by SongLiang on 2019-12-26
 */
@Data
public class UserDTO implements Serializable {

    private Long pdduid;

    private String openid;

    private String nickName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

}
