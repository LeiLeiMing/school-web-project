package com.secondhand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by LeiMing on 2020/3/3 22:02
 */
@FeignClient("auth-service")
public interface CartClient {
    @GetMapping("auth/userinfo")
    public Map getUserInfo(@RequestParam("token")String token);
}
