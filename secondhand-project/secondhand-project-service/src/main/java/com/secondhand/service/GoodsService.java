package com.secondhand.service;

import com.secondhand.client.AuthClient;
import com.secondhand.common.CodeUtils;
import com.secondhand.mapper.GoodsMapper;
import com.secondhand.pojo.Users;
import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.utils.UploadToOssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Date;
import java.util.Map;

/**
 * Created by LeiMing on 2020/2/24 21:45
 */
@Service
@Transactional
public class GoodsService {
    
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private AuthClient authClient;

    //保存图片到OSS服务器，并返回url地址
    public URL uploadFile(byte[] bytes, String filename) {
        return UploadToOssUtils.uploadFile(bytes,filename);
    }

    //将商品信息存进database
    public boolean saveSellGoods(GoodsPojo goods,String token){
        if (goods==null){
            return false;
        }
        //根据token获取当前用户id
        Map userinfomap = this.authClient.getUserInfo(token);
        Map userinfo = (Map) userinfomap.get("userinfo");
        if(userinfomap == null){
            return false;
        }
        //设置用户id
        goods.setUserid(userinfo.get("id").toString());
        //设置商品id
        goods.setSellgoodsid(CodeUtils.getRandomCode());
        //设置出售状态
        goods.setTakesstatus(1);
        //设置出售时间
        goods.setSelltime(new Date());
        int i = goodsMapper.insertSelective(goods);
        if (i == 0){
            return false;
        }
        return true;
    }

}
