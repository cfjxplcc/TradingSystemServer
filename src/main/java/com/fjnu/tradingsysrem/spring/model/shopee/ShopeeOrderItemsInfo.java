package com.fjnu.tradingsysrem.spring.model.shopee;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by luochunchen on 2018/12/13.
 */
@Entity
@Table(name = "tb_shopee_order_items_info")
public class ShopeeOrderItemsInfo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "ORDER_SN")
    private ShopeeOrderInfo shopeeOrderInfo;

    @Column(name = "ITEM_ID")
    private long itemId;

    @Column(name = "ITEM_NAME", length = 300)
    private String itemName;

    @Column(name = "ITEM_SKU", length = 100)
    private String itemSku;

    @Column(name = "VARIATION_ID")
    private long variationId;

    @Column(name = "VARIATION_NAME", length = 300)
    private String variationName;

    @Column(name = "VARIATION_SKU", length = 100)
    private String variationSku;

    @Column(name = "VARIATION_QUANTITY_PURCHASED")
    private int variationQuantityPurchased;

    @Column(name = "VARIATION_ORIGINAL_PRICE")
    private float variationOriginalPrice;

    @Column(name = "VARIATION_DISCOUNTED_PRICE")
    private float variationDiscountedPrice;

    @Column(name = "IS_WHOLESALE")
    private boolean isWholesale;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShopeeOrderInfo getShopeeOrderInfo() {
        return shopeeOrderInfo;
    }

    public void setShopeeOrderInfo(ShopeeOrderInfo shopeeOrderInfo) {
        this.shopeeOrderInfo = shopeeOrderInfo;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public long getVariationId() {
        return variationId;
    }

    public void setVariationId(long variationId) {
        this.variationId = variationId;
    }

    public String getVariationName() {
        return variationName;
    }

    public void setVariationName(String variationName) {
        this.variationName = variationName;
    }

    public String getVariationSku() {
        return variationSku;
    }

    public void setVariationSku(String variationSku) {
        this.variationSku = variationSku;
    }

    public int getVariationQuantityPurchased() {
        return variationQuantityPurchased;
    }

    public void setVariationQuantityPurchased(int variationQuantityPurchased) {
        this.variationQuantityPurchased = variationQuantityPurchased;
    }

    public float getVariationOriginalPrice() {
        return variationOriginalPrice;
    }

    public void setVariationOriginalPrice(float variationOriginalPrice) {
        this.variationOriginalPrice = variationOriginalPrice;
    }

    public float getVariationDiscountedPrice() {
        return variationDiscountedPrice;
    }

    public void setVariationDiscountedPrice(float variationDiscountedPrice) {
        this.variationDiscountedPrice = variationDiscountedPrice;
    }

    public boolean isWholesale() {
        return isWholesale;
    }

    public void setWholesale(boolean wholesale) {
        isWholesale = wholesale;
    }
}
