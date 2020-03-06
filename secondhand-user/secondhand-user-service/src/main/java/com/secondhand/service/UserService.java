package com.secondhand.service;

import com.secondhand.common.CodeUtils;
import com.secondhand.mapper.UserMapper;
import com.secondhand.Users;
import com.secondhand.saltutils.CodeSalt;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * user service
 * Created by LeiMing on 2020/2/13 18:35
 */
@Service
public class UserService {

    //rabiitmq操作模板，用于操作连接的消息队列
    @Autowired
    private AmqpTemplate amqpTemplate;

    //redis操作模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    //存进redis的数据前缀
    private static final String KEY_PREFIX = "user:code:";

    //发送注册验证码并存进redis
    public void sendCode(String phone) {
        if (StringUtils.isBlank(phone)){
            return;
        }
        //获取随机6位数字验证码
        String code = CodeUtils.getRandomCode();
        //发送消息给MQ
        Map<String,String> map = new HashMap<String,String>();
        map.put("phone",phone);
        map.put("code",code);
        this.amqpTemplate.convertAndSend("secondhand.sms.exchange","phoneCode",map);
        //将核对验证码存进redis
        this.redisTemplate.opsForValue().set(KEY_PREFIX+phone,code,1, TimeUnit.MINUTES);
    }

    //注册用户
    public boolean register(Users user, String code) {
        //redis获取验证码
        String reidsCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        //校验验证码
        if (!StringUtils.equals(code,reidsCode)){
            return false;
        }
        //生成盐
        String salt = CodeSalt.generateSALT();
        user.setSalt(salt);
        //加密
        user.setPassword(CodeSalt.md5Hex(user.getPassword(), salt));
        user.setUserid(CodeUtils.getRandomCode());
        user.setRegistertime(new Date());
        user.setLogintime(new Date());
        //注册
        userMapper.insertSelective(user);
        return true;
    }

    //登录
    public Users queryUser(String phone, String password) {
        Users record = new Users();
        record.setPhone(phone);
        Users user = this.userMapper.selectOne(record);
        if (user == null){
            return null;
        }
        //获取盐并对登录的用户加密
        String saltPassword = CodeSalt.md5Hex(password, user.getSalt());
        //比较
        if (StringUtils.equals(saltPassword,user.getPassword())){
            return user;
        }
        return null;
    }

    public List<Users> findUsrById(String id) {
        Example example = new Example(Users.class);
        example.createCriteria()
                .andEqualTo("id",id);
        return userMapper.selectByExample(example);

    }
}
