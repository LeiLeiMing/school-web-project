package com.secondhand.client;

import com.secondhand.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by LeiMing on 2020/2/18 21:28
 */
@FeignClient("user-service")
public interface UserClient  {
    @GetMapping("user/query")
    public Users query(@RequestParam("phone")String phone,@RequestParam("password")String password);
}
