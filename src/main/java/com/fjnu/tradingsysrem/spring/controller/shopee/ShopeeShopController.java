package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.fjnu.tradingsysrem.shopee.ShopeeApiManager;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/14.
 */
@RestController
@RequestMapping("shopee/shop")
public class ShopeeShopController {

    @Autowired
    private ShopeeShopService shopeeShopService;

    /**
     * 获取店铺授权url
     *
     * @return
     */
    @GetMapping("/authorization/getUrl")
    public ResponseEntity<String> getAuthorizationUrl() {
        String url = ShopeeApiManager.getInstance().getShopAuthorizationUrl();
        return ResponseEntity.ok().body(url);
    }

    /**
     * 获取所有店铺信息
     *
     * @return
     */
    @GetMapping("/shop_info_all")
    public ResponseEntity<List<ShopeeShopInfo>> getShopeeShopInfoList() {
        return ResponseEntity.ok().body(shopeeShopService.getShopeeShopInfoList());
    }

    /**
     * 接收授权结果
     *
     * @param shopId
     * @return
     */
    @GetMapping("/receive_authorization_result/")
    public ResponseEntity<String> receiveShopeeAuthorizationResult(@RequestParam("shop_id") int shopId) {
        return ResponseEntity.ok().body(shopeeShopService.receiveShopeeAuthorizationShopInfo(shopId));
    }

    /**
     * 获取取消店铺授权url
     *
     * @return
     */
    @GetMapping("/cancel_authorization/getUrl")
    public ResponseEntity<String> getCancelAuthorizationUrl() {
        String url = ShopeeApiManager.getInstance().getShopCancelAuthorizeUrl();
        return ResponseEntity.ok().body(url);
    }

    /**
     * 接收授权结果
     *
     * @param shopId
     * @return
     */
    @GetMapping("/receive_cancel_authorization_result/")
    public ResponseEntity<String> receiveShopeeCancelAuthorizationResult(@RequestParam("shop_id") int shopId) {
        return ResponseEntity.ok().body(shopeeShopService.receiveShopeeCancelAuthorizationShopInfo(shopId));
    }

}
