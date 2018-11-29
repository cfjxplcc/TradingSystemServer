package com.fjnu.tradingsysrem.shopee.response.logistics;

import com.fjnu.tradingsysrem.shopee.response.ShopeeBaseResponse;

import java.util.List;

/**
 * Created by luochunchen on 2018/11/28.
 */
public class GetLogisticInfoResponse extends ShopeeBaseResponse {

    private Pickup pickup;
    private DropOff dropoff;
    private InfoNeeded info_needed;

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

    public InfoNeeded getInfo_needed() {
        return info_needed;
    }

    public void setInfo_needed(InfoNeeded info_needed) {
        this.info_needed = info_needed;
    }

    public static class Pickup {
        private List<AddressInfo> address_list;

        public List<AddressInfo> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<AddressInfo> address_list) {
            this.address_list = address_list;
        }

        public static class AddressInfo {
            private long address_id;
            private String country;
            private String state;
            private String city;
            private String address;
            private String zipcode;
            private String district;
            private String town;
            private List<PickupTime> time_slot_list;

            public long getAddress_id() {
                return address_id;
            }

            public void setAddress_id(long address_id) {
                this.address_id = address_id;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }

            public List<PickupTime> getTime_slot_list() {
                return time_slot_list;
            }

            public void setTime_slot_list(List<PickupTime> time_slot_list) {
                this.time_slot_list = time_slot_list;
            }

            public static class PickupTime {
                private long pickup_time_id;
                private long date;
                private String time_text;

                public long getPickup_time_id() {
                    return pickup_time_id;
                }

                public void setPickup_time_id(long pickup_time_id) {
                    this.pickup_time_id = pickup_time_id;
                }

                public long getDate() {
                    return date;
                }

                public void setDate(long date) {
                    this.date = date;
                }

                public String getTime_text() {
                    return time_text;
                }

                public void setTime_text(String time_text) {
                    this.time_text = time_text;
                }
            }
        }
    }

    public static class DropOff {
        private List<BranchesInfo> branch_list;

        public List<BranchesInfo> getBranch_list() {
            return branch_list;
        }

        public void setBranch_list(List<BranchesInfo> branch_list) {
            this.branch_list = branch_list;
        }

        public static class BranchesInfo {
            private long branch_id;
            private String country;
            private String state;
            private String city;
            private String address;
            private String zipcode;
            private String district;
            private String town;

            public long getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(long branch_id) {
                this.branch_id = branch_id;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getTown() {
                return town;
            }

            public void setTown(String town) {
                this.town = town;
            }
        }
    }

    public static class InfoNeeded {
        private List<String> pickup;
        private List<String> dropoff;
        private List<String> non_integrated;

        public List<String> getPickup() {
            return pickup;
        }

        public void setPickup(List<String> pickup) {
            this.pickup = pickup;
        }

        public List<String> getDropoff() {
            return dropoff;
        }

        public void setDropoff(List<String> dropoff) {
            this.dropoff = dropoff;
        }

        public List<String> getNon_integrated() {
            return non_integrated;
        }

        public void setNon_integrated(List<String> non_integrated) {
            this.non_integrated = non_integrated;
        }
    }

}
