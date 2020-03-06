package com.secondhand.service;

import com.secondhand.client.UserClient;
import com.secondhand.UserInfo;
import com.secondhand.Users;
import com.secondhand.utils.JwtUtils;
import com.secondhand.utils.KeyUrl;
import com.secondhand.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 授权中心
 * Created by LeiMing on 2020/2/18 21:13
 */
@Service
public class AuthService extends KeyUrl {

    @Autowired
    private UserClient userClient;

    //公钥
    private PublicKey publicKey;

    //私钥
    private PrivateKey privateKey;

    /**
     * 授权用户token
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    public String userauth(String phone,String password) throws Exception {
        //查询该用户
        Users user;
        try{
            user = userClient.query(phone, password);
        }catch (Exception e){
            return null;
        }
        if (user == null){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.valueOf(user.getId()));
        userInfo.setPhone(user.getPhone());
        userInfo.setAge(user.getAge());
        userInfo.setName(user.getName());
        userInfo.setUserhead(user.getUserhead());

        //获取私钥
        this.privateKey = RsaUtils.getPrivateKey(PRIKRY_PATH);

        //生成Token
        try {
           return JwtUtils.generateToken(userInfo,privateKey,30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(String token) throws Exception {
        //获取公钥
        this.publicKey = RsaUtils.getPublicKey(PUBKRY_PATH);
        return JwtUtils.getInfoFromToken(token, publicKey);
    }

    public String newToken(UserInfo userInfo) throws Exception {
        //获取新的token
        this.privateKey = RsaUtils.getPrivateKey(PRIKRY_PATH);
        String newtoken = JwtUtils.generateToken(userInfo, privateKey, 30);
        return newtoken;
    }
}
