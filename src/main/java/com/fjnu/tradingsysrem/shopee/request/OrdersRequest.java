package com.fjnu.tradingsysrem.shopee.request;

import com.fjnu.tradingsysrem.shopee.request.body.orders.*;
import com.fjnu.tradingsysrem.shopee.response.orders.AcceptBuyerCancellationResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrderDetailsResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrdersResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.RejectBuyerCancellationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by LCC on 2018/10/13.
 */
public interface OrdersRequest {

    @Headers("Content-Type: application/json")
    @POST("orders/basics")
    Call<GetOrdersResponse> getOrderListByCreateTime(@Header("Authorization") String authorization, @Body GetOrderListByCreateTimeRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/basics")
    Call<GetOrdersResponse> getOrderListByUpdateTime(@Header("Authorization") String authorization, @Body GetOrderListByCreateTimeRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/get")
    Call<GetOrdersResponse> getOrderListByStatus(@Header("Authorization") String authorization, @Body GetOrderListByCreateTimeRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/detail")
    Call<GetOrderDetailsResponse> getOrderDetails(@Header("Authorization") String authorization, @Body GetOrderDetailsRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/cancel")
    Call<GetOrderDetailsResponse> cancelOrder(@Header("Authorization") String authorization, @Body CancelOrderRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/buyer_cancellation/accept")
    Call<AcceptBuyerCancellationResponse> acceptBuyerCancellation(@Header("Authorization") String authorization, @Body AcceptBuyerCancellationRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("orders/buyer_cancellation/reject")
    Call<RejectBuyerCancellationResponse> RejectBuyerCancellation(@Header("Authorization") String authorization, @Body RejectBuyerCancellationRequestBody requestBody);

}
