package com.fjnu.tradingsysrem.shopee.response.orders;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

/**
 * Created by luochunchen on 2018/11/20.
 */
public class AcceptBuyerCancellationResponse extends ShopeeBaseResponse {

    private long modified_time;

    public long getModified_time() {
        return modified_time;
    }

    public void setModified_time(long modified_time) {
        this.modified_time = modified_time;
    }
}
