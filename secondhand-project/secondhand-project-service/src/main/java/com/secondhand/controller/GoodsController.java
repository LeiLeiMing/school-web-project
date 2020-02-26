package com.secondhand.controller;

import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.pojo.ImagePojo;
import com.secondhand.service.GoodsService;
import com.secondhand.service.ImagePojoService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * Created by LeiMing on 2020/2/24 21:45
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private ImagePojoService imagePojoService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 首页轮播图
     * ResponseEntutity需要一个集合来装ImagePojo
     * @return
     */
    @GetMapping("indexlunbo")
    public ResponseEntity<List<ImagePojo>> getIndexLunBo(){
        List<ImagePojo> indexLunboImage = this.imagePojoService.getIndexLunboImage();
        return ResponseEntity.ok(indexLunboImage);
    }

    /**
     * 出售闲置物品的基本信息
     * @return
     */
    @PostMapping("sell")
    public ResponseEntity<Void> publicGoods(GoodsPojo goods,@RequestParam("authtoken")String authtoken){
        System.out.println(authtoken);
        if (StringUtils.isBlank(authtoken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        //把相关商品信息存进数据库
        if(!goodsService.saveSellGoods(goods,authtoken)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        };
        return ResponseEntity.ok(null);
    }

    /**
     * 商品详情图片，注意，数组文件用files单个文件用file，否则为空
     * @param fileList
     * @return
     */
    @PostMapping("filelist")
    public ResponseEntity<Void> filelist(@RequestParam("files") MultipartFile[] fileList){

        for (int i = 0;i < fileList.length;i++){
            try {
                byte[] bytes = fileList[i].getBytes();
                String filename = fileList[i].getOriginalFilename();
                URL url = goodsService.uploadFile(bytes, filename);
                System.out.println("详情保存成功地址为"+url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 商品首页图片
     * @param fileList
     * @return
     */
    @PostMapping("indexfilelist")
    public ResponseEntity<Void> indexfilelist(@RequestParam("files") MultipartFile[] fileList){
        for (int i = 0;i < fileList.length;i++){
            //分别存进OSS中
            try {
                byte[] bytes = fileList[i].getBytes();
                String filename = fileList[i].getOriginalFilename();
                URL url = goodsService.uploadFile(bytes, filename);
                System.out.println("首页保存成功，地址为"+url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(null);
    }
}
