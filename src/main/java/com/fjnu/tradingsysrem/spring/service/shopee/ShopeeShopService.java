package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeeShopService {

    /**
     * 获取本地所有店铺信息
     *
     * @return
     */
    List<ShopeeShopInfo> getShopeeShopInfoList();

    /**
     * 接收店铺授权码
     *
     * @param shopId
     * @return
     */
    String receiveShopeeAuthorizationShopInfo(int shopId);

    /**
     * 同步shopee平台已授权店铺信息
     *
     * @return
     */
    String synchShopeeShopInfoFromPlatform();

    /**
     * 接收取消店铺授权码
     *
     * @param shopId
     * @return
     */
    String receiveShopeeCancelAuthorizationShopInfo(int shopId);

}
