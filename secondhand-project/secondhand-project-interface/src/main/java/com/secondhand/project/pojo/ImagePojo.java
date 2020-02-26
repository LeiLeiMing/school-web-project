package com.secondhand.project.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 图片pojo
 * INT类型要用Intergr
 */
@Table(name = "goodsimage")
public class ImagePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //表主键
    private String goodsid;  //商品id
    private String imageaddress;    //图片路径
    private Date updatetime;    //上传时间
    private Integer lunboordateils; //轮播还是详情
    private Integer indexlunbo; //是否是首页轮播图
    private Integer imagestatus;    //图片状态
    private Integer grade;  //优先级，轮播图用

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getImageaddress() {
        return imageaddress;
    }

    public void setImageaddress(String imageaddress) {
        this.imageaddress = imageaddress;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getLunboordateils() {
        return lunboordateils;
    }

    public void setLunboordateils(Integer lunboordateils) {
        this.lunboordateils = lunboordateils;
    }

    public Integer getIndexlunbo() {
        return indexlunbo;
    }

    public void setIndexlunbo(Integer indexlunbo) {
        this.indexlunbo = indexlunbo;
    }

    public Integer getImagestatus() {
        return imagestatus;
    }

    public void setImagestatus(Integer imagestatus) {
        this.imagestatus = imagestatus;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
