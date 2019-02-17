package com.fjnu.tradingsysrem.spring.dao.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeePurchaseOrderInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/14.
 */
@RepositoryDefinition(domainClass = ShopeePurchaseOrderInfo.class, idClass = String.class)
public interface ShopeePurchaseOrderInfoDao {

    ShopeePurchaseOrderInfo save(ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    void saveAndFlush(ShopeePurchaseOrderInfo shopeePurchaseOrderInfo);

    ShopeePurchaseOrderInfo findById(String id);

    void deleteById(String id);

    List<ShopeePurchaseOrderInfo> findAllByShopeeOrderInfo(ShopeeOrderInfo shopeeOrderInfo);

    List<ShopeePurchaseOrderInfo> findAllByOrderExpressNumber(String orderExpressNumber);

    List<ShopeePurchaseOrderInfo> findAllByOrderExpressNumberIsNull();

    List<ShopeePurchaseOrderInfo> findAllByThirdPartyOrderId(String id);
}
