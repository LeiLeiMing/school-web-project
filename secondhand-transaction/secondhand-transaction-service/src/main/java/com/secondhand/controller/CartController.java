package com.secondhand.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secondhand.pojo.OrderPojo;
import com.secondhand.service.CartService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LeiMing on 2020/3/3 20:10
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 将当前用户下的购物车数据存进redis
     * @param jsonParam
     * @param token
     * @return
     */
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

    /**
     * 获取redis数据库中的当前用户的购物车数据
     * @param token
     * @return
     */
    @GetMapping("getcart")
    public ResponseEntity<String> getcartgoods(@RequestParam("token")String token){
        String cartGoods = cartService.getCartGooods(token);
        if (StringUtils.isBlank(cartGoods)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(cartGoods);
    }

    /**
     * 保存订单
     * @param token
     * @param oreder
     * @return
     */
    @PostMapping("generateorder")
    public ResponseEntity<Void> generateorder(@RequestParam("token")String token, OrderPojo oreder){
        //将订单循环插入数据库
        this.cartService.saveGoodsOrder(token,oreder);
        return ResponseEntity.ok(null);
    }

    /**
     * 删除购物车商品
     * @param jsonParam
     * @param token
     * @return
     */
    @PostMapping("deletegoods")
    public ResponseEntity<Void> deletecartgoods(@RequestBody JSONObject[] jsonParam,@RequestParam("token")String token){
        //get redis data /
        //String cartGoods = cartService.getCartGooods(token);
        List cartlist = new ArrayList<String>();
        for (int i =0;i<jsonParam.length;i++){
            cartlist.add(jsonParam[i].toJSONString());
        }
        this.cartService.deleteCartGoods(token,cartlist);
        return ResponseEntity.ok(null);
    }

    /**
     * 接收支付结果
     * @param request
     * @return
     */
    @GetMapping("/getalipay")
    public String getalipay(HttpServletRequest request){
        Map<String,String[]> map = request.getParameterMap();
        String[] orderinfo = map.get("out_trade_no");
        String orderid = orderinfo[0];
        //将该订单的状态改为已支付状态
        this.cartService.changeOrderStatu(orderid);
        //清空购物车
        //获取订单对应下的用户
        List<OrderPojo> order = this.cartService.getUserIdByOrder(orderid);
        String id = order.get(0).getBuyerid();
        //清空该用户的购物车
        this.cartService.clearCartGoods(id);
        return  "redirect:http://localhost:3000/#/paysuccess";
    }

}
