package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderService;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import com.fjnu.tradingsysrem.spring.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by luochunchen on 2019/3/5.
 */
@RestController
@RequestMapping("shopee/receive_push_mechanism/")
public class ShopeeReceivePushMechanismController {
    @Autowired
    private ShopeeShopService shopeeShopService;
    @Autowired
    private ShopeeOrderService shopeeOrderService;

    @PostMapping(headers = {"Content-Type=application/json"})
    public ResponseEntity receivePushMechanism(@RequestHeader("Authorization") String authorization, @RequestBody Object requestBody) {
        System.out.println("Authorization:" + authorization);
        String bodyString = JSON.toJSONString(requestBody);
        System.out.println("RequestBody:" + bodyString);

        // TODO: 2019/3/6 验证Authorization

        PushMechanismData pushMechanismData = JSON.parseObject(bodyString, PushMechanismData.class);
        String orderSn;
        switch (pushMechanismData.code) {
            case 0:
                // Test callback url
                break;
            case 1:
                // Shop authorization for partners
                if (pushMechanismData.getShop_id() > 0) {
                    shopeeShopService.receiveShopeeAuthorizationShopInfo(pushMechanismData.getShop_id());
                }
                break;
            case 2:
                // Shop deauthorization for partners
                if (pushMechanismData.getShop_id() > 0) {
                    shopeeShopService.receiveShopeeCancelAuthorizationShopInfo(pushMechanismData.getShop_id());
                }
                break;
            case 3:
                // Order status update push
                orderSn = ((JSONObject) pushMechanismData.getData()).getString("ordersn");
                String status = ((JSONObject) pushMechanismData.getData()).getString("status");
                if (TextUtils.isEmpty(orderSn) || TextUtils.isEmpty(status)) {
                    System.out.println("error:orderSn==null||status==null");
                    break;
                }
                shopeeOrderService.updateOrderStatus(orderSn, status);
                break;
            case 4:
                // TrackingNo push
                orderSn = ((JSONObject) pushMechanismData.getData()).getString("ordersn");
                String trackingNo = ((JSONObject) pushMechanismData.getData()).getString("trackingno");
                if (TextUtils.isEmpty(orderSn) || TextUtils.isEmpty(trackingNo)) {
                    System.out.println("error:orderSn==null||trackingNo==null");
                    break;
                }
                shopeeOrderService.updateOrderTrackingNo(orderSn, trackingNo);
                break;
        }

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class PushMechanismData {
        private int code;
        private int shop_id;
        private int success;
        private String extra;
        private Object data;
        private int timestamp;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
