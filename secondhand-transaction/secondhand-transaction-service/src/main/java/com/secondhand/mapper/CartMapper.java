package com.secondhand.mapper;

import com.secondhand.pojo.OrderPojo;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

/**
 * 订单Mapper
 * Created by LeiMing on 2020/3/5 11:20
 */
public interface CartMapper extends Mapper<OrderPojo> {
    @Insert("insert into goodsorder (orderid,buyerid,sellerid,goodsid," +
            "goodsmount,allprice,orderstatus,ordertime,orderleavemessage,address)" +
            "values(#{order.orderid},#{id},#{order.sellerid},#{order.goodsid},#{order.goodsmount}," +
            "#{order.allprice},#{order.orderstatus},#{order.ordertime},#{order.orderleavemessage},#{order.orderaddress})")
    void saveGoodsOrder(String id, OrderPojo order);
}
