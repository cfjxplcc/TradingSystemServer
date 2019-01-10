package com.fjnu.tradingsysrem.spring.service.lazada.imp;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.LazadaApiManager;
import com.fjnu.tradingsysrem.lazada.platform.bean.AccessTokenBean;
import com.fjnu.tradingsysrem.spring.dao.LazadaShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaAuthorizationService;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;
import com.fjnu.tradingsysrem.spring.utils.TextUtils;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by LCC on 2018/6/4.
 */
@Service
@Transactional(readOnly = true)
public class LazadaAuthorizationServiceImp implements LazadaAuthorizationService {

    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;

    @Override
    @Transactional
    public String generateAccessToken(String code) {
        if (TextUtils.isEmpty(code)) {
            System.out.println("Error:Code is empty");
            return "Error:Code is empty";
        }

        try {
            LazopResponse response = LazadaApiManager.getInstance().generateAccessToken(code);
            System.out.println(response.getBody());
            if (response.getCode().equals("0")) {
                AccessTokenBean accessTokenBean = JSON.parseObject(response.getBody(), AccessTokenBean.class);
                String accessToken = accessTokenBean.getAccess_token();
                int expriesIn = accessTokenBean.getExpires_in();
                String refreshToken = accessTokenBean.getRefresh_token();
                int refreshExpiresIn = accessTokenBean.getRefresh_expires_in();
                String acount = accessTokenBean.getAccount();
                java.sql.Timestamp lastTokenUpdateTime = DateUtils.nowTime();

                //保存用户数据
                List<AccessTokenBean.CountryUserInfoBean> userInfoBeanList = accessTokenBean.getCountry_user_info();
                for (AccessTokenBean.CountryUserInfoBean countryUserInfoBean : userInfoBeanList) {
                    boolean isNewData = false;//判断是否是新店铺数据
                    LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.findByShortCode(countryUserInfoBean.getShort_code());
                    //店铺不存在或店铺信息为旧数据
                    if (lazadaShopInfo == null) {
                        List<LazadaShopInfo> lazadaShopInfoList = lazadaShopInfoDao.findAllByEmailAndCountryCode(acount, countryUserInfoBean.getCountry());
                        if (lazadaShopInfoList.size() > 1) {
                            //当前版本一个账户在一个国家只能有一个账户,不考虑一个账户在一个国家有多个账户的情况
                            System.out.println("Error:Acount(" + acount + ") countryCode(" + countryUserInfoBean.getCountry() + ") size is wrong!!!");
                            continue;
                        } else if (lazadaShopInfoList.isEmpty()) {
                            //添加新的店铺数据
                            lazadaShopInfo = new LazadaShopInfo();
                            lazadaShopInfo.setEmail(acount);
                            lazadaShopInfo.setCountryCode(countryUserInfoBean.getCountry());
                            lazadaShopInfo.setSellerId(countryUserInfoBean.getSeller_id());
                            lazadaShopInfo.setUserId(countryUserInfoBean.getUser_id());
                            lazadaShopInfo.setShortCode(countryUserInfoBean.getShort_code());
                            lazadaShopInfo.setApiUrl(LazadaApiManager.getInstance().getApiUrl(countryUserInfoBean.getCountry()));
                            isNewData = true;
                        } else {
                            //旧数据,添加对应的UserId、SellerId、ShortCode
                            lazadaShopInfo = lazadaShopInfoList.get(0);
                            lazadaShopInfo.setSellerId(countryUserInfoBean.getSeller_id());
                            lazadaShopInfo.setUserId(countryUserInfoBean.getUser_id());
                            lazadaShopInfo.setShortCode(countryUserInfoBean.getShort_code());
                            lazadaShopInfo.setApiUrl(LazadaApiManager.getInstance().getApiUrl(countryUserInfoBean.getCountry()));
                        }
                    }

                    lazadaShopInfo.setAccessToken(accessToken);
                    lazadaShopInfo.setExpireeIn(expriesIn);
                    lazadaShopInfo.setRefreshToken(refreshToken);
                    lazadaShopInfo.setRefreshExpireeIn(refreshExpiresIn);
                    lazadaShopInfo.setLastTokenUpdateTime(lastTokenUpdateTime);

                    if (isNewData) {
                        lazadaShopInfoDao.save(lazadaShopInfo);
                    } else {
                        lazadaShopInfoDao.saveAndFlush(lazadaShopInfo);
                    }
                }
                return "GenerateAccessToken successful 授权店铺成功";
            } else {
                return response.getBody();
            }
        } catch (ApiException e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "Error:" + e.toString();
        }
    }

    @Override
    @Transactional
    public void refreshAccessToken() {
        System.out.println("----------------- RefreshAccessToken begin -------------------");
        List<LazadaShopInfo> lazadaShopInfoList = lazadaShopInfoDao.findAllByOrderByEmail();
        long currentTime = System.currentTimeMillis();
        System.out.println("currentTime:" + DateUtils.dateToStr(new Date(currentTime), "yyyy-MM-dd HH:mm:ss"));
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(currentTime));

        for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
            if (TextUtils.isEmpty(lazadaShopInfo.getRefreshToken())) {
                continue;
            }
            long lastTokenUpdateTime = DateUtils.timeToDate(lazadaShopInfo.getLastTokenUpdateTime()).getTime();
            int expireIn = lazadaShopInfo.getExpireeIn();
            int refreshExpiresIn = lazadaShopInfo.getRefreshExpireeIn();
            String refreshToken = lazadaShopInfo.getRefreshToken();

            Calendar tokenUpdateTime = Calendar.getInstance();
            tokenUpdateTime.setTime(new Date(lastTokenUpdateTime));

            if (now.equals(tokenUpdateTime)) {
                //Token最后更新时间等于当前时间
                continue;
            }

            if ((now.getTimeInMillis() - tokenUpdateTime.getTimeInMillis()) / 1000 < expireIn - 120) {
                //Token还在有效期内(有效期为lazada平台返回的有效时间减去120分钟)
                continue;
            } else {
                if ((now.getTimeInMillis() - tokenUpdateTime.getTimeInMillis()) / 1000 > refreshExpiresIn) {
                    //RefreshToken超出有效时间
                    lazadaShopInfo.setAccessToken(null);
                    lazadaShopInfo.setRefreshToken(null);
                    lazadaShopInfo.setExpireeIn(0);
                    lazadaShopInfo.setRefreshExpireeIn(0);
                    lazadaShopInfo.setLastTokenUpdateTime(new Timestamp(currentTime));
                } else {
                    try {
                        AccessTokenBean accessTokenBean = LazadaApiManager.getInstance().refreshAccessToken(refreshToken);
                        if (accessTokenBean.isSuccess()) {
                            String accessToken = accessTokenBean.getAccess_token();
                            expireIn = accessTokenBean.getExpires_in();
                            refreshToken = accessTokenBean.getRefresh_token();
                            refreshExpiresIn = accessTokenBean.getRefresh_expires_in();
                            List<AccessTokenBean.CountryUserInfoBean> countryUserInfoBeanList = accessTokenBean.getCountry_user_info();
                            for (AccessTokenBean.CountryUserInfoBean userInfoBean : countryUserInfoBeanList) {
                                for (LazadaShopInfo shopInfo : lazadaShopInfoList) {
                                    if (userInfoBean.getSeller_id().equals(shopInfo.getSellerId())) {
                                        shopInfo.setAccessToken(accessToken);
                                        shopInfo.setRefreshToken(refreshToken);
                                        shopInfo.setExpireeIn(expireIn);
                                        shopInfo.setRefreshExpireeIn(refreshExpiresIn);
                                        shopInfo.setLastTokenUpdateTime(new Timestamp(currentTime));
                                        break;
                                    }
                                }
                            }
                        } else {
                            System.out.println("Error:RefreshAccessToken " + accessTokenBean.getMessage());
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        }

        for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
            lazadaShopInfoDao.saveAndFlush(lazadaShopInfo);
        }

        long methodEndTime = System.currentTimeMillis();
        System.out.println("----------------------- refreshAccessToken cost time "
                + (methodEndTime - currentTime) / 1000 + " second -----------------");
    }
}
