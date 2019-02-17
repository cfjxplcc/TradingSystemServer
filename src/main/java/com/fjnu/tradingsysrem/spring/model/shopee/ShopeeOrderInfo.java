package com.fjnu.tradingsysrem.spring.model.shopee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrderDetailsResponse;
import com.fjnu.tradingsysrem.spring.model.ExchangeRate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by luochunchen on 2018/12/12.
 */
@Entity
@Table(name = "tb_shopee_order_info")
public class ShopeeOrderInfo {

    @Id
    @Column(name = "ORDER_SN", length = 20)
    private String orderSn;

    @ManyToOne
    @JoinColumn(name = "SHOP_ID", nullable = false)
    private ShopeeShopInfo shopeeShopInfo;

    @OneToMany(mappedBy = "shopeeOrderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShopeeOrderItemsInfo> shopeeOrderItemsInfoSet = new HashSet<>();

    @OneToMany(mappedBy = "shopeeOrderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShopeePurchaseOrderInfo> shopeePurchaseOrderInfoSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "EXCHANGE_RATE_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExchangeRate exchangeRate;

    @Column(name = "CASH_ON_DELIVERY")
    private boolean cashOnDelivery;

    @Column(name = "TRACKING_NO", length = 64)
    private String trackingNo;

    @Column(name = "DATE_TO_SHIP")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp dateToShip;

    @Column(name = "RECIPIENT_NAME", length = 100)
    private String recipientName;

    @Column(name = "RECIPIENT_PHONE", length = 50)
    private String recipientPhone;

    @Column(name = "RECIPIENT_COUNTRY", length = 2)
    private String recipientCountry;

    @Column(name = "RECIPIENT_ZIP_CODE", length = 50)
    private String recipientZipCode;

    @Column(name = "RECIPIENT_FULL_ADDRESS")
    private String recipientFullAddress;

    @Column(name = "ESTIMATED_SHIPPING_FEE")
    private float estimatedShippingFee;

    @Column(name = "ACTUAL_SHIPPING_COST")
    private float actualShippingCost;

    @Column(name = "TOTAL_AMOUNT")
    private float totalAmount;

    @Column(name = "ESCROW_AMOUNT")
    private float escrowAmount;

    @Column(name = "ORDER_STATUS", length = 50)
    private String orderStatus;

    @Column(name = "SHIPPING_CARRIER", length = 50)
    private String shippingCarrier;

    @Column(name = "PAYMENT_METHOD", length = 50)
    private String paymentMethod;

    @Column(name = "MESSAGE_TO_SELLER", length = 300)
    private String messageToSeller;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "NOTE_UPDATE_TIME")
    private String noteUpdateTime;

    @Column(name = "CREATE_TIME")
    private java.sql.Timestamp createTime;

    @Column(name = "UPDATE_TIME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp updateTime;

    @Column(name = "PAY_TIME")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.sql.Timestamp payTime;

    @Column(name = "OVERSEAS_EXPRESS_PRICE")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private float overseasExpressPrice;

    @Column(name = "IS_DELIVERY")
    private boolean delivery;

    @Column(name = "REMARKS")
    private String remarks;

    public ShopeeOrderInfo() {
    }

    public ShopeeOrderInfo(ShopeeShopInfo shopeeShopInfo, ExchangeRate exchangeRate, GetOrderDetailsResponse.Orders orders) {
        orderSn = orders.getOrdersn();
        this.shopeeShopInfo = shopeeShopInfo;
        this.exchangeRate = exchangeRate;
        cashOnDelivery = orders.isCod();
        trackingNo = orders.getTracking_no();
        long tempDateToShip = (orders.getCreate_time() + orders.getDays_to_ship() * 24 * 60 * 60) * 1000;
        dateToShip = new Timestamp(tempDateToShip);
        GetOrderDetailsResponse.RecipientAddress recipientAddress = orders.getRecipient_address();
        recipientName = recipientAddress.getName();
        recipientPhone = recipientAddress.getPhone();
        recipientCountry = recipientAddress.getCountry();
        recipientZipCode = recipientAddress.getZipcode();
        recipientFullAddress = recipientAddress.getFull_address();
        estimatedShippingFee = orders.getEstimated_shipping_fee();
        actualShippingCost = orders.getActual_shipping_cost();
        totalAmount = orders.getTotal_amount();
        escrowAmount = orders.getEscrow_amount();
        orderStatus = orders.getOrder_status();
        shippingCarrier = orders.getShipping_carrier();
        paymentMethod = orders.getPayment_method();
        messageToSeller = orders.getMessage_to_seller();
        note = orders.getNote();
        noteUpdateTime = orders.getNote_update_time();
        createTime = new Timestamp(orders.getCreate_time() * 1000);
        if (orders.getUpdate_time() > 0) {
            updateTime = new Timestamp(orders.getUpdate_time() * 1000);
        }
        if (orders.getPay_time() > 0) {
            payTime = new Timestamp(orders.getPay_time() * 1000);
        }
        delivery = false;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String ordrSn) {
        this.orderSn = ordrSn;
    }

    public ShopeeShopInfo getShopeeShopInfo() {
        return shopeeShopInfo;
    }

    public void setShopeeShopInfo(ShopeeShopInfo shopeeShopInfo) {
        this.shopeeShopInfo = shopeeShopInfo;
    }

    @JsonIgnore
    public Set<ShopeeOrderItemsInfo> getShopeeOrderItemsInfoSet() {
        return shopeeOrderItemsInfoSet;
    }

    public void setShopeeOrderItemsInfoSet(Set<ShopeeOrderItemsInfo> shopeeOrderItemsInfoSet) {
        this.shopeeOrderItemsInfoSet = shopeeOrderItemsInfoSet;
    }

    @JsonIgnore
    public Set<ShopeePurchaseOrderInfo> getShopeePurchaseOrderInfoSet() {
        return shopeePurchaseOrderInfoSet;
    }

    public void setShopeePurchaseOrderInfoSet(Set<ShopeePurchaseOrderInfo> shopeePurchaseOrderInfoSet) {
        this.shopeePurchaseOrderInfoSet = shopeePurchaseOrderInfoSet;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public boolean isCashOnDelivery() {
        return cashOnDelivery;
    }

    public void setCashOnDelivery(boolean cashOnDelivery) {
        this.cashOnDelivery = cashOnDelivery;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Timestamp getDateToShip() {
        return dateToShip;
    }

    public void setDateToShip(Timestamp dateToShip) {
        this.dateToShip = dateToShip;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientCountry() {
        return recipientCountry;
    }

    public void setRecipientCountry(String recipientCountry) {
        this.recipientCountry = recipientCountry;
    }

    public String getRecipientZipCode() {
        return recipientZipCode;
    }

    public void setRecipientZipCode(String recipientZipCode) {
        this.recipientZipCode = recipientZipCode;
    }

    public String getRecipientFullAddress() {
        return recipientFullAddress;
    }

    public void setRecipientFullAddress(String recipientFullAddress) {
        this.recipientFullAddress = recipientFullAddress;
    }

    public float getEstimatedShippingFee() {
        return estimatedShippingFee;
    }

    public void setEstimatedShippingFee(float estimatedShippingFee) {
        this.estimatedShippingFee = estimatedShippingFee;
    }

    public float getActualShippingCost() {
        return actualShippingCost;
    }

    public void setActualShippingCost(float actualShippingCost) {
        this.actualShippingCost = actualShippingCost;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getEscrowAmount() {
        return escrowAmount;
    }

    public void setEscrowAmount(float escrowAmount) {
        this.escrowAmount = escrowAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingCarrier() {
        return shippingCarrier;
    }

    public void setShippingCarrier(String shippingCarrier) {
        this.shippingCarrier = shippingCarrier;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMessageToSeller() {
        return messageToSeller;
    }

    public void setMessageToSeller(String messageToSeller) {
        this.messageToSeller = messageToSeller;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteUpdateTime() {
        return noteUpdateTime;
    }

    public void setNoteUpdateTime(String noteUpdateTime) {
        this.noteUpdateTime = noteUpdateTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public float getOverseasExpressPrice() {
        return overseasExpressPrice;
    }

    public void setOverseasExpressPrice(float overseasExpressPrice) {
        this.overseasExpressPrice = overseasExpressPrice;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
