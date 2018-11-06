package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by LCC on 2018/3/17.
 */
@RepositoryDefinition(domainClass = LazadaShopInfo.class, idClass = String.class)
public interface LazadaShopInfoDao {

    LazadaShopInfo save(LazadaShopInfo lazadaShopInfo);

    LazadaShopInfo getById(String id);

    List<LazadaShopInfo> findAllByOrderByShopNameAsc();

    LazadaShopInfo saveAndFlush(LazadaShopInfo lazadaShopInfo);

    void delete(LazadaShopInfo lazadaShopInfo);

    List<LazadaShopInfo> findAllByEmailAndCountryCode(String email, String countryCode);

    LazadaShopInfo findByShortCode(String shortCode);

    List<LazadaShopInfo> findAllByOrderByEmail();
}
