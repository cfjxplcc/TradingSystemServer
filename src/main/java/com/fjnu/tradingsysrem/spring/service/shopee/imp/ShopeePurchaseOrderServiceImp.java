package com.fjnu.tradingsysrem.spring.service.shopee.imp;

import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeePurchaseOrderInfoDao;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeePurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeePurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/25.
 */
@Service
@Transactional(readOnly = true)
public class ShopeePurchaseOrderServiceImp implements ShopeePurchaseOrderService {

    @Autowired
    private ShopeePurchaseOrderInfoDao shopeePurchaseOrderInfoDao;
    @Autowired
    private ShopeeOrderInfoDao shopeeOrderInfoDao;

    @Override
    @Transactional
    public String save(ShopeePurchaseOrderInfo shopeePurchaseOrderInfo) {
        return shopeePurchaseOrderInfoDao.save(shopeePurchaseOrderInfo).getId();
    }

    @Override
    public ShopeePurchaseOrderInfo get(String id) {
        return shopeePurchaseOrderInfoDao.findById(id);
    }

    @Override
    public List<ShopeePurchaseOrderInfo> getByShopeeOrderInfo(String shopeeOrderSn) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(shopeeOrderSn);
        return shopeePurchaseOrderInfoDao.findAllByShopeeOrderInfo(shopeeOrderInfo);
    }

    @Override
    @Transactional
    public void update(String id, ShopeePurchaseOrderInfo shopeePurchaseOrderInfo) {
        shopeePurchaseOrderInfo.setId(id);
        shopeePurchaseOrderInfoDao.saveAndFlush(shopeePurchaseOrderInfo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        shopeePurchaseOrderInfoDao.deleteById(id);
    }
}
