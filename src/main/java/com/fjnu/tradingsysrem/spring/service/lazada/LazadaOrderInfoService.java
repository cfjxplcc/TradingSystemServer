package com.fjnu.tradingsysrem.spring.service.lazada;

import com.fjnu.tradingsysrem.lazada.platform.bean.ShipmentProviderBean;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by LCC on 2018/3/20.
 */
public interface LazadaOrderInfoService {

    LazadaOrderInfo get(String id);

    List<LazadaOrderInfo> list();

    List<LazadaOrderInfo> getAllByParameters(String lazadaShopInfoId, String orderStatus, String beginTime, String endTime);

    LazadaOrderInfo save(LazadaOrderInfo lazadaOrderInfo);

    void update(String id, LazadaOrderInfo lazadaOrderInfo);

    Set<LazadaOrderInfo> findByPurchaseOrderInfoExpressIsNull();

    Set<LazadaOrderInfo> getAllByPurchaseOrderInfoThirdPartyOrderId(String thirdPartyOrderId);

    List<ShipmentProviderBean> getShipmentProviderByLazadaShopInfo(String lazadaShopInfoId);

    String changeOrderStatusToReadyToShip(String orderId, String shippingProvider);

    void manualSyncLazadaOrderInfo(String beginTime, String endTime);

    LazadaOrderInfo syncLazadaOrderInfo(String orderId);

    boolean updateLazadaOrderInfoOverseasExpressPrice(String id, float overseasExpressPrice);

    void syncLazadaOrderInfo(String beginTime, String endTime);

    Set<LazadaOrderInfo> getByOrderExpressNumber(String orderExpressNumber);

    boolean updateLazadaOrderInfoIsDelivery(String id, boolean isDelivery);

    boolean updateLazadaOrderInfoRemark(String id, String remark);

    List<LazadaOrderInfo> getOrderDeliveryStatusIsFalse();

    List<LazadaOrderInfo> getByEmail(String email, String orderStatus, String beginTime, String endTime);
}
