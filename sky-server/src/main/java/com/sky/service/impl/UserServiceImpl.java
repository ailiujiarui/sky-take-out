package com.sky.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;

import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;

    //微信登录
    public User wxlogin(UserLoginDTO userLoginDTO) {
        //构建参数并请求
        Map<String,String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("grant_type","authorization_code");
        map.put("js_code",userLoginDTO.getCode());
        String json = HttpClientUtil.doGet(WX_LOGIN,map);
        //解析请求结果，获得openid
        JSONObject jsonObject = JSON.parseObject(json);
       String openid = jsonObject.getString("openid");
       //判断是否登录成功
        if(openid==null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //看看用户是否注册，新用户自动注册
        User user = userMapper.getUserByopenId(openid);
        if(user==null){
            user=User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }

        return user;
    }
}
