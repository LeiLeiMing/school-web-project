package com.secondhand.service;

import com.secondhand.mapper.GoodSendMapper;
import com.secondhand.pojo.GoodSendPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LeiMing on 2020/4/21 10:29
 */
@Service
@Transactional
public class GoodSendService {

    @Autowired
    private GoodSendMapper goodSendMapper;

    public boolean shippedorder(GoodSendPojo goodSendPojo){
        int insert = this.goodSendMapper.insert(goodSendPojo);
        if (insert!=0){
            return true;
        }
        return false;
    }
}
