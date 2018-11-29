package com.fjnu.tradingsysrem.shopee.response.orders;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

import java.util.List;

/**
 * Created by LCC on 2018/10/13.
 */
public class GetOrderDetailsResponse extends ShopeeBaseResponse {

    private List<Orders> orders;
    private List<String> errors;

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public static class Orders {
        private String ordersn;
        private String country;
        private String currency;
        /** This value indicates whether the orders was a COD (cash on delivery) orders. */
        private boolean cod;
        private String tracking_no;
        private long days_to_ship;
        private RecipientAddress recipient_address;
        private float estimated_shipping_fee;
        private float total_amount;
        private float escrow_amount;
        private String order_status;
        /** The logistics service provider that the buyer selected for the orders to deliver items. */
        private String shipping_carrier;
        private String payment_method;
        private boolean goods_to_declare;
        private String message_to_seller;
        private String note;
        private String note_update_time;
        private long create_time;
        private long update_time;
        private List<Item> items;
        private long pay_time;

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public boolean isCod() {
            return cod;
        }

        public void setCod(boolean cod) {
            this.cod = cod;
        }

        public String getTracking_no() {
            return tracking_no;
        }

        public void setTracking_no(String tracking_no) {
            this.tracking_no = tracking_no;
        }

        public long getDays_to_ship() {
            return days_to_ship;
        }

        public void setDays_to_ship(long days_to_ship) {
            this.days_to_ship = days_to_ship;
        }

        public RecipientAddress getRecipient_address() {
            return recipient_address;
        }

        public void setRecipient_address(RecipientAddress recipient_address) {
            this.recipient_address = recipient_address;
        }

        public float getEstimated_shipping_fee() {
            return estimated_shipping_fee;
        }

        public void setEstimated_shipping_fee(float estimated_shipping_fee) {
            this.estimated_shipping_fee = estimated_shipping_fee;
        }

        public float getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(float total_amount) {
            this.total_amount = total_amount;
        }

        public float getEscrow_amount() {
            return escrow_amount;
        }

        public void setEscrow_amount(float escrow_amount) {
            this.escrow_amount = escrow_amount;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getShipping_carrier() {
            return shipping_carrier;
        }

        public void setShipping_carrier(String shipping_carrier) {
            this.shipping_carrier = shipping_carrier;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public boolean isGoods_to_declare() {
            return goods_to_declare;
        }

        public void setGoods_to_declare(boolean goods_to_declare) {
            this.goods_to_declare = goods_to_declare;
        }

        public String getMessage_to_seller() {
            return message_to_seller;
        }

        public void setMessage_to_seller(String message_to_seller) {
            this.message_to_seller = message_to_seller;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote_update_time() {
            return note_update_time;
        }

        public void setNote_update_time(String note_update_time) {
            this.note_update_time = note_update_time;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }
    }

    public static class RecipientAddress {
        private String name;
        private String phone;
        private String town;
        private String district;
        private String city;
        private String state;
        private String country;
        private String zipcode;
        private String full_address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getFull_address() {
            return full_address;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }
    }

    public static class Item {
        private String item_id;
        private String item_name;
        private String item_sku;
        private long variation_id;
        private String variation_name;
        private String variation_sku;
        private long variation_quantity_purchased;
        private float variation_original_price;
        private float variation_discounted_price;
        private boolean is_wholesale;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_sku() {
            return item_sku;
        }

        public void setItem_sku(String item_sku) {
            this.item_sku = item_sku;
        }

        public long getVariation_id() {
            return variation_id;
        }

        public void setVariation_id(long variation_id) {
            this.variation_id = variation_id;
        }

        public String getVariation_name() {
            return variation_name;
        }

        public void setVariation_name(String variation_name) {
            this.variation_name = variation_name;
        }

        public String getVariation_sku() {
            return variation_sku;
        }

        public void setVariation_sku(String variation_sku) {
            this.variation_sku = variation_sku;
        }

        public long getVariation_quantity_purchased() {
            return variation_quantity_purchased;
        }

        public void setVariation_quantity_purchased(long variation_quantity_purchased) {
            this.variation_quantity_purchased = variation_quantity_purchased;
        }

        public float getVariation_original_price() {
            return variation_original_price;
        }

        public void setVariation_original_price(float variation_original_price) {
            this.variation_original_price = variation_original_price;
        }

        public float getVariation_discounted_price() {
            return variation_discounted_price;
        }

        public void setVariation_discounted_price(float variation_discounted_price) {
            this.variation_discounted_price = variation_discounted_price;
        }

        public boolean isIs_wholesale() {
            return is_wholesale;
        }

        public void setIs_wholesale(boolean is_wholesale) {
            this.is_wholesale = is_wholesale;
        }
    }

}
