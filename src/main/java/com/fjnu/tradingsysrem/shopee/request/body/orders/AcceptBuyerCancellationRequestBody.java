package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by luochunchen on 2018/11/28.
 */
public class AcceptBuyerCancellationRequestBody extends ShopeeBaseRequestBody {

    private String ordersn;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
}
