package com.fjnu.lazada.apitest;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.response.GetOrdersResponse;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;

/**
 * Created by LCC on 2018/5/31.
 */
public class GetOrdersApiTest {

    private static final String URL = "https://api.lazada.com.my/rest";
    private static final String APP_KEY = "103021";
    private static final String APP_SECRET = "cswXEzuzIjO1jjEHyQDXSAvkLRnxGOZ3";
    private static final String ACCESS_TOKEN = "50000700824cFdesgoGQzpUEhdm1HJp9SoFY148b4a0feiqiLW2gJhNjQbYd4f";

    public static void main(String[] args) {
        LazopClient client = new LazopClient(URL, APP_KEY, APP_SECRET);
        LazopRequest request = new LazopRequest();
        request.setApiName("/orders/get");
        request.setHttpMethod("GET");
        request.addApiParameter("created_after", "2018-05-1T00:00:00+08:00");
        request.addApiParameter("created_before", "2018-06-4T00:00:00+08:00");
        request.addApiParameter("status", "pending");
        request.addApiParameter("sort_direction", "DESC");
        request.addApiParameter("offset", "0");
        request.addApiParameter("limit", "10");
        request.addApiParameter("sort_by", "updated_at");
        try {
            LazopResponse response = client.execute(request, ACCESS_TOKEN);
            System.out.println(response.getBody());
            GetOrdersResponse getOrdersResponse = JSON.parseObject(response.getBody(), GetOrdersResponse.class);
            getOrdersResponse.toString();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
