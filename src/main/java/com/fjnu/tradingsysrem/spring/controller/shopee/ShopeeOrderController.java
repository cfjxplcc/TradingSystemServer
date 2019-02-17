package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by luochunchen on 2019/1/21.
 */
@RestController
@RequestMapping("shopee/order")
public class ShopeeOrderController {

    @Autowired
    private ShopeeOrderService shopeeOrderService;

    /**
     * 根据订单id查询
     *
     * @param orderSn
     * @return
     */
    @GetMapping("/{orderSn}")
    public ResponseEntity<ShopeeOrderInfo> getByOrderSn(@PathVariable("orderSn") String orderSn) {
        return ResponseEntity.ok().body(shopeeOrderService.getBySn(orderSn));
    }

    /**
     * 根据店铺id、订单状态、订单时间查询数据
     *
     * @param shopId
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/shop/{shopId}/")
    public ResponseEntity<List<ShopeeOrderInfo>> getAllByShopInfoAndParameters(@PathVariable("shopId") int shopId, String orderStatus, String beginTime, String endTime) {
        return ResponseEntity.ok().body(shopeeOrderService.getAllByShopInfoAndParameters(shopId, orderStatus, beginTime, endTime));
    }

    /**
     * 根据订单状态、订单时间查询数据
     *
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<ShopeeOrderInfo>> getAllByParameters(String orderStatus, String beginTime, String endTime) {
        return ResponseEntity.ok().body(shopeeOrderService.getAllByParameters(orderStatus, beginTime, endTime));
    }

    /**
     * 更新订单出货状态
     *
     * @param orderSn    订单sn
     * @param isDelivery true-已出货 / false-未出货
     * @return
     */
    @PutMapping("/{orderSn}/update_delivery_status")
    public ResponseEntity<String> updateDeliveryStatus(@PathVariable("orderSn") String orderSn, @RequestParam("IsDelivery") boolean isDelivery) {
        boolean result = shopeeOrderService.updateDeliveryStatus(orderSn, isDelivery);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

    /**
     * 更新订单(公司内部)备注
     *
     * @param orderSn 订单sn
     * @param remark  备注信息
     * @return
     */
    @PutMapping("/{orderSn}/update_remark")
    public ResponseEntity<String> updateRemark(@PathVariable("orderSn") String orderSn, @RequestParam("remark") String remark) {
        boolean result = shopeeOrderService.updateRemark(orderSn, remark);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

    /**
     * 更新订单海外运费
     *
     * @param orderSn              订单sn
     * @param overseasExpressPrice 运费
     * @return
     */
    @PutMapping("/{orderSn}/update_overseas_express_price")
    public ResponseEntity<String> updateOverseasExpressPrice(@PathVariable("orderSn") String orderSn, @RequestParam("OverseasExpressPrice") float overseasExpressPrice) {
        boolean result = shopeeOrderService.updateOverseasExpressPrice(orderSn, overseasExpressPrice);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

}
