package com.secondhand.mapper;


import com.secondhand.project.pojo.ImagePojo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by LeiMing on 2020/3/19 17:58
 */
public interface ImageMapper extends Mapper<ImagePojo> {

    /**
     * 获取商品展示图片一张
     * @param id
     * @return
     */
    @Select("select * from goodsimage where goodsid = #{sellgoodsid} limit 0,1")
    public ImagePojo findDisplayGoodsImage(String id);
}
