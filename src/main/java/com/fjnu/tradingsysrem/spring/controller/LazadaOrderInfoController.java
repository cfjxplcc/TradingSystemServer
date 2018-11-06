package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.lazada.platform.bean.ShipmentProviderBean;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.service.LazadaOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by LCC on 2018/3/20.
 */
@RestController
public class LazadaOrderInfoController {

    @Autowired
    private LazadaOrderInfoService lazadaOrderInfoService;

    /**
     * 根据订单id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/lazadaorderinfo/{id}")
    public ResponseEntity<LazadaOrderInfo> get(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.get(id));
    }

    @GetMapping("/lazadaorderinfo")
    public ResponseEntity<List<LazadaOrderInfo>> list() {
        return ResponseEntity.ok().body(lazadaOrderInfoService.list());
    }

    @GetMapping("/lazadaorderinfo/")
    public ResponseEntity<List<LazadaOrderInfo>> getAllByLazadaShopInfo(String lazadaShopInfoId, String orderStatus, String beginTime, String endTime) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getAllByParameters(lazadaShopInfoId, orderStatus, beginTime, endTime));
    }

    @PostMapping("/lazadaorderinfo/")
    public ResponseEntity<LazadaOrderInfo> save(@RequestBody LazadaOrderInfo lazadaOrderInfo) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.save(lazadaOrderInfo));
    }

    @PutMapping("/lazadaorderinfo/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody LazadaOrderInfo lazadaShopInfo) {
        lazadaOrderInfoService.update(id, lazadaShopInfo);
        return ResponseEntity.ok().body("Updated successfully");
    }

    /**
     * 更新订单海外运费
     *
     * @param id
     * @param overseasExpressPrice
     * @return
     */
    @PutMapping("/lazadaorderinfo/{id}/updateOverseasExpressPrice")
    public ResponseEntity<String> updateLazadaOrderInfoOverseasExpressPrice(@PathVariable("id") String id, @RequestParam("OverseasExpressPrice") float overseasExpressPrice) {
        boolean result = lazadaOrderInfoService.updateLazadaOrderInfoOverseasExpressPrice(id, overseasExpressPrice);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

    /**
     * 更新订单出货状态
     *
     * @param id         订单id
     * @param isDelivery true-已出货 / false-未出货
     * @return
     */
    @PutMapping("/lazadaorderinfo/{id}/updateLazadaOrderInfoIsDelivery")
    public ResponseEntity<String> updateLazadaOrderInfoIsDelivery(@PathVariable("id") String id, @RequestParam("IsDelivery") boolean isDelivery) {
        boolean result = lazadaOrderInfoService.updateLazadaOrderInfoIsDelivery(id, isDelivery);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

    /**
     * 更新订单备注
     *
     * @param id
     * @param remark
     * @return
     */
    @PutMapping("/lazadaorderinfo/{id}/updateLazadaOrderInfoRemark")
    public ResponseEntity<String> updateLazadaOrderInfoRemark(@PathVariable("id") String id, @RequestParam("remark") String remark) {
        boolean result = lazadaOrderInfoService.updateLazadaOrderInfoRemark(id, remark);
        return ResponseEntity.ok().body("Updated is successful:" + result);
    }

    @GetMapping("/lazadaorderinfo/findByPurchaseOrderInfoExpressIsNull")
    public ResponseEntity<Set<LazadaOrderInfo>> findByPurchaseOrderInfoExpressIsNull() {
        return ResponseEntity.ok().body(lazadaOrderInfoService.findByPurchaseOrderInfoExpressIsNull());
    }

    @GetMapping("/lazadaorderinfo/getByPurchaseOrderInfoThirdPartyOrderId/{id}")
    public ResponseEntity<Set<LazadaOrderInfo>> getByPurchaseOrderInfoThirdPartyOrderId(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getAllByPurchaseOrderInfoThirdPartyOrderId(id));
    }

    @GetMapping("/lazadaorderinfo/getShipmentProviderByLazadaShopInfo/{id}")
    public ResponseEntity<List<ShipmentProviderBean>> getShipmentProviderByLazadaShopInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getShipmentProviderByLazadaShopInfo(id));
    }

    /**
     * 获取还未出货的订单
     *
     * @return
     */
    @GetMapping("/lazadaorderinfo/getOrderDeliveryStatusIsFalse")
    public ResponseEntity<List<LazadaOrderInfo>> getOrderDeliveryStatusIsFalse() {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getOrderDeliveryStatusIsFalse());
    }

    /**
     * 更改订单状态为ReadyToShip
     *
     * @param id
     * @param shippingProvider
     * @return
     */
    @PutMapping("/lazadaorderinfo/{id}/changeOrderStatusToReadyToShip/")
    public ResponseEntity<String> changeOrderStatusToReadyToShip(@PathVariable("id") String id, @RequestBody String shippingProvider) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.changeOrderStatusToReadyToShip(id, shippingProvider));
    }

    /**
     * 手动同步数据
     *
     * @param beginTime 起始时间
     * @param endTime   结束时间
     * @return
     */
    @PutMapping("/lazadaorderinfo/manualSyncLazadaOrderInfo")
    public ResponseEntity<String> manualSyncLazadaOrderInfo(@RequestParam("beginTime") String beginTime, @RequestParam("endTime") String endTime) {
        lazadaOrderInfoService.manualSyncLazadaOrderInfo(beginTime, endTime);
        return ResponseEntity.ok().body("Call api successful");
    }

    /**
     * 手动同步单笔数据，包含详细数据
     *
     * @param id 需要同步的订单ID
     * @return
     */
    @PutMapping("/lazada/order/{id}/sync")
    public ResponseEntity<LazadaOrderInfo> syncLazadaOrderInfo(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.syncLazadaOrderInfo(id));
    }

    /**
     * 根据采购快递单号查询lazada订单数据
     *
     * @param orderExpressNumber
     * @return
     */
    @GetMapping("/lazadaorderinfo/getByOrderExpressNumber")
    public ResponseEntity<Set<LazadaOrderInfo>> getByOrderExpressNumber(@RequestParam("OrderExpressNumber") String orderExpressNumber) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getByOrderExpressNumber(orderExpressNumber));
    }

    /**
     * 根据账户查询订单
     *
     * @param email
     * @param orderStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/lazadaorderinfo/getByEmail")
    public ResponseEntity<List<LazadaOrderInfo>> getByEmail(String email, String orderStatus, String beginTime, String endTime) {
        return ResponseEntity.ok().body(lazadaOrderInfoService.getByEmail(email, orderStatus, beginTime, endTime));
    }

}
