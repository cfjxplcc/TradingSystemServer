package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaOrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LCC on 2018/3/27.
 */
@RestController
public class LazadaOrderItemsController {

    @Autowired
    private LazadaOrderItemsService lazadaOrderItemsService;

    @GetMapping("/lazadaorderitemsinfo")
    public ResponseEntity<List<LazadaOrderItemsInfo>> listAll() {
        return ResponseEntity.ok().body(lazadaOrderItemsService.listAll());
    }

    @GetMapping("/lazadaorderitemsinfo/{id}")
    public ResponseEntity<LazadaOrderItemsInfo> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaOrderItemsService.get(id));
    }

    @GetMapping("/lazadaorderitemsinfo/lazadaorderinfo/{id}")
    public ResponseEntity<List<LazadaOrderItemsInfo>> getByLazadaOrderInfo(@PathVariable("id") String lazadaOrderInfoId) {
        return ResponseEntity.ok().body(lazadaOrderItemsService.getByLazadaOrderInfo(lazadaOrderInfoId));
    }

    @PostMapping("/lazadaorderitemsinfo/")
    public ResponseEntity<String> save(@RequestBody LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        return ResponseEntity.ok().body("New LazadaOrderItemsInfo has been saved with ID:" + lazadaOrderItemsService.save(lazadaOrderItemsInfo));
    }

    @PutMapping("/lazadaorderitemsinfo/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        lazadaOrderItemsService.update(id, lazadaOrderItemsInfo);
        return ResponseEntity.ok().body("Updated successfully");
    }

    @DeleteMapping("/lazadaorderitemsinfo/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        lazadaOrderItemsService.delete(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }


}
