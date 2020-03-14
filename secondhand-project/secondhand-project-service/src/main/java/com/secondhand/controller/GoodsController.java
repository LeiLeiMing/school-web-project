package com.secondhand.controller;

import com.secondhand.client.AuthClient;
import com.secondhand.common.CodeUtils;
import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.pojo.ImagePojo;
import com.secondhand.project.pojo.UserAddressPojo;
import com.secondhand.service.GoodsClickService;
import com.secondhand.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by LeiMing on 2020/2/24 21:45
 */
@Controller
@RequestMapping("goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsClickService goodsClickService;

    @Autowired
    private AuthClient authClient;

    /**
     * 首页轮播图
     * ResponseEntutity需要一个集合来装ImagePojo
     * @return
     */
    @GetMapping("indexlunbo")
    public ResponseEntity<List<ImagePojo>> getIndexLunBo(){
        List<ImagePojo> indexLunboImage = this.goodsService.getIndexLunboImage();
        return ResponseEntity.ok(indexLunboImage);
    }
    /**
     * 出售闲置物品的基本信息
     * @return
     */
    @PostMapping("sell")
    public ResponseEntity<Void> publicGoods(GoodsPojo goods,@RequestParam("authtoken")String authtoken,@RequestParam("files") MultipartFile[] fileList,@RequestParam("files") MultipartFile[] indexfileList) throws IOException {
        if (StringUtils.isBlank(authtoken)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //设置商品id
        goods.setSellgoodsid(CodeUtils.getRandomCode());
        //把相关商品信息存进数据库
        goodsService.saveSellGoods(goods,authtoken,fileList,indexfileList);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取当前用户出售的全部商品
     * @param authtoken
     * @return
     */
    @GetMapping("findsellgoods")
    public ResponseEntity<List<GoodsPojo>> findGroundingGoods(@RequestParam("authtoken")String authtoken){
        //获取当前用户信息
        Map userInfoMap = goodsService.getUserInfo(authtoken);
        if (userInfoMap.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map userinfo = (Map) userInfoMap.get("userinfo");
        String id = userinfo.get("id").toString();
        //查询该id的用户下的商品
        List<GoodsPojo> GroundingGoods = this.goodsService.findGoodsByUserId(id);
        return ResponseEntity.ok(GroundingGoods);
    }

    /**
     * 单个商品的详情
     * @param sellgoodsid
     * @return
     */
    @GetMapping("goodsdetails")
    public ResponseEntity<GoodsPojo> findGoodsById(@RequestParam("sellgoodsid")String sellgoodsid){
        //查询商品详情
        GoodsPojo goodsinfo = this.goodsService.findOneGoodsById(sellgoodsid);
        return ResponseEntity.ok(goodsinfo);
    }

    /**
     * 新出售的商品
     * @return
     */
    @GetMapping("newsellgoods")
    public ResponseEntity<List<GoodsPojo>> findNewSellGoods(){
        return ResponseEntity.ok(this.goodsService.findNewSellGoods());
    }

    /**
     * 更新商品浏览量
     * @param sellgoodsid
     * @return
     */
    @PostMapping("goodsclick")
    public ResponseEntity<Void> goodsclick(@RequestParam("sellgoodsid")String sellgoodsid){
        return goodsClickService.goodsclickmount(sellgoodsid);
    }

    /**
     * 热门的商品
     * @return
     */
    @GetMapping("hotsellgoods")
    public ResponseEntity<List<GoodsPojo>> findHotGoods(){
        List<GoodsPojo> hotSellGoods = this.goodsService.findHotSellGoods();
        return ResponseEntity.ok(hotSellGoods);
    }

    /**
     * 根据序列获取商品列商品
     * @return
     */
    @GetMapping("getgoodslimit")
    public ResponseEntity<List<GoodsPojo>> getgoodslimit(@RequestParam("startpage")Integer startpage,@RequestParam("endpage")Integer endpage){
        //1-1*5 1*5-5*2
        List<GoodsPojo> goods = goodsService.getgoodsLimit(startpage, endpage);
        return ResponseEntity.ok(goods);
    }

    /**
     * 获取全部商品总数
     * @return
     */
    @GetMapping("getgoodsmount")
    public ResponseEntity<Integer> getgoodsmount(){
        return ResponseEntity.ok(this.goodsService.getgoodsmount());
    }

    /**
     * 保存收货地址
     * @param token
     * @param selectid
     * @param username
     * @param usertel
     * @param useraddress
     * @param userdateliaddress
     * @return
     */
    @PostMapping("saveaddress")
    public ResponseEntity<Void> saveaddress(
            @RequestParam("token")String token,
            @RequestParam("selectid")String selectid,
            @RequestParam("username")String username,
            @RequestParam("usertel")String usertel,
            @RequestParam("useraddress")String useraddress,
            @RequestParam("userdateliaddress")String userdateliaddress){
        UserAddressPojo useradddress = new UserAddressPojo();
        useradddress.setSelectid(selectid);
        useradddress.setUsername(username);
        useradddress.setUsertel(usertel);
        useradddress.setUseraddress(useraddress);
        useradddress.setUserdateliaddress(userdateliaddress);
        if(!this.goodsService.saveAddress(useradddress, token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 获取收货地址
     * @param token
     * @return
     */
    @GetMapping("getaddress")
    public ResponseEntity<List<UserAddressPojo>> getaddress( @RequestParam("token")String token){
        Map userInfo = this.authClient.getUserInfo(token);
        if (userInfo.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Map userinfo = (Map) userInfo.get("userinfo");
        String id = userinfo.get("id").toString();
        List<UserAddressPojo> address = this.goodsService.getaddressbyid(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("changegoodsstatus")
    public ResponseEntity<Void> changeGoodsStatus(@RequestParam("status")Integer status, @RequestParam("goodsid")String goodsid){
        this.goodsService.changeGoodsStatus(status,goodsid);
        return ResponseEntity.ok(null);
    }
}
