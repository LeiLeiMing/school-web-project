package com.secondhand.project.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品Pojo
 * Created by LeiMing on 2020/2/24 21:44
 */
@Table(name = "commodity")
public class GoodsPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键
    private String userid; //用户id
    private String sellgoodsid; //商品id
    private String goodsname; //商品名称
    private BigDecimal goodsprice; //商品价格
    private String goodsaddress; //商品地址
    private Integer goodsmount; //商品数量
    private Integer takesstatus; //上架情况
    private Date selltime;//上架时间
    private String goodstype;//商品类型
    private Integer nogotable;//是否可议价
    private Integer baoyou;//是否包邮
    private String goodsdesc;//商品描述
    private Integer clickmount; //商品点击次数

    private ImagePojo imageaddress;  //用来展示的图片地址，只有一张，goodsid和该商品一致，首页图片状态为1

    private List<ImagePojo> allimageaddress; //该商品下的的全部图片


    public Integer getClickmount() {
        return clickmount;
    }

    public void setClickmount(Integer clickmount) {
        this.clickmount = clickmount;
    }

    public List<ImagePojo> getAllimageaddress() {
        return allimageaddress;
    }

    public void setAllimageaddress(List<ImagePojo> allimageaddress) {
        this.allimageaddress = allimageaddress;
    }

    public ImagePojo getImageaddress() {
        return imageaddress;
    }

    public void setImageaddress(ImagePojo imageaddress) {
        this.imageaddress = imageaddress;
    }

    public String getGoodsdesc() {
        return goodsdesc;
    }

    public void setGoodsdesc(String goodsdesc) {
        this.goodsdesc = goodsdesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSellgoodsid() {
        return sellgoodsid;
    }

    public void setSellgoodsid(String sellgoodsid) {
        this.sellgoodsid = sellgoodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public BigDecimal getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(BigDecimal goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getGoodsaddress() {
        return goodsaddress;
    }

    public void setGoodsaddress(String goodsaddress) {
        this.goodsaddress = goodsaddress;
    }

    public Integer getGoodsmount() {
        return goodsmount;
    }

    public void setGoodsmount(Integer goodsmount) {
        this.goodsmount = goodsmount;
    }

    public Integer getTakesstatus() {
        return takesstatus;
    }

    public void setTakesstatus(Integer takesstatus) {
        this.takesstatus = takesstatus;
    }

    public Date getSelltime() {
        return selltime;
    }

    public void setSelltime(Date selltime) {
        this.selltime = selltime;
    }

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }


    public Integer getNogotable() {
        return nogotable;
    }

    public void setNogotable(Integer nogotable) {
        this.nogotable = nogotable;
    }

    public Integer getBaoyou() {
        return baoyou;
    }

    public void setBaoyou(Integer baoyou) {
        this.baoyou = baoyou;
    }

    @Override
    public String toString() {
        return "GoodsPojo{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", sellgoodsid='" + sellgoodsid + '\'' +
                ", goodsname='" + goodsname + '\'' +
                ", goodsprice=" + goodsprice +
                ", goodsaddress='" + goodsaddress + '\'' +
                ", goodsmount=" + goodsmount +
                ", takesstatus=" + takesstatus +
                ", selltime=" + selltime +
                ", goodstype='" + goodstype + '\'' +
                ", nogotable=" + nogotable +
                ", baoyou=" + baoyou +
                ", goodsdesc='" + goodsdesc + '\'' +
                '}';
    }
}
