package com.fjnu.tradingsysrem.shopee.request.body.shop;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by LCC on 2018/9/10.
 */
public class GetShopInfoRequestBody extends ShopeeBaseRequestBody {

    private String ordersn;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
}
