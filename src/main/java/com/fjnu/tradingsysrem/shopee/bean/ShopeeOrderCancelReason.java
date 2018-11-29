package com.fjnu.tradingsysrem.shopee.bean;

/**
 * Created by luochunchen on 2018/11/20.
 */
public enum ShopeeOrderCancelReason {
    OutOfStock("OUT_OF_STOCK"),
    CustomerRequest("CUSTOMER_REQUEST"),
    UndeliverableArea("UNDELIVERABLE_AREA"),
    CodNotSupported("COD_NOT_SUPPORTED");

    private String reason;

    ShopeeOrderCancelReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
