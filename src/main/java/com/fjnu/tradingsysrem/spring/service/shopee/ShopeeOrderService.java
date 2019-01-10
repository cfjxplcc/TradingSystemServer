package com.fjnu.tradingsysrem.spring.service.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;

/**
 * Created by luochunchen on 2018/12/17.
 */
public interface ShopeeOrderService {

    /**
     * 根据订单创建时间同步shopee订单数据
     *
     * @param shopeeShopInfo
     * @param beginTime      开始时间,单位:milliseconds
     * @param endTime        结束时间,单位:milliseconds （时间不能超过15天）
     */
    void synchShopeeOrderInfoFromPlatformByCreateTime(ShopeeShopInfo shopeeShopInfo, long beginTime, long endTime);

}
