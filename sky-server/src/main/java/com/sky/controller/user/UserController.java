package com.sky.controller.user;


import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.sky.constant.JwtClaimsConstant.USER_ID;

@Api("用户模块")
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> wxlogin(@RequestBody UserLoginDTO userLoginDTO) {
        //微信登录获取openid
        User wxlogin = userService.wxlogin(userLoginDTO);
       //构建jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, wxlogin.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        //构建VO并返回
        UserLoginVO userLoginVO = UserLoginVO.builder().id(wxlogin.getId()).openid(wxlogin.getOpenid()).token(jwt).build();

        return Result.success(userLoginVO);
    }
}
