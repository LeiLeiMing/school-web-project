package com.secondhand.service;

import com.secondhand.client.CartClient;
import com.secondhand.client.GoodsClient;
import com.secondhand.mapper.CartMapper;
import com.secondhand.mapper.MessageMapper;
import com.secondhand.pojo.MessagePojo;
import com.secondhand.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LeiMing on 2020/3/3 21:37
 */
@Service
@Transactional
public class TransactionService {
    //存进redis的数据前缀
    private static final String KEY_PREFIX = "user:cart:";

    //redis操作模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartClient cartClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private MessageMapper messageMapper;

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
        order.setOrderaddress(order.getOrderaddress());
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

    public List<OrderPojo> getorderbyid(String orderid) {
        Example example = new Example(OrderPojo.class);
        example.createCriteria()
                .andEqualTo("orderid",orderid);
        List<OrderPojo> orders = this.cartMapper.selectByExample(example);
        return orders;
    }

    public void changeOrderStatu(String orderid) {
        this.cartMapper.changeOrderStatus(orderid);
    }

    public List<OrderPojo> getUserIdByOrder(String orderid) {
        return this.cartMapper.getUserByOrder(orderid);
    }

    public void clearCartGoods(String id) {
        this.redisTemplate.delete(KEY_PREFIX+id);
    }

    public List<String> getSellerByOrderid(String orderid) {
        return this.cartMapper.getSelleByOrderid(orderid);
    }

    public void addMessage(String sellerid,String orderid) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = format1.format(new Date());
        MessagePojo message = new MessagePojo();
        message.setUserid(sellerid);
        message.setMessage("你收到新的订单，点击查看");
        message.setTime(date);
        message.setStatus(0);
        message.setType(0);
        message.setOrderid(orderid);
        this.messageMapper.insert(message);
    }

    public List<MessagePojo> getUserMessage(String token) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return null;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        //根据id获取
        Example example = new Example(MessagePojo.class);
        example.createCriteria()
                .andEqualTo("userid",id);
        List<MessagePojo> message = this.messageMapper.selectByExample(example);
        return message;
    }

    public void changrMessageStatus(String messageid) {
        this.messageMapper.changeMessageStatus(messageid);
    }

    public Integer getMessageMount(String token) {
        Map userInfo = this.cartClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return null;
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        Integer mount = this.messageMapper.getMessageMount(id);
        return mount;
    }

    public void changeGoodsStatus(Integer status,String goodsid) {
        this.goodsClient.changeGoodsStatus(status,goodsid);
    }

    public List<OrderPojo> getToBeshippedOrder(String id) {
        //查询
        List<OrderPojo> orders = this.cartMapper.getToBeshippedOrder(id);
        return orders;
    }

    public List<OrderPojo> getToBePaidOrder(String id) {
        return this.cartMapper.getToBePaidOrders(id);
    }

    public List<OrderPojo> getToBePaidOrderByOrderid(String orderid) {
        return this.cartMapper.getToBePaidOrderByOrderid(orderid);
    }
}
