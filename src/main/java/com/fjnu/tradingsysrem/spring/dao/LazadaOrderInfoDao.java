package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by LCC on 2018/3/20.
 */
@RepositoryDefinition(domainClass = LazadaOrderInfo.class, idClass = String.class)
public interface LazadaOrderInfoDao {

    LazadaOrderInfo save(LazadaOrderInfo lazadaOrderInfo);

    void saveAndFlush(LazadaOrderInfo lazadaOrderInfo);

    LazadaOrderInfo findById(String id);

    LazadaOrderInfo findByLazadaOrderId(Long lazadaOrderId);

    List<LazadaOrderInfo> findAllByOrderByCreateTimeDesc();

    List<LazadaOrderInfo> findAllByCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByOrderStatusAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(String orderStatus, java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByOverseasExpressNumberIsNotNullAndDeliveryIsFalseOrderByCreateTimeAsc();

    List<LazadaOrderInfo> findAllByLazadaShopInfoOrderByCreateTime(LazadaShopInfo lazadaShopInfo);

    List<LazadaOrderInfo> findAllByLazadaShopInfoAndOrderStatusOrderByCreateTime(LazadaShopInfo lazadaShopInfo, String orderStatus);

    List<LazadaOrderInfo> findAllByLazadaShopInfoAndOrderStatusAndCreateTimeBetweenOrderByCreateTime(LazadaShopInfo lazadaShopInfo
            , String orderStatus, java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByLazadaShopInfoAndCreateTimeBetweenOrderByCreateTime(LazadaShopInfo lazadaShopInfo
            , java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByLazadaShopInfoEmailAndOrderStatusAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(String email
            , String orderStatus, java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByLazadaShopInfoEmailAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(String email
            , java.sql.Timestamp beginTime, java.sql.Timestamp endTime);

    List<LazadaOrderInfo> findAllByOrderNumberOrderByCreateTimeDesc(long orderNumber);
}
