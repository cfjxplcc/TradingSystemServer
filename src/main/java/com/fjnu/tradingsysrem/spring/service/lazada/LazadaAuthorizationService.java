package com.fjnu.tradingsysrem.spring.service.lazada;

/**
 * Created by LCC on 2018/6/4.
 */
public interface LazadaAuthorizationService {

    /**
     * 根据code生成AccessToken
     *
     * @param code
     * @return
     */
    String generateAccessToken(String code);

    void refreshAccessToken();

}
