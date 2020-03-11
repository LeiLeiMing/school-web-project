package com.secondhand.mapper;

import com.secondhand.pojo.OrderPojo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

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
}
