package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.lazada.PurchaseOrderInfo;

import java.util.List;

/**
 * Created by LCC on 2018/3/26.
 */
public interface PurchaseOrderInfoService {

    String save(PurchaseOrderInfo purchaseOrderInfo);

    PurchaseOrderInfo get(String id);

    List<PurchaseOrderInfo> listAll();

    List<PurchaseOrderInfo> getByLazadaOrderInfo(String lazadaOrderInfoId);

    void update(String id, PurchaseOrderInfo purchaseOrderInfo);

    void delete(String id);

    List<PurchaseOrderInfo> getByLazadaShopInfo(String lazadaShopInfoId, String beginTime, String endTime);

}
