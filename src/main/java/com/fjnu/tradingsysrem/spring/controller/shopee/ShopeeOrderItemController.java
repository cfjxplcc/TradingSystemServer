package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luochunchen on 2019/2/15.
 */
@RestController
@RequestMapping("shopee/order_item")
public class ShopeeOrderItemController {

    @Autowired
    private ShopeeOrderItemService shopeeOrderItemService;

    @GetMapping("/order/{orderSn}/")
    public ResponseEntity<List<ShopeeOrderItemsInfo>> getAllByShopeeOrderInfo(@PathVariable("orderSn") String orderSn) {
        return ResponseEntity.ok().body(shopeeOrderItemService.getAllByShopeeOrderInfo(orderSn));
    }
}
