package com.secondhand.project.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;

/**
 * 上图片文件至OSS工具类
 * Created by LeiMing on 2020/2/26 16:53
 */
public class UploadToOssUtils extends OssKey {

    public static URL uploadFile(byte[] bytes, String name){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEYID, ACCESS_Key_SECRET);
        String objectName = name;
        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        ossClient.putObject(BUCKET_NAME, objectName, new ByteArrayInputStream(bytes));
        // 指定过期时间为10年
        Date expiration = new Date(new Date().getTime() +  1000*60*525600*10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(BUCKET_NAME, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        //获取云服务器上文件的地址
        URL url = ossClient.generatePresignedUrl(req);
        // 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }
}
