package com.fjnu.tradingsysrem.shopee.request.body.orders;

import com.fjnu.tradingsysrem.shopee.request.body.ShopeeBaseRequestBody;

/**
 * Created by LCC on 2018/10/13.
 * <p>
 * note: The maximum date range that may be specified with the create_time_from and create_time_to fields is 15 days
 * </p>
 */
public class GetOrderListByCreateTimeRequestBody extends ShopeeBaseRequestBody {

    private long create_time_from;
    private long create_time_to;
    private int pagination_entries_per_page = 100;
    private int pagination_offset = 0;

    public long getCreate_time_from() {
        return create_time_from;
    }

    public void setCreate_time_from(long create_time_from) {
        this.create_time_from = create_time_from;
    }

    public long getCreate_time_to() {
        return create_time_to;
    }

    public void setCreate_time_to(long create_time_to) {
        this.create_time_to = create_time_to;
    }

    public int getPagination_entries_per_page() {
        return pagination_entries_per_page;
    }

    public void setPagination_entries_per_page(int pagination_entries_per_page) {
        this.pagination_entries_per_page = pagination_entries_per_page;
    }

    public int getPagination_offset() {
        return pagination_offset;
    }

    public void setPagination_offset(int pagination_offset) {
        this.pagination_offset = pagination_offset;
    }
}
