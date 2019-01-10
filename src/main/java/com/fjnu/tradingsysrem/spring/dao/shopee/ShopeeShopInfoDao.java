package com.fjnu.tradingsysrem.spring.dao.shopee;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/14.
 */
@RepositoryDefinition(domainClass = ShopeeShopInfo.class, idClass = Integer.class)
public interface ShopeeShopInfoDao {

    ShopeeShopInfo save(ShopeeShopInfo shopeeShopInfo);

    void saveAndFlush(ShopeeShopInfo shopeeShopInfo);

    ShopeeShopInfo findByShopId(int shopId);

    void delete(ShopeeShopInfo shopeeShopInfo);

    List<ShopeeShopInfo> findAllByOrderByShopName();

    List<ShopeeShopInfo> findAll();

}
