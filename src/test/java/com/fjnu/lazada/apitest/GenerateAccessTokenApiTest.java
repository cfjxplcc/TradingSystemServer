package com.fjnu.lazada.apitest;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.bean.AccessTokenBean;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;

/**
 * Created by LCC on 2018/5/31.
 */
public class GenerateAccessTokenApiTest {

    // 获取 code 网址
    // https://auth.lazada.com/oauth/authorize?response_type=code&force_auth=true&redirect_uri=https://cfjxplcc.test&client_id=103021

    private static final String URL = "https://auth.lazada.com/rest";
    private static final String APP_KEY = "103021";
    private static final String APP_SECRET = "cswXEzuzIjO1jjEHyQDXSAvkLRnxGOZ3";

    public static void main(String[] args) {
        LazopClient client = new LazopClient(URL, APP_KEY, APP_SECRET);
        LazopRequest request = new LazopRequest();
        request.setApiName("/auth/token/create");
        request.addApiParameter("code", "0_mQDYkpimokQ3qhNmM8O4uqES2359");

        try {
            LazopResponse response = client.execute(request);
            System.out.println(response.getBody());
            AccessTokenBean accessTokenBean = JSON.parseObject(response.getBody(), AccessTokenBean.class);
            accessTokenBean.toString();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     *  response
     *  {"access_token":"50000701302rzr181e3703zhTjoSduTHfGhxiMrTDfGLj3eVTol0mssdomdagp","country":"cb","refresh_token":"50001700f02BDE1b427c13nr9f5IxfMUiWvkBDaPekUvfneyHamuWDgzdmWLEr","account_platform":"seller_center","refresh_expires_in":1209600,"country_user_info":[{"country":"sg","user_id":"100027341","seller_id":"100015339","short_code":"SG10XF3"},{"country":"th","user_id":"100033870","seller_id":"100023397","short_code":"TH11H2O"},{"country":"ph","user_id":"100020236","seller_id":"100014320","short_code":"PH10XJ3"},{"country":"id","user_id":"100051229","seller_id":"100045560","short_code":"ID12PTP"},{"country":"my","user_id":"100034641","seller_id":"100028626","short_code":"MY11G00"}],"expires_in":604800,"account":"18020753014@163.com","code":"0","request_id":"0b86d54915277607120946169"}
     *
     {
     "access_token": "50000701302rzr181e3703zhTjoSduTHfGhxiMrTDfGLj3eVTol0mssdomdagp",
     "country": "cb",
     "refresh_token": "50001700f02BDE1b427c13nr9f5IxfMUiWvkBDaPekUvfneyHamuWDgzdmWLEr",
     "account_platform": "seller_center",
     "refresh_expires_in": 1209600,
     "country_user_info": [
     {
     "country": "sg",
     "user_id": "100027341",
     "seller_id": "100015339",
     "short_code": "SG10XF3"
     },
     {
     "country": "th",
     "user_id": "100033870",
     "seller_id": "100023397",
     "short_code": "TH11H2O"
     },
     {
     "country": "ph",
     "user_id": "100020236",
     "seller_id": "100014320",
     "short_code": "PH10XJ3"
     },
     {
     "country": "id",
     "user_id": "100051229",
     "seller_id": "100045560",
     "short_code": "ID12PTP"
     },
     {
     "country": "my",
     "user_id": "100034641",
     "seller_id": "100028626",
     "short_code": "MY11G00"
     }
     ],
     "expires_in": 604800,
     "account": "18020753014@163.com",
     "code": "0",
     "request_id": "0b86d54915277607120946169"
     }
     *
     */

}
