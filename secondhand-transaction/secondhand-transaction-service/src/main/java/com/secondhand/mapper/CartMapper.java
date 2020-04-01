package com.secondhand.mapper;

import com.secondhand.pojo.OrderPojo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单Mapper
 * Created by LeiMing on 2020/3/5 11:20
 */
public interface CartMapper extends Mapper<OrderPojo> {
    @Insert("insert into goodsorder (orderid,buyerid,sellerid,goodsid," +
            "goodsmount,allprice,orderstatus,ordertime,orderleavemessage,orderaddress)" +
            "values(#{order.orderid},#{id},#{order.sellerid},#{order.goodsid},#{order.goodsmount}," +
            "#{order.allprice},#{order.orderstatus},#{order.ordertime},#{order.orderleavemessage},#{order.orderaddress})")
    void saveGoodsOrder(String id, OrderPojo order);

    @Update("update goodsorder set orderstatus = 1 where orderid = #{orderid}")
    void changeOrderStatus(String orderid);

    @Select("select * from goodSorder where orderid = #{orderid} ")
    List<OrderPojo> getUserByOrder(String orderid);

    @Select("select DISTINCT sellerid from goodsorder where orderid = #{orderid}")
    List<String> getSelleByOrderid(String orderid);

    @Select("select * from goodsorder where orderstatus =1 and sellerid = #{id}")
    @Results(id = "tobeshippedOrder", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderid",column = "orderid"),
            @Result(property = "buyerid",column = "buyerid"),
            @Result(property = "sellerid",column = "sellerid"),
            @Result(property = "goodsid",column = "goodsid"),
            @Result(property = "goodsmount",column = "goodsmount"),
            @Result(property = "allprice",column = "allprice"),
            @Result(property = "orderstatus",column = "orderstatus"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "orderleavemessage",column = "orderleavemessage"),
            @Result(property = "orderendtime",column = "orderendtime"),
            @Result(property = "orderaddress",column = "orderaddress"),
            @Result(property = "goodsPojo",column = "goodsid",
                    one = @One(select = "com.secondhand.mapper.GoodsMapper.getGoodsByGoodsid",fetchType = FetchType.DEFAULT)),
    })
    List<OrderPojo> getToBeshippedOrder(String id);

    @Select("select * from goodsorder where orderstatus = 0 and buyerid = #{id}")
    @Results(id = "tobepaiddOrders", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderid",column = "orderid"),
            @Result(property = "buyerid",column = "buyerid"),
            @Result(property = "sellerid",column = "sellerid"),
            @Result(property = "goodsid",column = "goodsid"),
            @Result(property = "goodsmount",column = "goodsmount"),
            @Result(property = "allprice",column = "allprice"),
            @Result(property = "orderstatus",column = "orderstatus"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "orderleavemessage",column = "orderleavemessage"),
            @Result(property = "orderendtime",column = "orderendtime"),
            @Result(property = "orderaddress",column = "orderaddress"),
    })
    List<OrderPojo> getToBePaidOrders(String id);

    @Select("select * from goodsorder where orderid = #{orderid}")
    @Results(id = "TobepaidOrdersByOrderid", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderid",column = "orderid"),
            @Result(property = "buyerid",column = "buyerid"),
            @Result(property = "sellerid",column = "sellerid"),
            @Result(property = "goodsid",column = "goodsid"),
            @Result(property = "goodsmount",column = "goodsmount"),
            @Result(property = "allprice",column = "allprice"),
            @Result(property = "orderstatus",column = "orderstatus"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "orderleavemessage",column = "orderleavemessage"),
            @Result(property = "orderendtime",column = "orderendtime"),
            @Result(property = "orderaddress",column = "orderaddress"),
            @Result(property = "goodsPojo",column = "goodsid",
                    one = @One(select = "com.secondhand.mapper.GoodsMapper.getGoodsByGoodsid",fetchType = FetchType.DEFAULT)),
    })
    List<OrderPojo> getToBePaidOrderByOrderid(String orderid);

    @Delete("delete from goodsorder where orderstatus = 0 and buyerid = #{id} and orderid = #{orderid}")
    void deltobepaidOrder(String id, String orderid);


    @Select("select * from goodsorder where orderstatus != 0 and buyerid = #{id}")
    @Results(id = "getMyOrder", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderid",column = "orderid"),
            @Result(property = "buyerid",column = "buyerid"),
            @Result(property = "sellerid",column = "sellerid"),
            @Result(property = "goodsid",column = "goodsid"),
            @Result(property = "goodsmount",column = "goodsmount"),
            @Result(property = "allprice",column = "allprice"),
            @Result(property = "orderstatus",column = "orderstatus"),
            @Result(property = "ordertime",column = "ordertime"),
            @Result(property = "orderleavemessage",column = "orderleavemessage"),
            @Result(property = "orderendtime",column = "orderendtime"),
            @Result(property = "orderaddress",column = "orderaddress"),
    })
    List<OrderPojo> getMyOrder(String id);
}
