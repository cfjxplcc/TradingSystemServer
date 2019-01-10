package com.fjnu.tradingsysrem.spring.dao.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/14.
 */
@RepositoryDefinition(domainClass = ShopeeOrderInfo.class, idClass = String.class)
public interface ShopeeOrderInfoDao {

    ShopeeOrderInfo save(ShopeeOrderInfo shopeeOrderInfo);

    ShopeeOrderInfo findByOrderSn(String orderSn);

    void saveAndFlush(ShopeeOrderInfo shopeeOrderInfo);

    void delete(ShopeeOrderInfo shopeeOrderInfo);

    /**
     * 根据订单状态和订单创建时间查找
     *
     * @param orderStatus 订单状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    List<ShopeeOrderInfo> findAllByOrderStatusAndCreateTimeBetweenOrderByShopeeShopInfoAscCreateTimeDesc(String orderStatus, java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

}
