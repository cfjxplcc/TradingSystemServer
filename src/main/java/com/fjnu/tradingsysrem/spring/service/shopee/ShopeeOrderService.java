package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeeOrderService {

    /**
     * 根据订单创建时间同步shopee订单数据
     *
     * @param shopeeShopInfo 店铺信息
     * @param beginTime      开始时间,单位:milliseconds
     * @param endTime        结束时间,单位:milliseconds （时间不能超过15天）
     */
    void synchShopeeOrderInfoFromPlatformByCreateTime(ShopeeShopInfo shopeeShopInfo, long beginTime, long endTime);

    /**
     * 根据订单Sn获取数据
     *
     * @param orderSn
     * @return
     */
    ShopeeOrderInfo getBySn(String orderSn);

    /**
     * 根据店铺id、订单状态、订单时间查询数据
     *
     * @param shopId      店铺id
     * @param orderStatus 订单状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    List<ShopeeOrderInfo> getAllByShopInfoAndParameters(int shopId, String orderStatus, String beginTime, String endTime);

    /**
     * 根据订单状态、订单时间查询数据
     *
     * @param orderStatus 订单状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    List<ShopeeOrderInfo> getAllByParameters(String orderStatus, String beginTime, String endTime);

    /**
     * 更新订单出货状态
     *
     * @param orderSn    订单sn
     * @param isDelivery true-已出货 / false-未出货
     * @return
     */
    boolean updateDeliveryStatus(String orderSn, boolean isDelivery);

    /**
     * 更新订单(公司内部)备注
     *
     * @param orderSn 订单sn
     * @param remark  备注信息
     * @return
     */
    boolean updateRemark(String orderSn, String remark);

    /**
     * 更新订单海外运费
     *
     * @param orderSn              订单sn
     * @param overseasExpressPrice 运费
     * @return
     */
    boolean updateOverseasExpressPrice(String orderSn, float overseasExpressPrice);

    /**
     * 根据采购快递单号查询
     *
     * @param orderExpressNumber 采购快递单号
     * @return
     */
    Set<ShopeeOrderInfo> getByOrderExpressNumber(String orderExpressNumber);

    /**
     * 查询采购快递单号为空的订单
     *
     * @return
     */
    Set<ShopeeOrderInfo> getByPurchaseOrderExpressIsNull();

    /**
     * 查询还未出货的订单
     *
     * @return
     */
    List<ShopeeOrderInfo> getByOrderDeliveryStatusIsFalse();

    /**
     * 根据采购第三方平台订单id查询
     *
     * @param thirdPartyOrderId 第三方平台订单id
     * @return
     */
    Set<ShopeeOrderInfo> getByPurchaseOrderInfoThirdPartyOrderId(String thirdPartyOrderId);

    /**
     * 更新数据库中已有订单的订单状态
     *
     * @param orderSn     订单sn
     * @param orderStatus 订单状态
     * @return true-修改成功/false-订单不存在或者失败
     */
    boolean updateOrderStatus(String orderSn, String orderStatus);

    /**
     * 更新数据库中已有订单的shopee平台快递单号
     *
     * @param orderSn    订单sn
     * @param trackingNo shopee平台快递单号
     * @return
     */
    boolean updateOrderTrackingNo(String orderSn, String trackingNo);
}
