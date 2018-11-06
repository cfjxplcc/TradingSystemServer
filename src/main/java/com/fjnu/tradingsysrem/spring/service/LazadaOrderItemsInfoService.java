package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;

import java.util.List;

/**
 * Created by LCC on 2018/3/26.
 */
public interface LazadaOrderItemsInfoService {

    String save(LazadaOrderItemsInfo lazadaOrderItemsInfo);

    LazadaOrderItemsInfo get(String id);

    List<LazadaOrderItemsInfo> listAll();

    List<LazadaOrderItemsInfo> getByLazadaOrderInfo(String lazadaOrderInfoId);

    void update(String id, LazadaOrderItemsInfo lazadaOrderItemsInfo);

    void delete(String id);

}
