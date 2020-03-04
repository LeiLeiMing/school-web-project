package com.secondhand.service;

import com.secondhand.client.AuthClient;
import com.secondhand.mapper.GoodsMapper;
import com.secondhand.mapper.ImageMapper;
import com.secondhand.project.pojo.GoodsPojo;
import com.secondhand.project.pojo.ImagePojo;
import com.secondhand.project.utils.UploadToOssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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

    @Autowired
    private ImageMapper imageMapper;



    /**
     * 发布商品
     * @param goods
     * @param token
     * @param fileList
     * @param indexfileList
     * @return
     * @throws IOException
     */
    public ResponseEntity<Void> saveSellGoods(GoodsPojo goods, String token, MultipartFile[] fileList, MultipartFile[] indexfileList) throws IOException {
        //商品信息
        if (goods==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        int indeximagestatus = 0;
        int imagestatus = 0;
        //根据token获取当前用户id
        Map userinfomap =  this.authClient.getUserInfo(token);
        Map userinfo  = (Map) userinfomap.get("userinfo");
        if(userinfomap == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //设置商品的卖还是买标识 1是卖0是买
        goods.setTakesstatus(1);
        //设置用户id
        goods.setUserid(userinfo.get("id").toString());
        //设置出售状态
        goods.setTakesstatus(1);
        //设置出售时间
        goods.setSelltime(new Date());
        goods.setClickmount(0);
        //保存商品信息
        int status = goodsMapper.insertSelective(goods);
        //插入商品详情图片
        for(int i = 0 ;i<fileList.length;i++){
            byte[] bytes = fileList[i].getBytes();
            String filename = fileList[i].getOriginalFilename();
            ImagePojo image = new ImagePojo();
            image.setGoodsid(userinfo.get("id").toString());
            //属于详情图片
            image.setLunboordateils(0);
            image.setUpdatetime(new Date());
            //插入商品详情图片到OSS
            URL url = UploadToOssUtils.uploadFile(bytes,filename);
            System.out.println("商品详情图片保存成功，地址是:"+url);
            image.setImageaddress(url.toString());
            image.setGoodsid(goods.getSellgoodsid());
            imagestatus = imageMapper.insertSelective(image);
        }
        //插入首页图片
        for(int i = 0 ;i<indexfileList.length;i++){
            byte[] bytes = indexfileList[i].getBytes();
            String filename = indexfileList[i].getOriginalFilename();
            ImagePojo image = new ImagePojo();
            image.setGoodsid(userinfo.get("id").toString());
            //属于详情图片
            image.setLunboordateils(1);
            image.setUpdatetime(new Date());
            //插入商品详情图片到OSS
            URL url = UploadToOssUtils.uploadFile(bytes,filename);
            System.out.println("首页图片保存成功，地址是:"+url);
            image.setImageaddress(url.toString());
            image.setGoodsid(goods.getSellgoodsid());
            indeximagestatus = imageMapper.insertSelective(image);
        }
        if (status==0||imagestatus==0||indeximagestatus==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 获取首页的轮播图
     * @return
     */
    public List<ImagePojo> getIndexLunboImage() {
        Example example = new Example(ImagePojo.class);
        //条件查询:indexlunbo为1的数据
        example.createCriteria()
                .andEqualTo("indexlunbo",1);
        return imageMapper.selectByExample(example);
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public Map getUserInfo(String token){
        Map userinfomap =  this.authClient.getUserInfo(token);
        return userinfomap;
    }


    /**
     * 根据用户id查询所发布的商品
     * @param id
     * @return
     */
    public List<GoodsPojo> findGoodsByUserId(String id){
        //自定义多表联查
        List<GoodsPojo> goods = this.goodsMapper.findGroundingGoods(id);
        return goods;
    }

    /**
     * 根据商品id获取商品
     * @param sellgoodsid
     * @return
     */
    public GoodsPojo findOneGoodsById(String sellgoodsid){
        return this.goodsMapper.findGoodsById(sellgoodsid);
    }


    /**
     * 获取最新出售的商品(新品)
     * @return
     */
    public List<GoodsPojo> findNewSellGoods() {
        return goodsMapper.findNewSellGoods();
    }

    public List<GoodsPojo> findHotSellGoods() {
        return goodsMapper.findHotSellGoods();
    }
}
