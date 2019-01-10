package com.fjnu.tradingsysrem.shopee.response.shop;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

import java.util.List;

/**
 * Created by luochunchen on 2018/12/21.
 */
public class GetShopsByPartnerResponse extends ShopeeBaseResponse {

    private List<AuthedShop> authed_shops;

    public List<AuthedShop> getAuthed_shops() {
        return authed_shops;
    }

    public void setAuthed_shops(List<AuthedShop> authed_shops) {
        this.authed_shops = authed_shops;
    }

    public static class AuthedShop {
        private String country;
        private long shopid;
        private long auth_time;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getShopid() {
            return shopid;
        }

        public void setShopid(long shopid) {
            this.shopid = shopid;
        }

        public long getAuth_time() {
            return auth_time;
        }

        public void setAuth_time(long auth_time) {
            this.auth_time = auth_time;
        }
    }
}
