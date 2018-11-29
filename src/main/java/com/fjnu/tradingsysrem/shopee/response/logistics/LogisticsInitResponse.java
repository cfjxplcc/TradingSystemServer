package com.fjnu.tradingsysrem.shopee.response.logistics;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

/**
 * Created by luochunchen on 2018/11/29.
 */
public class LogisticsInitResponse extends ShopeeBaseResponse {
    private String tracking_number;

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }
}
