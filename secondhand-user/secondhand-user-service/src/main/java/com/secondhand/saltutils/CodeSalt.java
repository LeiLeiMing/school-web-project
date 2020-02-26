package com.secondhand.saltutils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.security.DigestException;
import java.util.UUID;

/**
 * 加密盐
 * Created by LeiMing on 2020/2/14 10:34
 */
public class CodeSalt {

    public static String md5Hex(String data,String salt){
        if (StringUtils.isBlank(salt)){
            salt = data.hashCode()+"";
        }
        return DigestUtils.md5Hex(data+DigestUtils.md5Hex(data));
    }

    public static String shaHex(String data,String salt){
        if(StringUtils.isBlank(salt)){
            salt = data.hashCode()+"";
        }
        return DigestUtils.sha512Hex(salt+DigestUtils.sha512Hex(data));
    }

    public static String generateSALT(){
        return StringUtils.replace(UUID.randomUUID().toString(),"-","");
    }
}
