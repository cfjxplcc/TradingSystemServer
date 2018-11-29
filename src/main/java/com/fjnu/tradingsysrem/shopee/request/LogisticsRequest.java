package com.fjnu.tradingsysrem.shopee.request;

import com.fjnu.tradingsysrem.shopee.request.body.logistics.GetLogisticInfoRequestBody;
import com.fjnu.tradingsysrem.shopee.request.body.logistics.LogisticsInitRequestBody;
import com.fjnu.tradingsysrem.shopee.response.logistics.GetLogisticInfoResponse;
import com.fjnu.tradingsysrem.shopee.response.logistics.LogisticsInitResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by luochunchen on 2018/11/28.
 */
public interface LogisticsRequest {

    @Headers("Content-Type: application/json")
    @POST("logistics/init_info/get")
    Call<GetLogisticInfoResponse> getLogisticInfo(@Header("Authorization") String authorization, @Body GetLogisticInfoRequestBody requestBody);

    @Headers("Content-Type: application/json")
    @POST("logistics/init")
    Call<LogisticsInitResponse> logisticInit(@Header("Authorization") String authorization, @Body LogisticsInitRequestBody requestBody);

}
