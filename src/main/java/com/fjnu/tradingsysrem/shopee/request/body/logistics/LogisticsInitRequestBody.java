package com.fjnu.tradingsysrem.shopee.request.body.logistics;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by luochunchen on 2018/11/29.
 */
public class LogisticsInitRequestBody extends ShopeeBaseRequestBody {

    private String ordersn;
    /**
     * Required parameter ONLY if GetParameterForInit returns "pickup" or if GetLogisticsInfo returns "pickup" under "info_needed" for the same order.
     * Developer should still include "pickup" field in the call even if "pickup" has empty value.
     */
    private Pickup pickup;
    /**
     * Required parameter ONLY if GetParameterForInit returns "dropoff" or if GetLogisticsInfo returns "dropoff" under "info_needed" for the same order.
     * Developer should still include "dropoff" field in the call even if "dropoff" has empty value.
     */
    private DropOff dropoff;
    /** Optional parameter when GetParameterForInit returns "non-integrated" or GetLogisticsInfo returns "non-integrated" under "info_needed". */
    private NonIntegrated non_integrated;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;
    }

    public DropOff getDropoff() {
        return dropoff;
    }

    public void setDropoff(DropOff dropoff) {
        this.dropoff = dropoff;
    }

    public NonIntegrated getNon_integrated() {
        return non_integrated;
    }

    public void setNon_integrated(NonIntegrated non_integrated) {
        this.non_integrated = non_integrated;
    }

    public static class Pickup {
        private long address_id;
        private String pickup_time_id;
        private String tracking_no;

        public long getAddress_id() {
            return address_id;
        }

        public void setAddress_id(long address_id) {
            this.address_id = address_id;
        }

        public String getPickup_time_id() {
            return pickup_time_id;
        }

        public void setPickup_time_id(String pickup_time_id) {
            this.pickup_time_id = pickup_time_id;
        }

        public String getTracking_no() {
            return tracking_no;
        }

        public void setTracking_no(String tracking_no) {
            this.tracking_no = tracking_no;
        }
    }

    public static class DropOff {
        private long branch_id;
        private String sender_real_name;
        private String tracking_no;

        public long getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(long branch_id) {
            this.branch_id = branch_id;
        }

        public String getSender_real_name() {
            return sender_real_name;
        }

        public void setSender_real_name(String sender_real_name) {
            this.sender_real_name = sender_real_name;
        }

        public String getTracking_no() {
            return tracking_no;
        }

        public void setTracking_no(String tracking_no) {
            this.tracking_no = tracking_no;
        }
    }

    public static class NonIntegrated {
        private String tracking_no;

        public String getTracking_no() {
            return tracking_no;
        }

        public void setTracking_no(String tracking_no) {
            this.tracking_no = tracking_no;
        }
    }

}
