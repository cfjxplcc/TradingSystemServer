package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LCC on 2018/3/17.
 */
@RestController
public class LazadaShopInfoController {

    @Autowired
    private LazadaShopInfoService lazadaShopInfoService;

    @GetMapping("/lazadashopinfo/{id}")
    public ResponseEntity<LazadaShopInfo> get(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaShopInfoService.get(id));
    }

    @GetMapping("/lazadashopinfo")
    public ResponseEntity<List<LazadaShopInfo>> list() {
        return ResponseEntity.ok().body(lazadaShopInfoService.getAll());
    }

    @PostMapping("/lazadashopinfo/")
    public ResponseEntity<String> save(@RequestBody LazadaShopInfo lazadaShopInfo) {
        lazadaShopInfoService.save(lazadaShopInfo);
        return ResponseEntity.ok().body("New LazadaShopInfo has been saved with ID:" + lazadaShopInfo.getId());
    }

    @PutMapping("/lazadashopinfo/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody LazadaShopInfo lazadaShopInfo) {
        lazadaShopInfoService.update(id, lazadaShopInfo);
        return ResponseEntity.ok().body("Updated successfully");
    }

    @DeleteMapping("/lazadashopinfo/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        lazadaShopInfoService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }

}
