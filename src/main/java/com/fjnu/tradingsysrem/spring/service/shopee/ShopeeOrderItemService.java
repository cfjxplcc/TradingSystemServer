package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderItemsInfo;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeeOrderItemService {

    /**
     * 根据订单查找订单详细数据
     *
     * @param orderSn 订单sn
     * @return
     */
    List<ShopeeOrderItemsInfo> getAllByShopeeOrderInfo(String orderSn);
}
