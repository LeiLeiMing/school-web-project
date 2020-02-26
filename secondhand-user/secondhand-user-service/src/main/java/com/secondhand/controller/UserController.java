package com.secondhand.controller;

import com.secondhand.pojo.Users;
import com.secondhand.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * user controller
 * Created by LeiMing on 2020/2/13 18:55
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    //发送注册验证码
    @PostMapping("sendcode")
    public ResponseEntity<Void> sendSms(@RequestParam("phone")String phone){
        this.userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //注册用户
    @PostMapping("register")
    public ResponseEntity<Void> register(Users user,String code) {
        boolean status = this.userService.register(user, code);
        if (status){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED.BAD_REQUEST).build();
    }

    //登录
    @GetMapping("query")
    public ResponseEntity<Users> query(@RequestParam("phone")String phone,@RequestParam("password")String password){
        Users user = this.userService.queryUser(phone,password);
        if (user == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
