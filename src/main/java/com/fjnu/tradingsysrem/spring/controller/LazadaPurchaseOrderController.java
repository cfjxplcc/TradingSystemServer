package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.model.lazada.PurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LCC on 2018/3/26.
 */
@RestController
public class LazadaPurchaseOrderController {

    @Autowired
    private LazadaPurchaseOrderService lazadaPurchaseOrderService;

    @GetMapping("/purchaseorderinfo")
    public ResponseEntity<List<PurchaseOrderInfo>> listAll() {
        return ResponseEntity.ok().body(lazadaPurchaseOrderService.listAll());
    }

    @GetMapping("/purchaseorderinfo/{id}")
    public ResponseEntity<PurchaseOrderInfo> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaPurchaseOrderService.get(id));
    }

    @GetMapping("/purchaseorderinfo/lazadaorderinfo/{id}")
    public ResponseEntity<List<PurchaseOrderInfo>> getByLazadaOrderInfo(@PathVariable("id") String lazadaOrderInfoId) {
        return ResponseEntity.ok().body(lazadaPurchaseOrderService.getByLazadaOrderInfo(lazadaOrderInfoId));
    }

    @PostMapping("/purchaseorderinfo/")
    public ResponseEntity<String> save(@RequestBody PurchaseOrderInfo lazadaOrderItemsInfo) {
        return ResponseEntity.ok().body("New PurchaseOrderInfo has been saved with ID:" + lazadaPurchaseOrderService.save(lazadaOrderItemsInfo));
    }

    @PutMapping("/purchaseorderinfo/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody PurchaseOrderInfo lazadaOrderItemsInfo) {
        lazadaPurchaseOrderService.update(id, lazadaOrderItemsInfo);
        return ResponseEntity.ok().body("Updated successfully");
    }

    @DeleteMapping("/purchaseorderinfo/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        lazadaPurchaseOrderService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }

    @GetMapping("/purchaseorderinfo/lazadashopinfo/{id}")
    public ResponseEntity<List<PurchaseOrderInfo>> getAllByLazadaShopInfo(@PathVariable("id") String lazadaShopInfoId, String beginTime, String endTime) {
        return ResponseEntity.ok().body(lazadaPurchaseOrderService.getByLazadaShopInfo(lazadaShopInfoId, beginTime, endTime));
    }

}
