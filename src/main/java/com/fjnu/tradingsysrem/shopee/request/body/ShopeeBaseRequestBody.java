package com.fjnu.tradingsysrem.shopee.request.body;

/**
 * Created by LCC on 2018/9/5.
 */
public abstract class ShopeeBaseRequestBody {

    private int partner_id;
    private long timestamp;
    private int shopid;

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

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
}
