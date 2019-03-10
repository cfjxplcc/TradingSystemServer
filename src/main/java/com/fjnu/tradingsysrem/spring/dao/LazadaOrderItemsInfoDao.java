package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by LCC on 2018/3/20.
 */
@RepositoryDefinition(domainClass = LazadaOrderItemsInfo.class, idClass = String.class)
public interface LazadaOrderItemsInfoDao {

    LazadaOrderItemsInfo save(LazadaOrderItemsInfo lazadaOrderItemsInfo);

    List<LazadaOrderItemsInfo> findAll();

    List<LazadaOrderItemsInfo> findAllByLazadaOrderInfo(LazadaOrderInfo lazadaOrderInfo);

    List<LazadaOrderItemsInfo> findAllBySkuAndLazadaOrderInfoDeliveryIsFalseOrderByCreatedTimeDesc(String sku);

    LazadaOrderItemsInfo findById(String id);

    LazadaOrderItemsInfo findByLazadaOrderItemId(Long lazadaOrderItemId);

    void saveAndFlush(LazadaOrderItemsInfo lazadaOrderItemsInfo);

    void deleteById(String id);

    @Modifying
    @Query("delete from LazadaOrderItemsInfo i where i.lazadaOrderInfo.id = ?1")
    void deleteByLazadaOrderInfo(String id);
}
