package com.fjnu.tradingsysrem.lazada.platform;

import com.alibaba.fastjson.JSON;
import com.fjnu.tradingsysrem.lazada.platform.bean.*;
import com.fjnu.tradingsysrem.lazada.platform.response.*;
import com.fjnu.tradingsysrem.spring.model.Country;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;
import com.lazada.lazop.api.LazopClient;
import com.lazada.lazop.api.LazopRequest;
import com.lazada.lazop.api.LazopResponse;
import com.lazada.lazop.util.ApiException;
import com.lazada.lazop.util.UrlConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.*;

/**
 * Created by LCC on 2017/10/31.
 */
public class LazadaApiManager {

    /** Lazada 应用密码properties的key */
    private static final String PROPERTIES_APP_KEY_NAME = "lazada.app_key";
    private static final String PROPERTIES_APP_SECRET_NAME = "lazada.app_secret";
    private static final String PROPERTIES_APP_CALLBACK_URL = "lazada.app_callback_url";

    private String appKey = "";
    private String appSecret = "";
    private String appCallbackUrl = "";

    private static final Map<String, String> SERVICE_URL_MAP = new HashMap<String, String>() {
        {
            put(Country.INDONESIA.getCode(), UrlConstants.API_GATEWAY_URL_ID);
            put(Country.MALAYSIA.getCode(), UrlConstants.API_GATEWAY_URL_MY);
            put(Country.PHILIPPINES.getCode(), UrlConstants.API_GATEWAY_URL_PH);
            put(Country.SINGAPORE.getCode(), UrlConstants.API_GATEWAY_URL_SG);
            put(Country.VIETNAM.getCode(), UrlConstants.API_GATEWAY_URL_VN);
            put(Country.THAILAND.getCode(), UrlConstants.API_GATEWAY_URL_TH);
        }
    };

    private LazopClient client;
    private String access_token;

    private LazadaApiManager() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("lazadakey.properties");
        try {
            properties.load(inputStream);

            //初始化应用秘钥
            appKey = properties.getProperty(PROPERTIES_APP_KEY_NAME);
            appSecret = properties.getProperty(PROPERTIES_APP_SECRET_NAME);
            appCallbackUrl = properties.getProperty(PROPERTIES_APP_CALLBACK_URL);
//            System.out.println("appKey:" + appKey + "  |  appSecret:" + appSecret);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "1080");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "1080");
    }

    public static final LazadaApiManager getInstance() {
        return InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final LazadaApiManager instance = new LazadaApiManager();
    }

    /**
     * @param lazadaShopInfo
     */
    public void initClient(LazadaShopInfo lazadaShopInfo) {
        client = new LazopClient(lazadaShopInfo.getApiUrl(), appKey, appSecret);
        client.setConnectTimeout(30);
        client.setReadTimeout(60);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        access_token = lazadaShopInfo.getAccessToken();
    }

    /**
     * @param status
     * @param beginTime 格式需要符合：ISO 8601 date format yyyy-MM-dd'T'HH:mm:ss'Z'
     * @param endTime   规则同上
     * @return
     * @throws ApiException
     */
    public List<OrderBean> getOrders(OrderBean.Status status, String beginTime, String endTime) throws ApiException {
        System.out.println("getOrders(status:" + status + " , beginTime:" + beginTime + " , endTime:" + endTime + ")");

        LazopRequest request = new LazopRequest();
        request.setApiName("/orders/get");
        request.setHttpMethod("GET");
        request.addApiParameter("created_after", beginTime);
        request.addApiParameter("created_before", endTime);
        if (status != null) {
            request.addApiParameter("status", status.getStatus());
        }
        request.addApiParameter("sort_direction", "DESC");
        request.addApiParameter("sort_by", "updated_at");
        LazopResponse response = client.execute(request, access_token);
        if (response.getCode().equals("0")) {
            GetOrdersResponse getOrdersResponse = JSON.parseObject(response.getBody(), GetOrdersResponse.class);
            //        System.out.println(response.getBody());
            return getOrdersResponse.getData().getOrders();
        } else {
            System.out.println(response.getBody());
            return new ArrayList<>();
        }
    }

    public OrderBean getOrder(LazadaShopInfo lazadaShopInfo, Long orderId) throws ApiException {
        LazopClient client = new LazopClient(lazadaShopInfo.getApiUrl(), appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/get");
        request.setHttpMethod("GET");
        request.addApiParameter("order_id", String.valueOf(orderId));
        LazopResponse response = client.execute(request, lazadaShopInfo.getAccessToken());
        if (response.getCode().equals("0")) {
            //        System.out.println(response.getBody());
            GetOrderResponse getOrderResponse = JSON.parseObject(response.getBody(), GetOrderResponse.class);
            return getOrderResponse.getData();
        } else {
            System.out.println(response.getBody());
            return null;
        }
    }

    /**
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     * @throws ApiException
     */
    public List<OrderBean> getOrders(OrderBean.Status status, long beginTime, long endTime) throws ApiException {
        String begin = DateUtils.dateToISO8601DateFormatStr(beginTime);
        String end = DateUtils.dateToISO8601DateFormatStr(endTime);
        return getOrders(status, begin, end);
    }

    public List<OrderItemBean> getOrderItems(LazadaShopInfo lazadaShopInfo, Long orderId) throws ApiException {
        LazopClient client = new LazopClient(lazadaShopInfo.getApiUrl(), appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/items/get");
        request.setHttpMethod("GET");
        request.addApiParameter("order_id", String.valueOf(orderId));
        LazopResponse response = client.execute(request, lazadaShopInfo.getAccessToken());
        if (response.getCode().equals("0")) {
            //        System.out.println(response.getBody());
            GetOrderItemsResponse getOrderItemsResponse = JSON.parseObject(response.getBody(), GetOrderItemsResponse.class);
            return getOrderItemsResponse.getData();
        } else {
            System.out.println(response.getBody());
            return new ArrayList<>();
        }
    }

    /**
     * @param orderId
     * @throws ApiException
     */
    public List<OrderItemBean> getOrderItems(Long orderId) throws ApiException {
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/items/get");
        request.setHttpMethod("GET");
        request.addApiParameter("order_id", String.valueOf(orderId));
        LazopResponse response = client.execute(request, access_token);
        if (response.getCode().equals("0")) {
            //        System.out.println(response.getBody());
            GetOrderItemsResponse getOrderItemsResponse = JSON.parseObject(response.getBody(), GetOrderItemsResponse.class);
            return getOrderItemsResponse.getData();
        } else {
            System.out.println(response.getBody());
            return new ArrayList<>();
        }
    }

    public List<ShipmentProviderBean> getShipmentProviders(LazadaShopInfo shopInfo) throws ApiException {
        System.out.println("getShipmentProviders()");

        LazopClient client = new LazopClient(shopInfo.getApiUrl(), appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/shipment/providers/get");
        request.setHttpMethod("GET");
        LazopResponse response = client.execute(request, shopInfo.getAccessToken());
        System.out.println(response.getBody());
        if (response.getCode().equals("0")) {
            GetShipmentProvidersResponse getShipmentProvidersResponse = JSON.parseObject(response.getBody(), GetShipmentProvidersResponse.class);
            return getShipmentProvidersResponse.getData().getShipment_providers();
        } else {
            return new ArrayList<>();
        }
    }

    public List<PackedByMarketplaceOrderItemBean> setStatusToPackedByMarketplace(LazadaShopInfo lazadaShopInfo, List<LazadaOrderItemsInfo> lazadaOrderItemsInfoList, String shippingProvider) throws ApiException {
        System.out.println("setStatusToPackedByMarketplace()");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (LazadaOrderItemsInfo lazadaOrderItemsInfo : lazadaOrderItemsInfoList) {
            stringBuffer.append(lazadaOrderItemsInfo.getLazadaOrderItemId());
            stringBuffer.append(",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("]");
        System.out.println(stringBuffer.toString());

        LazopClient client = new LazopClient(lazadaShopInfo.getApiUrl(), appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/pack");
        request.addApiParameter("shipping_provider", shippingProvider);
        request.addApiParameter("delivery_type", DeliveryType.Dropship.getType());
        request.addApiParameter("order_item_ids", stringBuffer.toString());

        LazopResponse response = client.execute(request, lazadaShopInfo.getAccessToken());
        System.out.println(response.getBody());
        if (response.getCode().equals("0")) {
            SetStatusToPackedByMarketplaceResponse setStatusToPackedByMarketplaceResponse = JSON.parseObject(response.getBody(), SetStatusToPackedByMarketplaceResponse.class);
            return setStatusToPackedByMarketplaceResponse.getData().getOrder_items();
        } else {
            return new ArrayList<>();
        }
    }

    public String setStatusToReadyToShip(LazadaShopInfo lazadaShopInfo, List<LazadaOrderItemsInfo> lazadaOrderItemsInfoList, String shippingProvider, String trackingNumber) throws ApiException {
        System.out.println("setStatusToReadyToShip");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (LazadaOrderItemsInfo lazadaOrderItemsInfo : lazadaOrderItemsInfoList) {
            stringBuffer.append(lazadaOrderItemsInfo.getLazadaOrderItemId());
            stringBuffer.append(",");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("]");
        System.out.println(stringBuffer.toString());

        LazopClient client = new LazopClient(lazadaShopInfo.getApiUrl(), appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/order/rts");
        request.addApiParameter("delivery_type", DeliveryType.Dropship.getType());
        request.addApiParameter("order_item_ids", stringBuffer.toString());
        request.addApiParameter("shipment_provider", shippingProvider);
        request.addApiParameter("tracking_number", trackingNumber);

        LazopResponse response = client.execute(request, lazadaShopInfo.getAccessToken());
        System.out.println(response.getBody());
        if (response.getCode().equals("0")) {
            SetStatusToPackedByMarketplaceResponse setStatusToPackedByMarketplaceResponse = JSON.parseObject(response.getBody(), SetStatusToPackedByMarketplaceResponse.class);
            if (!setStatusToPackedByMarketplaceResponse.getData().getOrder_items().isEmpty()) {
                System.out.println("Update status succeed?true");
                return "Update status succeed?true";
            } else {
                System.out.println("Update status succeed?false");
                return "Update status succeed?false";
            }
        } else {
            System.out.println("Update status succeed?false");
            return "Update status succeed?false";
        }
    }

    public LazopResponse generateAccessToken(String code) throws ApiException {
        LazopClient client = new LazopClient(UrlConstants.API_AUTHORIZATION_URL, appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/auth/token/create");
        request.addApiParameter("code", code);
        LazopResponse response = client.execute(request);
        return response;
    }

    public AccessTokenBean refreshAccessToken(String refreshToken) throws ApiException {
        LazopClient client = new LazopClient(UrlConstants.API_AUTHORIZATION_URL, appKey, appSecret);
        // 使用代理访问境外服务器
        client.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(1080)));
        LazopRequest request = new LazopRequest();
        request.setApiName("/auth/token/refresh");
        request.addApiParameter("refresh_token", refreshToken);
        LazopResponse response = client.execute(request);
        return JSON.parseObject(response.getBody(), AccessTokenBean.class);
    }

    public String getApiUrl(String countryCode) {
        return SERVICE_URL_MAP.get(countryCode.toLowerCase());
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getAppCallbackUrl() {
        return appCallbackUrl;
    }
}
