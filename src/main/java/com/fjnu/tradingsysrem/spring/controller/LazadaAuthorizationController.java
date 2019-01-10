package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.lazada.platform.LazadaApiManager;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LCC on 2018/6/4.
 */
@RestController
public class LazadaAuthorizationController {

    @Autowired
    private LazadaAuthorizationService authorizationService;

    /**
     * 获取用户授权页面地址
     *
     * @return
     */
    @GetMapping("/authorization/getUrl")
    public ResponseEntity<String> getAuthorizationUrl() {
        String force_auth = LazadaApiManager.getInstance().getAppCallbackUrl();
        String client_id = LazadaApiManager.getInstance().getAppKey();
        String url = "https://auth.lazada.com/oauth/authorize?response_type=code&force_auth=true&redirect_uri="
                + force_auth + "&client_id=" + client_id;
        return ResponseEntity.ok().body(url);
    }

    /**
     * 提供给 open.lazada开发平台的Callback URL
     *
     * @param code
     * @return
     */
    @GetMapping("/authorization/receiveCode")
    public ResponseEntity<String> receiveCode(@RequestParam("code") String code) {
        System.out.println("receiveCode code:" + code);
        String result = authorizationService.generateAccessToken(code);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/authorization/refreshAccessToken")
    public ResponseEntity<String> refreshAccessToken() {
        authorizationService.refreshAccessToken();
        return ResponseEntity.ok().body("刷新AccessToken完成");
    }


}
