package com.fjnu.tradingsysrem.spring.controller.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

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

    /**
     * 根据采购快递单号查询
     *
     * @param orderExpressNumber 采购快递单号
     * @return
     */
    @GetMapping("/get_by_purchase_order_express_number")
    public ResponseEntity<Set<ShopeeOrderInfo>> getByPurchaseOrderExpressNumber(@RequestParam("OrderExpressNumber") String orderExpressNumber) {
        return ResponseEntity.ok().body(shopeeOrderService.getByOrderExpressNumber(orderExpressNumber));
    }

    /**
     * 查询采购快递单号为空的订单
     *
     * @return
     */
    @GetMapping("/get_by_purchase_order_express_is_null")
    public ResponseEntity<Set<ShopeeOrderInfo>> getByPurchaseOrderExpressIsNull() {
        return ResponseEntity.ok().body(shopeeOrderService.getByPurchaseOrderExpressIsNull());
    }

    /**
     * 查询还未出货的订单
     *
     * @return
     */
    @GetMapping("/get_by_order_delivery_status_is_false")
    public ResponseEntity<List<ShopeeOrderInfo>> getByOrderDeliveryStatusIsFalse() {
        return ResponseEntity.ok().body(shopeeOrderService.getByOrderDeliveryStatusIsFalse());
    }

    /**
     * 根据采购第三方平台订单id查询
     *
     * @param thirdPartyOrderId 第三方平台订单id
     * @return
     */
    @GetMapping("/get_by_purchase_order_third_party_order_id")
    public ResponseEntity<Set<ShopeeOrderInfo>> getByPurchaseOrderInfoThirdPartyOrderId(@RequestParam("third_party_order_id") String thirdPartyOrderId) {
        return ResponseEntity.ok().body(shopeeOrderService.getByPurchaseOrderInfoThirdPartyOrderId(thirdPartyOrderId));
    }

    /**
     * 根据订单创建时间同步数据
     *
     * @param beginTime 起始时间 yyyy-MM-dd HH:mm:ss
     * @param endTime   结束时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    @PutMapping("/manual_sync_order_info")
    public ResponseEntity<String> manualSyncOrderInfo(@RequestParam("beginTime") String beginTime, @RequestParam("endTime") String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long begin, end;
        try {
            begin = simpleDateFormat.parse(beginTime).getTime();
            end = simpleDateFormat.parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.ok().body("error:beginTime or endTime is wrong!!!");
        }

        shopeeOrderService.synchShopeeOrderInfoFromPlatformByCreateTime(begin, end);
        return ResponseEntity.ok().body("Call api successful");
    }

    /**
     * 根据订单商品sku查询未出货的订单数据
     *
     * @param sku 商品sku
     * @return
     */
    @GetMapping("/get_by_order_item_sku_and_delivery_is_false")
    public ResponseEntity<Set<ShopeeOrderInfo>> getByItemSkuAndDeliveryIsFalse(@RequestParam("sku") String sku) {
        return ResponseEntity.ok().body(shopeeOrderService.getByItemSkuAndDeliveryIsFalse(sku));
    }

}
