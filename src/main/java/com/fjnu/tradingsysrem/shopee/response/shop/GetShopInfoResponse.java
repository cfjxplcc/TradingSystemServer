package com.fjnu.tradingsysrem.shopee.response.shop;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

/**
 * Created by LCC on 2018/9/12.
 */
public class GetShopInfoResponse extends ShopeeBaseResponse {

    private int shop_id;
    private String shop_name;
    private String country;
    private String shop_description;
    private String[] videos;
    private String[] images;
    /** Allow negotiations or not, 1: don't allow, 0: allow. */
    private int disable_make_offer;
    /** Display pickup address or not. */
    private boolean enable_display_unitno;
    /** Listing limitation of items for the shop. */
    private int item_limit;

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShop_description() {
        return shop_description;
    }

    public void setShop_description(String shop_description) {
        this.shop_description = shop_description;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getDisable_make_offer() {
        return disable_make_offer;
    }

    public void setDisable_make_offer(int disable_make_offer) {
        this.disable_make_offer = disable_make_offer;
    }

    public boolean isEnable_display_unitno() {
        return enable_display_unitno;
    }

    public void setEnable_display_unitno(boolean enable_display_unitno) {
        this.enable_display_unitno = enable_display_unitno;
    }

    public int getItem_limit() {
        return item_limit;
    }

    public void setItem_limit(int item_limit) {
        this.item_limit = item_limit;
    }
}
