package com.secondhand.service;

import com.secondhand.client.CartClient;
import com.secondhand.mapper.CartMapper;
import com.secondhand.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LeiMing on 2020/3/3 21:37
 */
@Service
@Transactional
public class CartService {
    //存进redis的数据前缀
    private static final String KEY_PREFIX = "user:cart:";

    //redis操作模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartClient cartClient;

    /**
     * 保存购物车至redis
     * @param token
     * @param cartlist
     * @return
     */
    public boolean saveCartGoods(String token, List cartlist) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return false;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        //save redis
        this.redisTemplate.opsForValue().set(KEY_PREFIX+id,cartlist.toString());
        return true;
    }

    /**
     * 从redis获取购物车数据
     * @param token
     * @return
     */
    public String getCartGooods(String token) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return null;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        return this.redisTemplate.opsForValue().get(KEY_PREFIX + id);
    }

    /**
     * 保存当前用户购物车的订单
     * @param token
     * @param order
     * @return
     */
    public boolean saveGoodsOrder(String token, OrderPojo order) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return false;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        order.setBuyerid(id);
        order.setOrdertime(new Date());
        //设置订单状态 0:待支付 1：已支付 2：未发货 3：已发货
        order.setOrderstatus(0);
        //设置订单收货地址,将根据用户id从数据库获取详细地址 的 ID 并存进数据库
        order.setOrderaddress("广西");
        this.cartMapper.saveGoodsOrder(id,order);
        return false;
    }

    public boolean deleteCartGoods(String token, List cartlist) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return false;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        //删除该key下的全部redis
        Long mount = this.redisTemplate.opsForValue().decrement(id);
        if (mount==0){
            return false;
        }
        //save redis
        this.redisTemplate.opsForValue().set(KEY_PREFIX+id,cartlist.toString());
        return true;
    }
}
