package com.fjnu.tradingsysrem.spring.service.shopee.tasks;

import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by luochunchen on 2018/12/25.
 */
@Component
public class ShopeeShopScheduledTask {

    @Autowired
    private ShopeeShopService shopeeShopService;

    @Scheduled(cron = "0 0 12 * * *")//每天中午12点执行
//    @Scheduled(fixedRate = 60 * 60 * 1000)//每间隔1小时执行一次
    @Transactional
    public void asyncAuthorizedShopeeShopInfo() {
        System.out.println("asyncAuthorizedShopeeShopInfo task begin");
        shopeeShopService.synchShopeeShopInfoFromPlatform();
        System.out.println("asyncAuthorizedShopeeShopInfo task end");
    }

}
