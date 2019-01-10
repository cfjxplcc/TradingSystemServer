package com.fjnu.tradingsysrem.spring.model.shopee;

import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;

import javax.persistence.*;

/**
 * Created by luochunchen on 2018/12/12.
 */
@Entity
@Table(name = "tb_shopee_shop_info")
public class ShopeeShopInfo {

    @Id
    @Column(name = "SHOP_ID")
    private int shopId;

    @Column(name = "SHOP_NAME", length = 50)
    private String shopName;

    @Column(name = "COUNTRY_CODE", length = 50)
    private String countryCode;

    @Column(name = "AUTHORIZATION_FLAG")
    private boolean authorizationFlag;

    public ShopeeShopInfo() {
    }

    public ShopeeShopInfo(GetShopInfoResponse response) {
        shopId = response.getShop_id();
        shopName = response.getShop_name();
        countryCode = response.getCountry();
        authorizationFlag = true;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isAuthorizationFlag() {
        return authorizationFlag;
    }

    public void setAuthorizationFlag(boolean authorizationFlag) {
        this.authorizationFlag = authorizationFlag;
    }
}
