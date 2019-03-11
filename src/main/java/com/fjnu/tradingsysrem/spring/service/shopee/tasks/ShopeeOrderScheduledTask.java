package com.fjnu.tradingsysrem.spring.service.shopee.tasks;

import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderService;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * Created by luochunchen on 2018/12/18.
 */
@Component
public class ShopeeOrderScheduledTask {

    @Autowired
    private ShopeeOrderService shopeeOrderService;
    @Autowired
    private ShopeeShopService shopeeShopService;

    /**
     * 获取过去6小时内的新订单
     */
    @Scheduled(cron = "0 0 0/6 * * *")//每6小时执行一次
//    @Scheduled(fixedRate = 60 * 60 * 1000)//每间隔1小时执行一次
    public void asyncNewShopeeOrderInfo() {
        System.out.println("-------------> asyncNewShopeeOrderInfo task begin <-------------");
        long methodBeginTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 6);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);

        long beginTime = calendar.getTimeInMillis();
        long endTime = System.currentTimeMillis();

        List<ShopeeShopInfo> shopeeShopInfoList = shopeeShopService.getShopeeShopInfoList();
        for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
            if (shopeeShopInfo.isAuthorizationFlag()) {
                shopeeOrderService.synchShopeeOrderInfoFromPlatformByCreateTime(shopeeShopInfo, beginTime, endTime);
            }
        }

        long methodEndTime = System.currentTimeMillis();
        System.out.println("asyncNewShopeeOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }
}
