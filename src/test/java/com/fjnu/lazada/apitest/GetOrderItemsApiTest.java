package com.fjnu.lazada.apitest;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.response.GetOrderItemsResponse;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;

/**
 * Created by LCC on 2018/6/3.
 */
public class GetOrderItemsApiTest {

    private static final String URL = "https://api.lazada.com.my/rest";
    private static final String APP_KEY = "103021";
    private static final String APP_SECRET = "cswXEzuzIjO1jjEHyQDXSAvkLRnxGOZ3";
    private static final String ACCESS_TOKEN = "50000700824cFdesgoGQzpUEhdm1HJp9SoFY148b4a0feiqiLW2gJhNjQbYd4f";

    public static void main(String[] args) {
        LazopClient client = new LazopClient(URL, APP_KEY, APP_SECRET);
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/items/get");
        request.setHttpMethod("GET");
        request.addApiParameter("order_id", "203019890686539");
        try {
            LazopResponse response = client.execute(request, ACCESS_TOKEN);
            System.out.println(response.getBody());
            GetOrderItemsResponse getOrderItemsResponse = JSON.parseObject(response.getBody(), GetOrderItemsResponse.class);
            getOrderItemsResponse.toString();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
