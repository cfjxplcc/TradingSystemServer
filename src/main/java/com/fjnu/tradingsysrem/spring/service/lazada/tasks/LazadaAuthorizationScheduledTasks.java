package com.fjnu.tradingsysrem.spring.service.lazada.tasks;

import com.fjnu.tradingsysrem.spring.service.lazada.LazadaAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by LCC on 2018/6/11.
 */
@Component
public class LazadaAuthorizationScheduledTasks {

    @Autowired
    private LazadaAuthorizationService lazadaAuthorizationService;

    @Scheduled(cron = "0 30 * * * *")//每小时30分执行一次
    public void refreshAccessToken() {
        lazadaAuthorizationService.refreshAccessToken();
    }


}
