package com.secondhand.service;

import com.secondhand.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LeiMing on 2020/3/2 9:39
 */
@Service
@Transactional
public class GoodsClickService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     *
     * @param sellgoodsid
     * @return
     */
    public ResponseEntity<Void> goodsclickmount(String sellgoodsid) {
        this.goodsMapper.savegoodsclick(sellgoodsid);
        return ResponseEntity.ok(null);
    }
}
