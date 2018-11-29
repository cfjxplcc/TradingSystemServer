package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

import java.util.List;

/**
 * Created by LCC on 2018/10/13.
 */
public class GetOrderDetailsRequestBody extends ShopeeBaseRequestBody {

    private List<String> ordersn_list;

    public List<String> getOrdersn_list() {
        return ordersn_list;
    }

    public void setOrdersn_list(List<String> ordersn_list) {
        this.ordersn_list = ordersn_list;
    }
}
