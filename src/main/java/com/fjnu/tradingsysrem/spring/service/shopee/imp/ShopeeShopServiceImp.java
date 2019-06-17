package com.fjnu.tradingsysrem.spring.service.shopee.imp;

import com.fjnu.tradingsysrem.shopee.ShopeeApiManager;
import com.fjnu.tradingsysrem.shopee.response.ShopeeResponse;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopsByPartnerResponse;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * Created by luochunchen on 2018/12/17.
 */
@Service
@Transactional(readOnly = true)
public class ShopeeShopServiceImp implements ShopeeShopService {
    private static Logger logger = LoggerFactory.getLogger(ShopeeShopServiceImp.class);

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
                return "店铺(" + shopId + "|" + shopeeShopInfo.getShopName() + ")授权成功";
            } else {
                return shopeeResponse.getErrorMsg();
            }
        } catch (IOException e) {
            return "Error:" + e.toString();
        }
    }

    @Override
    @Transactional
    public String synchShopeeShopInfoFromPlatform() {
        try {
            Response<GetShopsByPartnerResponse> response = ShopeeApiManager.getInstance().getShopsByPartner();
            ShopeeResponse<GetShopsByPartnerResponse> shopeeResponse = new ShopeeResponse<>(response);
            if (shopeeResponse.isSuccessful()) {
                List<GetShopsByPartnerResponse.AuthedShop> authedShopList = shopeeResponse.getResponseBody().getAuthed_shops();
                Iterator<GetShopsByPartnerResponse.AuthedShop> authedShopsIterator = authedShopList.iterator();
                List<ShopeeShopInfo> shopeeShopInfoList = shopeeShopInfoDao.findAll();

                // 比对数据库中已有的店铺信息
                boolean isExist = false;
                while (authedShopsIterator.hasNext()) {
                    GetShopsByPartnerResponse.AuthedShop authedShop = authedShopsIterator.next();
                    for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
                        // shopId相同
                        if (shopeeShopInfo.getShopId() == authedShop.getShopid()) {
                            if (!shopeeShopInfo.isAuthorizationFlag()) {
                                // 修改授权状态
                                shopeeShopInfo.setAuthorizationFlag(true);
                                shopeeShopInfoDao.saveAndFlush(shopeeShopInfo);
                            }
                            isExist = true;
                            authedShopsIterator.remove();
                            shopeeShopInfoList.remove(shopeeShopInfo);
                            break;
                        }
                    }
                    // 数据库中不存在则获取数据
                    if (!isExist) {
                        String result = receiveShopeeAuthorizationShopInfo((int) authedShop.getShopid());
                        logger.info(result.replace("店铺授权成功", "店铺(" + authedShop.getShopid() + ")信息添加成功"));
                    }
                }

                // 数据库中存在店铺数据，但是未授权的，更新数据库标识位
                for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
                    shopeeShopInfo.setAuthorizationFlag(false);
                    shopeeShopInfoDao.saveAndFlush(shopeeShopInfo);
                }
            } else {
                return shopeeResponse.getErrorMsg();
            }
        } catch (IOException e) {
            return "Error" + e.toString();
        }
        return "店铺数据同步完成";
    }

    @Override
    @Transactional
    public String receiveShopeeCancelAuthorizationShopInfo(int shopId) {
        ShopeeShopInfo shopeeShopInfo = shopeeShopInfoDao.findByShopId(shopId);
        if (shopeeShopInfo != null) {
            if (shopeeShopInfo.isAuthorizationFlag()) {
                shopeeShopInfo.setAuthorizationFlag(false);
                shopeeShopInfoDao.saveAndFlush(shopeeShopInfo);
            }
            return "店铺(" + shopId + "|" + shopeeShopInfo.getShopName() + ")已经取消授权";
        } else {
            return "本地系统不存在该店铺";
        }
    }
}
