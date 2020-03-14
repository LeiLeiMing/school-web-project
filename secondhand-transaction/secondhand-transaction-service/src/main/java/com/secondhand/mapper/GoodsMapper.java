package com.secondhand.mapper;

import com.secondhand.project.pojo.GoodsPojo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by LeiMing on 2020/3/14 17:39
 */
public interface GoodsMapper extends Mapper<GoodsPojo> {

    @Select("select * from commodity where sellgoodsid = #{goodsid}")
    public GoodsPojo getGoodsByGoodsid(String goodsid);
}
