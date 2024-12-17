package com.sky.service.impl;

import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setStatus(int status) {
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
    }

    public int getStatus() {
        //返回值是object类型，所以要用integer强转，再自动拆箱
        return (Integer)redisTemplate.opsForValue().get("SHOP_STATUS");
    }
}
