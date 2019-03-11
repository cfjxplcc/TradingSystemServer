package com.fjnu.tradingsysrem.shopee.response;

import retrofit2.Response;

import java.io.IOException;

/**
 * Created by luochunchen on 2018/12/28.
 */
public class ShopeeResponse<T extends ShopeeBaseResponse> {

    private final Response<T> response;

    public ShopeeResponse(Response<T> response) {
        this.response = response;
    }

    public boolean isSuccessful() {
        return response.isSuccessful() && response.body() != null && response.body().isSuccessful();
    }

    public T getResponseBody() {
        return response.body();
    }

    public void printError() {
        System.out.println(getErrorMsg());
    }

    public String getErrorMsg() {
        String errorMsg = "";
        if (!response.isSuccessful()) {
            errorMsg = "Error: response ---> code:" + response.code() + " ; errorBody:";
            try {
                errorMsg += (response.errorBody() == null ? "null" : response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (response.body() == null) {
                errorMsg = "Error:response.body in null !!!";
            } else {
                errorMsg = "ResponseClass:" + response.body().getClass().getSimpleName()
                        + " | RequestId:" + response.body().getRequest_id() + " ; Error:" + response.body().getError();
            }
        }
        return errorMsg;
    }
}
