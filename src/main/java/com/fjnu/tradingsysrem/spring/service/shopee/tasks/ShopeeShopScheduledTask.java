package com.fjnu.tradingsysrem.spring.service.shopee.tasks;

import com.fjnu.tradingsysrem.shopee.ShopeeApiManager;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopsByPartnerResponse;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by luochunchen on 2018/12/25.
 */
@Component
public class ShopeeShopScheduledTask {

    @Autowired
    private ShopeeShopInfoDao shopeeShopInfoDao;
    @Autowired
    private ShopeeShopService shopeeShopService;

    @Scheduled(cron = "0 0 12 * * *")//每天中午12点执行
//    @Scheduled(fixedRate = 60 * 60 * 1000)//每间隔1小时执行一次
    @Transactional
    public void asyncAuthorizedShopeeShopInfo() {
        System.out.println("asyncAuthorizedShopeeShopInfo task begin");
        try {
            Response<GetShopsByPartnerResponse> response = ShopeeApiManager.getInstance().getShopsByPartner();
            if (response.isSuccessful()) {
                if (response.body() != null) {
                    if (response.body().isSuccessful()) {
                        List<GetShopsByPartnerResponse.AuthedShop> authedShopList = response.body().getAuthed_shops();
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
                                String result = shopeeShopService.receiveShopeeAuthorizationShopInfo((int) authedShop.getShopid());
                                System.out.println(result.replace("店铺授权成功", "店铺信息更新成功"));
                            }
                        }

                        // 数据库中存在店铺数据，但是未授权的，更新数据库标识位
                        for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
                            shopeeShopInfo.setAuthorizationFlag(false);
                            shopeeShopInfoDao.saveAndFlush(shopeeShopInfo);
                        }
                    } else {
                        System.out.println("RequestId:" + response.body().getRequest_id() + " ; Error:" + response.body().getError());
                    }
                } else {
                    System.out.println("asyncAuthorizedShopeeShopInfo() Error:response.body in null !!!");
                }
            } else {
                System.out.println("Error: response ---> code:" + response.code() + " ; errorBody:"
                        + (response.errorBody() == null ? "null" : response.errorBody().string()));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        System.out.println("asyncAuthorizedShopeeShopInfo task end");
    }

}
