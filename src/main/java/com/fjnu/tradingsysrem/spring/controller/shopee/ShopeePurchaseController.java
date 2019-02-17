package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeePurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeePurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LCC on 2018/3/26.
 */
@RestController
@RequestMapping("shopee/purchase")
public class ShopeePurchaseController {

    @Autowired
    private ShopeePurchaseOrderService shopeePurchaseOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<ShopeePurchaseOrderInfo> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(shopeePurchaseOrderService.get(id));
    }

    @GetMapping("/order/{orderSn}")
    public ResponseEntity<List<ShopeePurchaseOrderInfo>> getByShopeeOrderInfo(@PathVariable("orderSn") String shopeeOrderSn) {
        return ResponseEntity.ok().body(shopeePurchaseOrderService.getByShopeeOrderInfo(shopeeOrderSn));
    }

    @PostMapping("/")
    public ResponseEntity<String> save(@RequestBody ShopeePurchaseOrderInfo shopeePurchaseOrderInfo) {
        return ResponseEntity.ok().body("New ShopeePurchaseOrderInfo has been saved with ID:" + shopeePurchaseOrderService.save(shopeePurchaseOrderInfo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody ShopeePurchaseOrderInfo shopeePurchaseOrderInfo) {
        shopeePurchaseOrderService.update(id, shopeePurchaseOrderInfo);
        return ResponseEntity.ok().body("Updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        shopeePurchaseOrderService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }

}
