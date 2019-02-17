package com.fjnu.tradingsysrem.spring.service.shopee.imp;

import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeOrderItemsInfoDao;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luochunchen on 2018/12/25.
 */
@Service
@Transactional(readOnly = true)
public class ShopeeOrderItemServiceImp implements ShopeeOrderItemService {

    @Autowired
    private ShopeeOrderItemsInfoDao shopeeOrderItemsInfoDao;
    @Autowired
    private ShopeeOrderInfoDao shopeeOrderInfoDao;

    @Override
    public List<ShopeeOrderItemsInfo> getAllByShopeeOrderInfo(String orderSn) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo != null) {
            return shopeeOrderItemsInfoDao.findAllByShopeeOrderInfo(shopeeOrderInfo);
        } else {
            return new ArrayList<>();
        }
    }
}
