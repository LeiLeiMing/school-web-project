package com.secondhand.controller;

import com.alibaba.fastjson.JSONObject;
import com.secondhand.client.CartClient;
import com.secondhand.pojo.MessagePojo;
import com.secondhand.pojo.OrderPojo;
import com.secondhand.service.TransactionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LeiMing on 2020/3/3 20:10
 */
@Controller
@RequestMapping("cart")
public class TransactionController {

    @Autowired
    private CartClient cartClient;

    @Autowired
    private TransactionService transactionService;

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
        this.transactionService.saveCartGoods(token,cartlist);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取redis数据库中的当前用户的购物车数据
     * @param token
     * @return
     */
    @GetMapping("getcart")
    public ResponseEntity<String> getcartgoods(@RequestParam("token")String token){
        String cartGoods = transactionService.getCartGooods(token);
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
        this.transactionService.saveGoodsOrder(token,oreder);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取待发货订单
     * @param token
     * @return
     */
    @GetMapping("getshippedorder")
    public ResponseEntity<List<OrderPojo>> getToBeshippedOrder(@RequestParam("token")String token){
        Map userInfo = null;
        try{
            userInfo = this.cartClient.getUserInfo(token);
            if (userInfo.isEmpty()){
                //用户登录过期
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        List<OrderPojo> toBeshippedOrder = this.transactionService.getToBeshippedOrder(id);
        return ResponseEntity.ok(toBeshippedOrder);
    }

    /**
     * 待支付商品/订单
     * @param token
     * @return
     */
    @GetMapping("gettobepaidorder")
    public ResponseEntity<Map<String,List<OrderPojo>>> getToBePaidOrder(@RequestParam("token")String token){
        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map userInfo = null;
        try{
            userInfo = this.cartClient.getUserInfo(token);
            if (userInfo.isEmpty()){
                //用户登录过期
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        List<OrderPojo> tobepaidorders = this.transactionService.getToBePaidOrder(id);
        //为了实现按订单编号分类，还需要根据订单编号遍历商品
        ArrayList<String> ids = new ArrayList<>(tobepaidorders.size());
        Map<String,List<OrderPojo>> map = new HashMap<>();
        for (OrderPojo order:tobepaidorders){
            List<OrderPojo> toBePaidOrderByOrderid = this.transactionService.getToBePaidOrderByOrderid(order.getOrderid());
            map.put(order.getOrderid(),toBePaidOrderByOrderid);
        }
        //数组查询
        return ResponseEntity.ok(map);
    }

    /**
     * 删除购物车商品
     * @param jsonParam
     * @param token
     * @return
     */
    @PostMapping("deletegoods")
    public ResponseEntity<Void> deletecartgoods(@RequestBody JSONObject[] jsonParam,@RequestParam("token")String token){
        List cartlist = new ArrayList<String>();
        for (int i =0;i<jsonParam.length;i++){
            cartlist.add(jsonParam[i].toJSONString());
        }
        this.transactionService.deleteCartGoods(token,cartlist);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取消息
     * @param token
     * @return
     */
    @GetMapping("getmessage")
    public ResponseEntity<List<MessagePojo>> getUserMessage(@RequestParam("token")String token){
        List<MessagePojo> userMessage = this.transactionService.getUserMessage(token);
        return ResponseEntity.ok(userMessage);
    }

    /**
     * 获取未读消息数量
     * @param token
     * @return
     */
    @GetMapping("getmessagemount")
    public ResponseEntity<Integer> getMessageMount(@RequestParam("token")String token){
        Integer mount = this.transactionService.getMessageMount(token);
        return ResponseEntity.ok(mount);
    }

    /**
     * 修改消息已读状态
     * @param messageid
     * @return
     */
    @GetMapping("changemessagestatus")
    public ResponseEntity<Void> changemessagestatus(@RequestParam("messageid")String messageid){
        this.transactionService.changrMessageStatus(messageid);
        return ResponseEntity.ok(null);
    }


    /**
     * 接收支付结果
     * @param request
     * @return
     */
    @GetMapping("/getalipay")
    public String getalipay(HttpServletRequest request) throws ParseException {
        Map<String,String[]> map = request.getParameterMap();
        String[] orderinfo = map.get("out_trade_no");
        String orderid = orderinfo[0];
        //将该订单的状态改为已支付状态
        this.transactionService.changeOrderStatu(orderid);
        //获取该用户id
        List<OrderPojo> order = this.transactionService.getUserIdByOrder(orderid);
        String id = order.get(0).getBuyerid();
        //获取商品编号 并根据商品编号修改商品状态
        for (int i = 0;i<order.size();i++){
            String goodsid = order.get(i).getGoodsid();
            this.transactionService.changeGoodsStatus(2,goodsid);
        }
        //清空该用户的购物车
        this.transactionService.clearCartGoods(id);
        //获取该订单的出售者id
        List<String> sellerids = this.transactionService.getSellerByOrderid(orderid);
        //将订单消息存入数据库通知卖家
        for (int i = 0;i<sellerids.size();i++){
            this.transactionService.addMessage(sellerids.get(i),orderid);
        }
        return  "redirect:http://localhost:3000/#/paysuccess";
    }

}
