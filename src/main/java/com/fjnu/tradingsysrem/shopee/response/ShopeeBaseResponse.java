package com.fjnu.tradingsysrem.shopee.response;

import com.fjnu.tradingsysrem.spring.utils.TextUtils;

/**
 * Created by LCC on 2018/9/12.
 */
public class ShopeeBaseResponse {

    private String error;
    private String request_id;

    public boolean isSuccessful() {
        return TextUtils.isEmpty(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
