package com.secondhand.mapper;

import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.pojo.ImagePojo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 首页轮播图Mapper接口
 * Created by LeiMing on 2020/2/6 10:23
 */
public interface ImageMapper extends Mapper<ImagePojo> {

    //根据商品id查询商品图片
    @Select("select * from goodsimage where goodsid = #{sellgoodsid} and lunboordateils = 1 limit 0,1")
    public ImagePojo findGoodsImageById(String sellgoodsid);

    //根据商品id查询该商品的全部图片
    @Select("select * from goodsimage where goodsid = #{sellgoodsid}")
    public List<ImagePojo> findAllGoodsImage(String sellgoodsid);
}
