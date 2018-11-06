package com.fjnu.lazada.apitest;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.bean.AccessTokenBean;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;

/**
 * Created by LCC on 2018/6/1.
 */
public class RefreshAccessTokenApiTest {

    private static final String URL = "https://auth.lazada.com/rest";
    private static final String APP_KEY = "103021";
    private static final String APP_SECRET = "cswXEzuzIjO1jjEHyQDXSAvkLRnxGOZ3";

    public static void main(String[] args) {
        LazopClient client = new LazopClient(URL, APP_KEY, APP_SECRET);
        LazopRequest request = new LazopRequest();
        request.setApiName("/auth/token/refresh");
        request.addApiParameter("refresh_token", "50001701419byHgvCowCvrHBkFwBPTt1655e705CGdwiylrXkH5CSseMJtXWhy");

        try {
            LazopResponse response = client.execute(request);
            System.out.println(response.getBody());
            AccessTokenBean accessTokenBean = JSON.parseObject(response.getBody(), AccessTokenBean.class);
            accessTokenBean.toString();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
