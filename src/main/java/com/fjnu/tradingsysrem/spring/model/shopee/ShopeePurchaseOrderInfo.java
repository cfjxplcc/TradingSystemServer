package com.fjnu.tradingsysrem.spring.model.shopee;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by luochunchen on 2018/12/13.
 */
@Entity
@Table(name = "tb_shopee_purchase_info")
public class ShopeePurchaseOrderInfo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "ORDER_SN")
    private ShopeeOrderInfo shopeeOrderInfo;

    @ManyToOne
    @JoinColumn(name = "ORDER_ITEM_ID")
    private ShopeeOrderItemsInfo shopeeOrderItemsInfo;

    @Column(name = "THIRD_PARTY_ORDER_ID")
    private String thirdPartyOrderId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TOTAL_PRICE")
    private Float totalPrice;

    @Column(name = "ITEM_TOTAL_NUMBER")
    private int itemTotalNumber;

    @Column(name = "ORDER_URL")
    private String orderUrl;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "DATE")
    private java.sql.Timestamp date;

    @Column(name = "ORDER_EXPRESS_NUMBER")
    private String orderExpressNumber;

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

    public ShopeeOrderItemsInfo getShopeeOrderItemsInfo() {
        return shopeeOrderItemsInfo;
    }

    public void setShopeeOrderItemsInfo(ShopeeOrderItemsInfo shopeeOrderItemsInfo) {
        this.shopeeOrderItemsInfo = shopeeOrderItemsInfo;
    }

    public String getThirdPartyOrderId() {
        return thirdPartyOrderId;
    }

    public void setThirdPartyOrderId(String thirdPartyOrderId) {
        this.thirdPartyOrderId = thirdPartyOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemTotalNumber() {
        return itemTotalNumber;
    }

    public void setItemTotalNumber(int itemTotalNumber) {
        this.itemTotalNumber = itemTotalNumber;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getOrderExpressNumber() {
        return orderExpressNumber;
    }

    public void setOrderExpressNumber(String orderExpressNumber) {
        this.orderExpressNumber = orderExpressNumber;
    }
}
