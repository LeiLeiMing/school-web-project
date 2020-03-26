package com.secondhand.mapper;

import com.secondhand.project.pojo.GoodsPojo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.http.ResponseEntity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 商品Mapper
 * Created by LeiMing on 2020/2/24 21:44
 */
public interface GoodsMapper extends Mapper<GoodsPojo> {

    //查询该用户下上架的商品
    @Select("select * from commodity where userid = #{id}")
    @Results(id = "goodsforuser", value = {
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
            @Result(property = "imageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findGoodsImageById",fetchType = FetchType.DEFAULT)),
    })
    public List<GoodsPojo> findGroundingGoods(String id);


    //查询单个商品的全部信息
    @Select("select * from commodity where sellgoodsid = #{id}")
    @Results(id = "goodsdetails", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    GoodsPojo findGoodsById(String id);


    //查询新出售的商品
    @Select("select * from  commodity order by selltime desc limit 0,10")
    @Results(id = "newsellgoods", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> findNewSellGoods();


    @Insert("update  commodity set clickmount = clickmount+1 where sellgoodsid = #{sellgoodsid}")
    void savegoodsclick(String sellgoodsid);

    @Select("select * from  commodity order by clickmount desc limit 0,10")
    @Results(id = "hotsellgoods", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> findHotSellGoods();


    @Select("select * from commodity limit #{startpage},#{endpage}")
    @Results(id = "getgoodslimit", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> getGoodsLimit(Integer startpage, Integer endpage);

    @Select("select count(*) from commodity")
    Integer getgoodsmount();

    @Update("update commodity set takesstatus = #{status} where sellgoodsid = #{goodsid}")
    void changeGoodsStatus(Integer status,String goodsid);

    @Select("select * from commodity where goodstype = #{type}")
    @Results(id = "studygoods", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> getStudyGoods(String type);


    @Select("select * from commodity where 1 order by rand() limit 10")
    @Results(id = "randgoods", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> getRandGoods();

    @Select("select * from commodity where 1 order by rand() limit 3")
    @Results(id = "indexlunbo", value = {
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
            @Result(property = "allimageaddress",column = "sellgoodsid",
                    one = @One(select = "com.secondhand.mapper.ImageMapper.findAllGoodsImage",fetchType = FetchType.DEFAULT)),
    })
    List<GoodsPojo> getIndexLunBo();
}
