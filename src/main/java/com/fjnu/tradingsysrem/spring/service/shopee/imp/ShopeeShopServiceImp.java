package com.fjnu.tradingsysrem.spring.service.shopee.imp;

import com.fjnu.tradingsysrem.shopee.ShopeeApiManager;
import com.fjnu.tradingsysrem.shopee.response.ShopeeResponse;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;


/**
 * Created by luochunchen on 2018/12/17.
 */
@Service
@Transactional(readOnly = true)
public class ShopeeShopServiceImp implements ShopeeShopService {

    @Autowired
    private ShopeeShopInfoDao shopeeShopInfoDao;

    @Override
    public List<ShopeeShopInfo> getShopeeShopInfoList() {
        return shopeeShopInfoDao.findAll();
    }

    @Override
    @Transactional
    public String receiveShopeeAuthorizationShopInfo(int shopId) {
        try {
            Response<GetShopInfoResponse> response = ShopeeApiManager.getInstance().getShopInfo(shopId);
            ShopeeResponse<GetShopInfoResponse> shopeeResponse = new ShopeeResponse<>(response);
            if (shopeeResponse.isSuccessful()) {
                ShopeeShopInfo shopeeShopInfo = new ShopeeShopInfo(shopeeResponse.getResponseBody());
                shopeeShopInfoDao.saveAndFlush(shopeeShopInfo);
                return shopId + "店铺授权成功";
            } else {
                return shopeeResponse.getErrorMsg();
            }
        } catch (IOException e) {
            return "Error:" + e.toString();
        }
    }

    @Override
    public void synchShopeeShopInfoFromPlatform() {

    }
}
