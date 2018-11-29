package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by LCC on 2018/10/13.
 * <p>
 * note: The maximum date range that may be specified with the create_time_from and create_time_to fields is 15 days
 * </p>
 */
public class GetOrderListByCreateTimeRequestBody extends ShopeeBaseRequestBody {

    private long create_time_from;
    private long create_time_to;

    public long getCreate_time_from() {
        return create_time_from;
    }

    public void setCreate_time_from(long create_time_from) {
        this.create_time_from = create_time_from;
    }

    public long getCreate_time_to() {
        return create_time_to;
    }

    public void setCreate_time_to(long create_time_to) {
        this.create_time_to = create_time_to;
    }
}
