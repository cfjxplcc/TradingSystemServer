package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by LCC on 2018/10/13.
 * <p>
 * note:The maximum date range that may be specified with the update_time_from and update_time_to fields is 15 days
 * </p>
 */
public class GetOrderListByUpdateTimeRequestBody extends ShopeeBaseRequestBody {

    private long update_time_from;
    private long update_time_to;

    public long getUpdate_time_from() {
        return update_time_from;
    }

    public void setUpdate_time_from(long update_time_from) {
        this.update_time_from = update_time_from;
    }

    public long getUpdate_time_to() {
        return update_time_to;
    }

    public void setUpdate_time_to(long update_time_to) {
        this.update_time_to = update_time_to;
    }
}
