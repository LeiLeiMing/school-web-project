package com.secondhand.controller;

import com.secondhand.Users;
import com.secondhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * user controller
 * Created by LeiMing on 2020/2/13 18:55
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("sendcode")
    public ResponseEntity<Void> sendSms(@RequestParam("phone")String phone){
        this.userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Void> register(Users user,String code) {
        boolean status = this.userService.register(user, code);
        if (status){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<Users> query(@RequestParam("phone")String phone,@RequestParam("password")String password){
        Users user = this.userService.queryUser(phone,password);
        if (user == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("findbyid")
    public ResponseEntity<Users> findUserById(@RequestParam("id")String id){
        List<Users> users = this.userService.findUsrById(id);
        Users user = users.get(0);
        return ResponseEntity.ok(user);
    }
}
