package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;

import java.util.List;

/**
 * Created by LCC on 2018/3/17.
 */
public interface LazadaShopInfoService {

    LazadaShopInfo save(LazadaShopInfo shopInfo);

    LazadaShopInfo get(String id);

    void update(String id, LazadaShopInfo lazadaShopInfo);

    void delete(String id);

    List<LazadaShopInfo> getAll();

}
