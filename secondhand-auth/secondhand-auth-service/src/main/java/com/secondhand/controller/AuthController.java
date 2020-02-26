package com.secondhand.controller;

import com.secondhand.pojo.UserInfo;
import com.secondhand.service.AuthService;
import com.secondhand.utils.CookieUtils;
import com.secondhand.utils.KeyUrl;
import com.secondhand.utils.RsaUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授权中心
 * Created by LeiMing on 2020/2/18 20:33
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends KeyUrl {
    @Autowired
    private AuthService authService;

    /**
     * 授权用户token，核对登录信息无误后授权
     * @param phone
     * @param password
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping("check")
    public ResponseEntity<List> userauth(
            @RequestParam("phone")String phone,
            @RequestParam("password")String password,
            HttpServletRequest request,
            HttpServletResponse response
            ) throws Exception {
        //将公私密钥存进去 secret为密钥加密盐
        RsaUtils.generateKey(PUBKRY_PATH, PRIKRY_PATH, "asd_334467");
        //核对用户账号密码，核对无误获取token
        String token = this.authService.userauth(phone,password);
        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //返回token给前端，前端完成cookie录入
        List list = new ArrayList<>();
        list.add(token);
        return ResponseEntity.ok(list);
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     * @throws Exception
     */
    @GetMapping("userinfo")
    public ResponseEntity<Map> getUserInfo(@RequestParam("token")String token) throws Exception {
        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //通过token获取用户信息
        UserInfo userInfo = authService.getUserInfo(token);
        if (userInfo == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //更新Cookie中的Token
        String newToken = this.authService.newToken(userInfo);
        Map map = new HashMap<String,Object>();
        map.put("userinfo",userInfo);
        map.put("newtoken",newToken);
        return ResponseEntity.ok(map);
    }
}
