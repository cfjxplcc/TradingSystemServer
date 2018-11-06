package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.PurchaseOrderInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Collection;
import java.util.List;

/**
 * Created by LCC on 2018/3/26.
 */
@RepositoryDefinition(domainClass = PurchaseOrderInfo.class, idClass = String.class)
public interface PurchaseOrderInfoDao {

    PurchaseOrderInfo save(PurchaseOrderInfo purchaseOrderInfo);

    PurchaseOrderInfo findById(String id);

    List<PurchaseOrderInfo> findAll();

    List<PurchaseOrderInfo> findAllByLazadaOrderInfo(LazadaOrderInfo lazadaOrderInfo);

    void saveAndFlush(PurchaseOrderInfo purchaseOrderInfo);

    void deleteById(String id);

    List<PurchaseOrderInfo> findAllByOrderExpressNumberIsNullOrderByLazadaOrderInfoLazadaShopInfoShopNameAscLazadaOrderInfoCreateTimeDesc();

    List<PurchaseOrderInfo> findAllByThirdPartyOrderId(String thirdPartyOrderId);

    List<PurchaseOrderInfo> findAllByOrderExpressNumber(String orderExpressNumber);

    List<PurchaseOrderInfo> findAllByLazadaOrderInfoIn(Collection<LazadaOrderInfo> lazadaOrderInfoList);
}
