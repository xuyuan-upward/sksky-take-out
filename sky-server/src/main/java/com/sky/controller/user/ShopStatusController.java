package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopStatusController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopStatusController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取营业状态
     */
    @GetMapping("/status")
    public Result<Integer> GetStatus(){
        Integer shop_status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取营业状态：{}",shop_status);
        return Result.success(shop_status);
    }


}
