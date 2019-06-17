package com.fjnu.tradingsysrem.spring.service.shopee.imp;

import com.fjnu.tradingsysrem.shopee.ShopeeApiManager;
import com.fjnu.tradingsysrem.shopee.response.ShopeeResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrderDetailsResponse;
import com.fjnu.tradingsysrem.shopee.response.orders.GetOrdersResponse;
import com.fjnu.tradingsysrem.spring.dao.ExchangeRateDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeOrderItemsInfoDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeePurchaseOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.shopee.ShopeeShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeePurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.model.shopee.ShopeeShopInfo;
import com.fjnu.tradingsysrem.spring.service.shopee.ShopeeOrderService;
import com.fjnu.tradingsysrem.spring.utils.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Response;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by luochunchen on 2018/12/25.
 */
@Service
@Transactional(readOnly = true)
public class ShopeeOrderServiceImp implements ShopeeOrderService {
    private static Logger logger = LoggerFactory.getLogger(ShopeeOrderServiceImp.class);

    @Autowired
    private ShopeeOrderInfoDao shopeeOrderInfoDao;
    @Autowired
    private ExchangeRateDao exchangeRateDao;
    @Autowired
    private ShopeeOrderItemsInfoDao shopeeOrderItemsInfoDao;
    @Autowired
    private ShopeeShopInfoDao shopeeShopInfoDao;
    @Autowired
    private ShopeePurchaseOrderInfoDao shopeePurchaseOrderInfoDao;

    @Override
    @Transactional
    public void synchShopeeOrderInfoFromPlatformByCreateTime(ShopeeShopInfo shopeeShopInfo, long beginTime, long endTime) {
        // 判断时间是否超过15天
        float totalDays = ((endTime - beginTime) / 1000.0f / 60 / 60 / 24);
        logger.info("beginTime:" + beginTime + " ; endTime:" + endTime + " ; totalDays:" + totalDays);
        if (totalDays > 15.0f) {
            logger.info("End time is greater than the specified time");
            endTime = beginTime + 1000 * 60 * 60 * 24 * 15;
        }

        int tryCount = 0;
        boolean isRequestSuccessful = false;

        int pageIndex = 0;
        List<GetOrdersResponse.Order> orderList = new ArrayList<>();
        // 每次请求进行3次尝试
        while (tryCount < 3 && !isRequestSuccessful) {
            try {
                Response<GetOrdersResponse> response = ShopeeApiManager.getInstance().getOrderListByCreateTime(shopeeShopInfo.getShopId(), beginTime, endTime, pageIndex);
                ShopeeResponse<GetOrdersResponse> shopeeResponse = new ShopeeResponse<>(response);
                if (shopeeResponse.isSuccessful()) {
                    isRequestSuccessful = true;
                    GetOrdersResponse getOrdersResponse = shopeeResponse.getResponseBody();
                    orderList.addAll(getOrdersResponse.getOrders());
                    // 还有订单则继续发起请求
                    if (getOrdersResponse.isMore()) {
                        pageIndex++;
                        tryCount = 0;
                        isRequestSuccessful = false;
                    }
                } else {
                    shopeeResponse.printError();
                    tryCount++;
                    isRequestSuccessful = false;
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                tryCount++;
                isRequestSuccessful = false;
            }
        }

        // 对获取到的订单数据进行数据库对比,更新数据库中已经有的数据
        Iterator<GetOrdersResponse.Order> orderIterator = orderList.iterator();
        while (orderIterator.hasNext()) {
            GetOrdersResponse.Order order = orderIterator.next();
            String orderSn = order.getOrdersn();
            ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
            if (shopeeOrderInfo != null) {
                if (shopeeOrderInfo.getUpdateTime().getTime() / 1000 < order.getUpdate_time()) {
                    shopeeOrderInfo.setOrderStatus(order.getOrder_status());
                    shopeeOrderInfo.setUpdateTime(new Timestamp(order.getUpdate_time() * 1000));
                    shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
                }
                orderIterator.remove();
            }
        }

        ExchangeRate exchangeRate = exchangeRateDao.findFirstByCountryCodeOrderByDateDesc(shopeeShopInfo.getCountryCode().toLowerCase());
        // 从平台获取数据库中不存在的订单数据详细信息
        List<String> orderSnList = new ArrayList<>();
        for (int i = 0, count = 0; i < orderList.size(); i++) {
            orderSnList.add(orderList.get(i).getOrdersn());
            count++;
            // 最多一次请求50个订单数据，每次请求进行3次尝试
            if (count % 50 == 0 || i == orderList.size() - 1) {
                tryCount = 0;
                isRequestSuccessful = false;
                while (tryCount < 3 && !isRequestSuccessful) {
                    try {
                        Response<GetOrderDetailsResponse> response = ShopeeApiManager.getInstance().getOrderDetails(shopeeShopInfo.getShopId(), orderSnList);
                        ShopeeResponse<GetOrderDetailsResponse> shopeeResponse = new ShopeeResponse<>(response);
                        if (shopeeResponse.isSuccessful()) {
                            isRequestSuccessful = true;
                            // 打印出错的订单信息
                            GetOrderDetailsResponse getOrderDetailsResponse = shopeeResponse.getResponseBody();
                            List<String> errors = getOrderDetailsResponse.getErrors();
                            for (String error : errors) {
                                logger.info("(getOrderDetails)Orders that encountered error:" + error);
                            }

                            // 保存订单详细数据
                            List<GetOrderDetailsResponse.Orders> ordersList = getOrderDetailsResponse.getOrders();
                            for (GetOrderDetailsResponse.Orders orders : ordersList) {
                                ShopeeOrderInfo shopeeOrderInfo = new ShopeeOrderInfo(shopeeShopInfo, exchangeRate, orders);
                                try {
                                    shopeeOrderInfo = shopeeOrderInfoDao.save(shopeeOrderInfo);
                                    if (shopeeOrderInfo != null) {
                                        // 保存数据成功
                                        List<GetOrderDetailsResponse.Item> itemList = orders.getItems();
                                        for (GetOrderDetailsResponse.Item item : itemList) {
                                            ShopeeOrderItemsInfo shopeeOrderItemsInfo = new ShopeeOrderItemsInfo(shopeeOrderInfo, item);
                                            shopeeOrderItemsInfoDao.save(shopeeOrderItemsInfo);
                                        }
                                    } else {
                                        logger.info("订单(" + shopeeOrderInfo.getOrderSn() + ")保存失败!!!");
                                    }
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }
                        } else {
                            tryCount++;
                            shopeeResponse.printError();
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                orderSnList.clear();
            }
        }
    }

    @Override
    public ShopeeOrderInfo getBySn(String orderSn) {
        if (TextUtils.isEmpty(orderSn)) {
            return null;
        }
        return shopeeOrderInfoDao.findByOrderSn(orderSn);
    }

    @Override
    public List<ShopeeOrderInfo> getAllByShopInfoAndParameters(int shopId, String orderStatus, String beginTime, String endTime) {
        ShopeeShopInfo shopeeShopInfo = shopeeShopInfoDao.findByShopId(shopId);
        if (shopeeShopInfo == null) {
            return new ArrayList<>();
        }

        java.sql.Timestamp begin = new Timestamp(Long.valueOf(beginTime));
        java.sql.Timestamp end = new Timestamp(Long.valueOf(endTime));

        if (TextUtils.isEmpty(orderStatus)) {
            return shopeeOrderInfoDao.findAllByShopeeShopInfoAndCreateTimeBetweenOrderByShopeeShopInfoAscCreateTimeDesc(shopeeShopInfo, begin, end);
        } else {
            return shopeeOrderInfoDao.findAllByShopeeShopInfoAndOrderStatusAndCreateTimeBetweenOrderByShopeeShopInfoAscCreateTimeDesc(shopeeShopInfo, orderStatus, begin, end);
        }
    }

    @Override
    public List<ShopeeOrderInfo> getAllByParameters(String orderStatus, String beginTime, String endTime) {
        java.sql.Timestamp begin = new Timestamp(Long.valueOf(beginTime));
        java.sql.Timestamp end = new Timestamp(Long.valueOf(endTime));

        if (TextUtils.isEmpty(orderStatus)) {
            return shopeeOrderInfoDao.findAllByCreateTimeBetweenOrderByShopeeShopInfoAscCreateTimeDesc(begin, end);
        } else {
            return shopeeOrderInfoDao.findAllByOrderStatusAndCreateTimeBetweenOrderByShopeeShopInfoAscCreateTimeDesc(orderStatus, begin, end);
        }
    }

    @Override
    @Transactional
    public boolean updateDeliveryStatus(String orderSn, boolean isDelivery) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo == null) {
            return false;
        }
        shopeeOrderInfo.setDelivery(isDelivery);
        shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
        return true;
    }

    @Override
    @Transactional
    public boolean updateRemark(String orderSn, String remark) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo == null) {
            return false;
        }
        shopeeOrderInfo.setRemarks(remark);
        shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
        return true;
    }

    @Override
    @Transactional
    public boolean updateOverseasExpressPrice(String orderSn, float overseasExpressPrice) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo == null) {
            return false;
        }
        shopeeOrderInfo.setOverseasExpressPrice(overseasExpressPrice);
        shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
        return true;
    }

    @Override
    public Set<ShopeeOrderInfo> getByOrderExpressNumber(String orderExpressNumber) {
        Set<ShopeeOrderInfo> lazadaOrderInfoSet = new HashSet<>();
        List<ShopeePurchaseOrderInfo> purchaseOrderInfoList = shopeePurchaseOrderInfoDao.findAllByOrderExpressNumber(orderExpressNumber);
        for (ShopeePurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getShopeeOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    @Override
    public Set<ShopeeOrderInfo> getByPurchaseOrderExpressIsNull() {
        Set<ShopeeOrderInfo> lazadaOrderInfoSet = new HashSet<>();
        List<ShopeePurchaseOrderInfo> purchaseOrderInfoList = shopeePurchaseOrderInfoDao.findAllByOrderExpressNumberIsNull();
        for (ShopeePurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getShopeeOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    @Override
    public List<ShopeeOrderInfo> getByOrderDeliveryStatusIsFalse() {
        return shopeeOrderInfoDao.findAllByDeliveryIsFalseOrderByCreateTimeAsc();
    }

    @Override
    public Set<ShopeeOrderInfo> getByPurchaseOrderInfoThirdPartyOrderId(String thirdPartyOrderId) {
        Set<ShopeeOrderInfo> lazadaOrderInfoSet = new HashSet<>();
        List<ShopeePurchaseOrderInfo> purchaseOrderInfoList = shopeePurchaseOrderInfoDao.findAllByThirdPartyOrderId(thirdPartyOrderId);
        for (ShopeePurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getShopeeOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    @Override
    @Transactional
    public boolean updateOrderStatus(String orderSn, String orderStatus) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo != null) {
            shopeeOrderInfo.setOrderStatus(orderStatus);
            shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateOrderTrackingNo(String orderSn, String trackingNo) {
        ShopeeOrderInfo shopeeOrderInfo = shopeeOrderInfoDao.findByOrderSn(orderSn);
        if (shopeeOrderInfo != null) {
            shopeeOrderInfo.setTrackingNo(trackingNo);
            shopeeOrderInfoDao.saveAndFlush(shopeeOrderInfo);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void synchShopeeOrderInfoFromPlatformByCreateTime(long beginTime, long endTime) {
        List<ShopeeShopInfo> shopeeShopInfoList = shopeeShopInfoDao.findAll();
        for (ShopeeShopInfo shopeeShopInfo : shopeeShopInfoList) {
            if (shopeeShopInfo.isAuthorizationFlag()) {
                synchShopeeOrderInfoFromPlatformByCreateTime(shopeeShopInfo, beginTime, endTime);
            }
        }
    }

    @Override
    public Set<ShopeeOrderInfo> getByItemSkuAndDeliveryIsFalse(String sku) {
        Set<ShopeeOrderInfo> shopeeOrderInfoSet = new HashSet<>();
        List<ShopeeOrderItemsInfo> shopeeOrderItemsInfoList = shopeeOrderItemsInfoDao.findAllByItemSkuAndShopeeOrderInfoDeliveryIsFalseOrderByShopeeOrderInfoCreateTimeDesc(sku);
        for (ShopeeOrderItemsInfo shopeeOrderItemsInfo : shopeeOrderItemsInfoList) {
            shopeeOrderInfoSet.add(shopeeOrderItemsInfo.getShopeeOrderInfo());
        }
        return shopeeOrderInfoSet;
    }
}
