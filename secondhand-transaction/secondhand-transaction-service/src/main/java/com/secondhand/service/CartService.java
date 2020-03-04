package com.secondhand.service;

import com.secondhand.client.CartClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CartClient cartClient;

    public boolean saveCartGoods(String token, List cartlist) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return false;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        //以用户id作为key list作为value 存进redis
        this.redisTemplate.opsForValue().set(KEY_PREFIX+id,cartlist.toString());
        System.out.println("存储成功");
        return true;
    }
}
