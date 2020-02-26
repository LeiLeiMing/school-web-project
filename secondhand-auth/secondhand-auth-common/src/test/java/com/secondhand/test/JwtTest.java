package com.secondhand.test;

import com.secondhand.pojo.UserInfo;
import com.secondhand.utils.JwtUtils;
import com.secondhand.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by LeiMing on 2020/2/18 20:00
 */
public class JwtTest {

    //私钥存储位置
    private static final String priKeyPath = "C:\\Key\\rsa.pri";

    //公钥存储位置
    private static final String pubKeyPath = "C:\\Key\\rsa.pub";

    //公钥
    private PublicKey publicKey;

    //私钥
    private PrivateKey privateKey;

    /**
     * 生成公钥和私有并本地保存，运行时需要把 @Before 注释
     * secret 加密盐
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    //获取私钥和公钥
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    //生成Token
    @Test
    public void testGenerateToken() throws Exception {
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    //解析
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MSwicGhvbmUiOiIxNTc3ODY4OTk3OCIsImV4cCI6MTU4MjMzNTYxM30.Ia7902ny1eYjhVhwzfi1MwH7U4CXUix5edG6YW-YxB0nuHJpmmY9LynuiqdoB-9hQVJLnOVGGLdxQeP7qFidUz-EeN1T3zvo7RiXf9aXpjFXDlQllF2y_PlAdw8yeBKuim-jFm6v5E2UgNUAsU5ZuuinTh4V0XdBVADfDMR83IQ";
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getPhone());
    }
}
