package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeeShopService {

    List<ShopeeShopInfo> getShopeeShopInfoList();

    String receiveShopeeAuthorizationShopInfo(int shopId);

    void synchShopeeShopInfoFromPlatform();

}
