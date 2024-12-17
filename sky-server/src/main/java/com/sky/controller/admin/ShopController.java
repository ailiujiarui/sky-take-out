package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/shop")
@Api("管理端店铺相关接口")
public class ShopController {
    @Autowired
    ShopService shopService;


    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result  ShopStatus(@PathVariable int status){
        log.info("设置店铺状态为：{}",status==1?"营业中":"打烊中");
        shopService.setStatus(status);

        return Result.success();
    }
    @GetMapping("/status")
    public Result getShopStatus(){
        log.info("查询店铺状态");
        int status = shopService.getStatus();
        return Result.success(status);
    }
}
