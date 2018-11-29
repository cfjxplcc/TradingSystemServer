package com.fjnu.tradingsysrem.shopee.response.orders;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

import java.util.List;

/**
 * Created by LCC on 2018/10/13.
 */
public class GetOrdersResponse extends ShopeeBaseResponse {

    private List<Order> orders;
    private boolean more;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static class Order {
        private String ordersn;
        private String order_status;
        private int update_time;

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}
