package com.fjnu.tradingsysrem.shopee;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.common.retrofit.RetrofitManager;
import com.fjnu.tradingsysrem.shopee.request.body.shop.GetShopInfoRequestBody;
import com.fjnu.tradingsysrem.shopee.request.ShopRequest;
import com.fjnu.tradingsysrem.shopee.response.shop.GetShopInfoResponse;
import com.fjnu.tradingsysrem.spring.utils.EncryptUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.io.InputStream;
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

    private String getShopCancelAuthorizeUrl() {
        String token = EncryptUtils.stringToSHA256(key + appCancelAuthorizationCallbackUrl);
        return serverBaseUrl + "shop/cancel_auth_partner?id=" + partnerId + "&redirect="
                + appAuthorizationCallbackUrl + "&token=" + token;
    }

    public Response<GetShopInfoResponse> getShopInfo(int shopId) throws IOException {
        ShopRequest shopRequest = retrofit.create(ShopRequest.class);
        GetShopInfoRequestBody requestBody = new GetShopInfoRequestBody();
        requestBody.setPartner_id(partnerId);
        requestBody.setTimestamp(System.currentTimeMillis() / 1000);
        requestBody.setShopid(shopId);
        // TODO: 2018/9/10 apiUrl需要改成通过获取注解的方式
        String apiUrl = serverBaseUrl + "shop/get";
        String bodyJson = JSON.toJSONString(requestBody);
//        System.out.println(bodyJson);
//        System.out.println(apiUrl + "|" + bodyJson);
//        System.out.println("key:" + key);
        String authorization = EncryptUtils.sha256_HMAC(apiUrl + "|" + bodyJson, key);
//        System.out.println(authorization);
        Call<GetShopInfoResponse> call = shopRequest.getShopInfo(authorization, requestBody);
        return call.execute();
    }

    private static final class InstanceHolder {
        private static final ShopeeApiManager instance = new ShopeeApiManager();
    }
}
