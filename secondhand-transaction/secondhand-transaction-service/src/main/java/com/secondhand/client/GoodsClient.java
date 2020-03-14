package com.secondhand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by LeiMing on 2020/3/13 16:44
 */
@FeignClient("project-service")
public interface GoodsClient {
    @GetMapping("goods/changegoodsstatus")
    public ResponseEntity<Void> changeGoodsStatus(@RequestParam("status")Integer status, @RequestParam("goodsid")String goodsid);
}
