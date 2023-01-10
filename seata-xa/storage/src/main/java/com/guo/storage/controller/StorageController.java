package com.guo.storage.controller;

import com.guo.common.RespBean;
import com.guo.storage.service.StorageService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
    @Autowired
    StorageService storageService;

    @PostMapping("/deduct")
    public RespBean deduct(String productId, Integer count){
        if(storageService.deduct(productId,count)){
            return RespBean.ok("扣库存成功");
        }
        return RespBean.error("扣库存失败");
    }
}
