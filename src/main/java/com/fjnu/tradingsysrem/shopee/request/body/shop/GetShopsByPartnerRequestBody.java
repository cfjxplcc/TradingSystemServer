package com.fjnu.tradingsysrem.shopee.request.body.shop;

/**
 * Created by luochunchen on 2018/12/21.
 */
public class GetShopsByPartnerRequestBody {

    private int partner_id;
    private long timestamp;

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
