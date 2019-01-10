package com.fjnu.tradingsysrem.spring.dao.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderItemsInfo;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Created by luochunchen on 2018/12/14.
 */
@RepositoryDefinition(domainClass = ShopeeOrderItemsInfo.class, idClass = String.class)
public interface ShopeeOrderItemsInfoDao {

    ShopeeOrderItemsInfo save(ShopeeOrderItemsInfo shopeeOrderItemsInfo);

    void saveAndFlush(ShopeeOrderItemsInfo shopeeOrderItemsInfo);

    ShopeeOrderItemsInfo findById(String id);

    void deleteById(String id);
}
