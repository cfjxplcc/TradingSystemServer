package com.fjnu.tradingsysrem.shopee.request;

import com.fjnu.tradingsysrem.shopee.request.body.shop.GetShopInfoRequestBody;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by LCC on 2018/9/10.
 */
public interface ShopRequest {

    @Headers("Content-Type: application/json")
    @POST("shop/get")
    Call<GetShopInfoResponse> getShopInfo(@Header("Authorization") String authorization, @Body GetShopInfoRequestBody requestBody);
}
