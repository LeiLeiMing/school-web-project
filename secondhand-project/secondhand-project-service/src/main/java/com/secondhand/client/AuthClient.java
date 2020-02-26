package com.secondhand.client;

import com.secondhand.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by LeiMing on 2020/2/26 20:53
 */
@FeignClient("auth-service")
public interface AuthClient {
    @GetMapping("auth/userinfo")
    public Map getUserInfo(@RequestParam("token")String token);
}
