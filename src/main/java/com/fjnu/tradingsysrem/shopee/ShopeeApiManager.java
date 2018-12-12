package com.fjnu.tradingsysrem.shopee;

import com.fjnu.tradingsysrem.common.retrofit.RetrofitManager;
import com.fjnu.tradingsysrem.shopee.request.LogisticsRequest;
import com.fjnu.tradingsysrem.shopee.request.OrdersRequest;
import com.fjnu.tradingsysrem.shopee.request.ShopRequest;
import com.fjnu.tradingsysrem.shopee.request.body.logistics.GetLogisticInfoRequestBody;
import com.fjnu.tradingsysrem.shopee.request.body.logistics.LogisticsInitRequestBody;
import com.fjnu.tradingsysrem.shopee.request.body.orders.GetOrderDetailsRequestBody;
import com.fjnu.tradingsysrem.shopee.request.body.orders.GetOrderListByCreateTimeRequestBody;
import com.fjnu.tradingsysrem.shopee.request.body.shop.GetShopInfoRequestBody;
import com.fjnu.tradingsysrem.shopee.response.logistics.GetLogisticInfoResponse;
import com.fjnu.tradingsysrem.shopee.response.logistics.LogisticsInitResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrderDetailsResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrdersResponse;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;
import com.fjnu.tradingsysrem.spring.utils.EncryptUtils;
import com.google.gson.Gson;
import org.springframework.lang.Nullable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by LCC on 2018/8/26.
 */
public class ShopeeApiManager {

    /** shopee properties的key */
    private static final String PROPERTIES_PARTNER_ID = "shopee.partner_id";
    private static final String PROPERTIES_KEY = "shopee.key";
    private static final String PROPERTIES_APP_AUTHORIZATION_CALLBACK_URL = "shopee.app_authorization_callback_url";
    private static final String PROPERTIES_APP_CANCEL_AUTHORIZATION_CALLBACK_URL = "shopee.app_cancel_authorization_callback_url";
    private static final String PROPERTIES_SERVER_BASE_URL = "shopee.server_base_url";

    private int partnerId;
    private String key;
    private String appAuthorizationCallbackUrl;
    private String appCancelAuthorizationCallbackUrl;
    private String serverBaseUrl;

    private Retrofit retrofit;

    private ShopeeApiManager() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("shopee.properties");
        try {
            properties.load(inputStream);

            //初始化应用秘钥
            partnerId = Integer.valueOf(properties.getProperty(PROPERTIES_PARTNER_ID));
            key = properties.getProperty(PROPERTIES_KEY);
            appAuthorizationCallbackUrl = properties.getProperty(PROPERTIES_APP_AUTHORIZATION_CALLBACK_URL);
            appCancelAuthorizationCallbackUrl = properties.getProperty(PROPERTIES_APP_CANCEL_AUTHORIZATION_CALLBACK_URL);
            serverBaseUrl = properties.getProperty(PROPERTIES_SERVER_BASE_URL);
           /* System.out.println(" partnerId:" + partnerId
                    + "\n key:" + key
                    + "\n appAuthorizationCallbackUrl:" + appAuthorizationCallbackUrl
                    + "\n appCancelAuthorizationCallbackUrl:" + appCancelAuthorizationCallbackUrl
                    + "\n serverBaseUrl:" + serverBaseUrl);*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        retrofit = RetrofitManager.getRetrofit(serverBaseUrl);
    }

    public static ShopeeApiManager getInstance() {
        return ShopeeApiManager.InstanceHolder.instance;
    }

    /**
     * 获取店铺授权url
     *
     * @return
     */
    public String getShopAuthorizationUrl() {
        String token = EncryptUtils.stringToSHA256(key + appAuthorizationCallbackUrl);
        return serverBaseUrl + "shop/auth_partner?id=" + partnerId + "&redirect="
                + appAuthorizationCallbackUrl + "&token=" + token;
    }

    /**
     * @return
     */
    public String getShopCancelAuthorizeUrl() {
        String token = EncryptUtils.stringToSHA256(key + appCancelAuthorizationCallbackUrl);
        return serverBaseUrl + "shop/cancel_auth_partner?id=" + partnerId + "&redirect="
                + appAuthorizationCallbackUrl + "&token=" + token;
    }

    /**
     * 获取店铺信息
     *
     * @param shopId 店铺id
     * @return
     * @throws IOException
     */
    public Response<GetShopInfoResponse> getShopInfo(int shopId) throws IOException {
        ShopRequest shopRequest = retrofit.create(ShopRequest.class);
        GetShopInfoRequestBody requestBody = new GetShopInfoRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "shop/get";
        // 注意：一定要使用Retrofit使用的第三方Json解析库，否则authorization不准确
        Gson gson = new Gson();
        String bodyJson = gson.toJson(requestBody);
       /* System.out.println(bodyJson);
        System.out.println(apiUrl + "|" + bodyJson);
        System.out.println("key:" + key);*/
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
//        System.out.println(authorization);
        Call<GetShopInfoResponse> call = shopRequest.getShopInfo(authorization, requestBody);
        return call.execute();
    }

    /**
     * 根据订单创建时间获取订单数据,时间最长15天
     *
     * @param shopId 店铺id
     * @param from   开始时间,单位秒
     * @param to     结束时间,单位秒
     * @return
     * @throws IOException
     */
    public Response<GetOrdersResponse> getOrderListByCreateTime(int shopId, long from, long to) throws IOException {
        OrdersRequest ordersRequest = retrofit.create(OrdersRequest.class);
        GetOrderListByCreateTimeRequestBody requestBody = new GetOrderListByCreateTimeRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        requestBody.setCreate_time_from(from);
        requestBody.setCreate_time_to(to);
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "orders/basics";
        Gson gson = new Gson();
        String bodyJson = gson.toJson(requestBody);
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
        Call<GetOrdersResponse> call = ordersRequest.getOrderListByCreateTime(authorization, requestBody);
        return call.execute();
    }

    /**
     * 获取订单详细信息
     *
     * @param shopId      店铺id
     * @param orderSnList 订单sn
     * @return
     * @throws IOException
     */
    public Response<GetOrderDetailsResponse> getOrderDetails(int shopId, List<String> orderSnList) throws IOException {
        OrdersRequest ordersRequest = retrofit.create(OrdersRequest.class);
        GetOrderDetailsRequestBody requestBody = new GetOrderDetailsRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        requestBody.setOrdersn_list(orderSnList);
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "orders/detail";
        Gson gson = new Gson();
        String bodyJson = gson.toJson(requestBody);
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
        Call<GetOrderDetailsResponse> call = ordersRequest.getOrderDetails(authorization, requestBody);
        return call.execute();
    }

    /**
     * 获取物流订单生成需要的信息
     *
     * @param shopId  店铺id
     * @param orderSn 订单sn
     * @return
     * @throws IOException
     */
    public Response<GetLogisticInfoResponse> getLogisticInfo(int shopId, String orderSn) throws IOException {
        LogisticsRequest logisticsRequest = retrofit.create(LogisticsRequest.class);
        GetLogisticInfoRequestBody requestBody = new GetLogisticInfoRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        requestBody.setOrdersn(orderSn);
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "logistics/init_info/get";
        Gson gson = new Gson();
        String bodyJson = gson.toJson(requestBody);
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
        Call<GetLogisticInfoResponse> call = logisticsRequest.getLogisticInfo(authorization, requestBody);
        return call.execute();
    }

    /**
     * 生成订单物流编号
     *
     * @param shopId        店铺id
     * @param orderSn       订单sn
     * @param pickup
     * @param dropOff
     * @param nonIntegrated
     * @return
     * @throws IOException
     */
    public Response<LogisticsInitResponse> logisticsInit(int shopId,
                                                         String orderSn,
                                                         @Nullable LogisticsInitRequestBody.Pickup pickup,
                                                         @Nullable LogisticsInitRequestBody.DropOff dropOff,
                                                         @Nullable LogisticsInitRequestBody.NonIntegrated nonIntegrated) throws IOException {
        LogisticsRequest logisticsRequest = retrofit.create(LogisticsRequest.class);
        LogisticsInitRequestBody requestBody = new LogisticsInitRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        requestBody.setOrdersn(orderSn);
        if (pickup != null) {
            requestBody.setPickup(pickup);
        }
        if (dropOff != null) {
            requestBody.setDropoff(dropOff);
        }
        if (nonIntegrated != null) {
            requestBody.setNon_integrated(nonIntegrated);
        }
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "logistics/init";
        Gson gson = new Gson();
        String bodyJson = gson.toJson(requestBody);
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
        Call<LogisticsInitResponse> call = logisticsRequest.logisticInit(authorization, requestBody);
        return call.execute();
    }

    private static final class InstanceHolder {
        private static final ShopeeApiManager instance = new ShopeeApiManager();
    }
}
