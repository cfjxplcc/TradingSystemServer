package com.fjnu.lazada.apitest;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.response.GetOrderResponse;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;

/**
 * Created by LCC on 2018/6/2.
 */
public class GetOrderApiTest {

    private static final String URL = "https://api.lazada.com.my/rest";
    private static final String APP_KEY = "103021";
    private static final String APP_SECRET = "cswXEzuzIjO1jjEHyQDXSAvkLRnxGOZ3";
    private static final String ACCESS_TOKEN = "50000700824cFdesgoGQzpUEhdm1HJp9SoFY148b4a0feiqiLW2gJhNjQbYd4f";

    public static void main(String[] args) {
        LazopClient client = new LazopClient(URL, APP_KEY, APP_SECRET);
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/get");
        request.setHttpMethod("GET");
        request.addApiParameter("order_id", "202811067726038");
        try {
            LazopResponse response = client.execute(request, ACCESS_TOKEN);
            System.out.println(response.getBody());
            GetOrderResponse getOrderResponse = JSON.parseObject(response.getBody(), GetOrderResponse.class);
            getOrderResponse.toString();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
