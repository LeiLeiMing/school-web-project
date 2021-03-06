package com.secondhand.pojo;

import com.secondhand.Users;
import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.pojo.UserAddressPojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单pojo
 * Created by LeiMing on 2020/3/5 11:20
 */
@Table(name = "goodsorder")
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderid;

    private String buyerid;

    private String sellerid;

    private String goodsid;

    private Integer goodsmount;

    private BigDecimal allprice;

    private Integer orderstatus;

    private Date ordertime;

    private String orderleavemessage;

    private Date orderendtime;

    private String orderaddress;

    private Integer addressid;

    private GoodsPojo goodsPojo;

    private Users user;

    private UserAddressPojo userAddressPojo;

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public UserAddressPojo getUserAddressPojo() {
        return userAddressPojo;
    }

    public void setUserAddressPojo(UserAddressPojo userAddressPojo) {
        this.userAddressPojo = userAddressPojo;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public GoodsPojo getGoodsPojo() {
        return goodsPojo;
    }

    public void setGoodsPojo(GoodsPojo goodsPojo) {
        this.goodsPojo = goodsPojo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public Integer getGoodsmount() {
        return goodsmount;
    }

    public void setGoodsmount(Integer goodsmount) {
        this.goodsmount = goodsmount;
    }

    public BigDecimal getAllprice() {
        return allprice;
    }

    public void setAllprice(BigDecimal allprice) {
        this.allprice = allprice;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrderleavemessage() {
        return orderleavemessage;
    }

    public void setOrderleavemessage(String orderleavemessage) {
        this.orderleavemessage = orderleavemessage;
    }

    public Date getOrderendtime() {
        return orderendtime;
    }

    public void setOrderendtime(Date orderendtime) {
        this.orderendtime = orderendtime;
    }

    public String getOrderaddress() {
        return orderaddress;
    }

    public void setOrderaddress(String orderaddress) {
        this.orderaddress = orderaddress;
    }
}
