package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api("用户端店铺相关接口")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    public Result getShopStatus(){
        log.info("查询店铺状态");
        int status = shopService.getStatus();
        return Result.success(status);
    }
}
