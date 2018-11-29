package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.bean.ShopeeOrderCancelReason;
import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by luochunchen on 2018/11/20.
 */
public class CancelOrderRequestBody extends ShopeeBaseRequestBody {

    private String ordersn;
    private String cancel_reason;
    /** Required when cancel_reason is OUT_OF_STOCK. */
    private long item_id;
    /** Required when cancel_reason is OUT_OF_STOCK. */
    private long variation_id;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(ShopeeOrderCancelReason shopeeOrderCancelReason) {
        this.cancel_reason = shopeeOrderCancelReason.getReason();
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public long getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(long variation_id) {
        this.variation_id = variation_id;
    }
}
