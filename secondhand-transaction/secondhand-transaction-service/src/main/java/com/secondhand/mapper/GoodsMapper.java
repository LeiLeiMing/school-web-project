package com.secondhand.mapper;

import com.secondhand.project.pojo.GoodsPojo;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by LeiMing on 2020/3/14 17:39
 */
public interface GoodsMapper extends Mapper<GoodsPojo> {

    @Select("select * from commodity where sellgoodsid = #{goodsid}")
    @Results(id = "goodsvalue", value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "userid",column = "userid"),
            @Result(property = "sellgoodsid",column = "sellgoodsid"),
            @Result(property = "goodsname",column = "goodsname"),
            @Result(property = "goodsprice",column = "goodsprice"),
            @Result(property = "goodsaddress",column = "goodsaddress"),
            @Result(property = "goodsmount",column = "goodsmount"),
            @Result(property = "takesstatus",column = "takesstatus"),
            @Result(property = "selltime",column = "selltime"),
            @Result(property = "goodstype",column = "goodstype"),
            @Result(property = "nogotable",column = "nogotable"),
            @Result(property = "baoyou",column = "baoyou"),
            @Result(property = "goodsdesc",column = "goodsdesc"),
            @Result(property = "clickmount",column = "clickmount"),
            @Result(property = "fresh",column = "fresh"),
            @Result(property = "imageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findDisplayGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    public GoodsPojo getGoodsByGoodsid(String goodsid);
}
