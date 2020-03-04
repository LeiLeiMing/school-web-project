package com.secondhand.controller;

import com.alibaba.fastjson.JSONObject;
import com.secondhand.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LeiMing on 2020/3/3 20:10
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("savelocal")
    public ResponseEntity<Void> saveLocalCartData(@RequestBody JSONObject[] jsonParam, @RequestParam("token")String token){
        List cartlist = new ArrayList<String>();
        for (int i =0;i<jsonParam.length;i++){
            cartlist.add(jsonParam[i].toJSONString());
        }
        //将数据存进redis token将用于获取用户id作为redis的key
        this.cartService.saveCartGoods(token,cartlist);
        return ResponseEntity.ok(null);
    }
}
