package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeePurchaseOrderInfo;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeePurchaseOrderService {

    String save(ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    ShopeePurchaseOrderInfo get(String id);

    List<ShopeePurchaseOrderInfo> getByShopeeOrderInfo(String shopeeOrderSn);

    void update(String id, ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    void delete(String id);
}
