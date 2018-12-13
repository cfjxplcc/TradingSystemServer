package com.fjnu.tradingsysrem.spring.model.shopee;

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

    @Column(name = "SHOP_DESCRIPTION", length = 300)
    private String shopDescription;

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

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }
}
