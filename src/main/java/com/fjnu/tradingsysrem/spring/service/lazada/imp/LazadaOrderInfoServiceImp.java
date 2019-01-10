package com.fjnu.tradingsysrem.spring.service.lazada.imp;

import com.fjnu.tradingsysrem.lazada.platform.LazadaApiManager;
import com.fjnu.tradingsysrem.lazada.platform.bean.*;
import com.fjnu.tradingsysrem.spring.dao.*;
import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.PurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaOrderInfoService;
import com.fjnu.tradingsysrem.spring.utils.DateUtils;
import com.fjnu.tradingsysrem.spring.utils.TextUtils;
import com.lazada.lazop.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LCC on 2018/3/20.
 */
@Service
@Transactional(readOnly = true)
public class LazadaOrderInfoServiceImp implements LazadaOrderInfoService {

    @Autowired
    private LazadaOrderInfoDao lazadaOrderInfoDao;
    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;
    @Autowired
    private PurchaseOrderInfoDao purchaseOrderInfoDao;
    @Autowired
    private LazadaOrderItemsInfoDao lazadaOrderItemsInfoDao;
    @Autowired
    private ExchangeRateDao exchangeRateDao;

    @Override
    public LazadaOrderInfo get(String id) {
        return lazadaOrderInfoDao.findById(id);
    }

    @Override
    public List<LazadaOrderInfo> list() {
        return lazadaOrderInfoDao.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public List<LazadaOrderInfo> getAllByParameters(String lazadaShopInfoId, String orderStatus, String beginTime, String endTime) {
        java.sql.Timestamp begin = new Timestamp(Long.valueOf(beginTime));
        java.sql.Timestamp end = new Timestamp(Long.valueOf(endTime));

        if (TextUtils.isEmpty(lazadaShopInfoId)) {
            if (TextUtils.isEmpty(orderStatus)) {
                return lazadaOrderInfoDao.findAllByCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(begin, end);
            }
            return lazadaOrderInfoDao.findAllByOrderStatusAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(orderStatus, begin, end);
        }

        LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.getById(lazadaShopInfoId);
        if (lazadaShopInfo == null) {
            return new ArrayList<>();
        }

        if (TextUtils.isEmpty(orderStatus)) {
            return lazadaOrderInfoDao.findAllByLazadaShopInfoAndCreateTimeBetweenOrderByCreateTime(lazadaShopInfo, begin, end);
        }

        return lazadaOrderInfoDao.findAllByLazadaShopInfoAndOrderStatusAndCreateTimeBetweenOrderByCreateTime(lazadaShopInfo, orderStatus, begin, end);
    }

    @Override
    @Transactional
    public LazadaOrderInfo save(LazadaOrderInfo lazadaOrderInfo) {
        return lazadaOrderInfoDao.save(lazadaOrderInfo);
    }

    @Override
    @Transactional
    public void update(String id, LazadaOrderInfo lazadaOrderInfo) {
        lazadaOrderInfo.setId(id);
        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
    }

    @Override
    public Set<LazadaOrderInfo> findByPurchaseOrderInfoExpressIsNull() {
        Set<LazadaOrderInfo> lazadaOrderInfoSet = new LinkedHashSet<>();
        List<PurchaseOrderInfo> purchaseOrderInfoList = purchaseOrderInfoDao.findAllByOrderExpressNumberIsNullOrderByLazadaOrderInfoLazadaShopInfoShopNameAscLazadaOrderInfoCreateTimeDesc();
        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getLazadaOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    @Override
    public Set<LazadaOrderInfo> getAllByPurchaseOrderInfoThirdPartyOrderId(String thirdPartyOrderId) {
        Set<LazadaOrderInfo> lazadaOrderInfoSet = new HashSet<>();
        List<PurchaseOrderInfo> purchaseOrderInfoList = purchaseOrderInfoDao.findAllByThirdPartyOrderId(thirdPartyOrderId);
        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getLazadaOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    /**
     * 根据店铺信息获取对应的货运商信息
     *
     * @param lazadaShopInfoId
     * @return 请求失败返回null
     */
    @Override
    public List<ShipmentProviderBean> getShipmentProviderByLazadaShopInfo(String lazadaShopInfoId) {
        LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.getById(lazadaShopInfoId);
        if (lazadaShopInfo == null) {
            return null;
        }
        LazadaApiManager lazadaApiManager = LazadaApiManager.getInstance();

        try {
            List<ShipmentProviderBean> shipmentProviders = lazadaApiManager.getShipmentProviders(lazadaShopInfo);
            return shipmentProviders;
        } catch (ApiException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Transactional
    @Override
    public String changeOrderStatusToReadyToShip(String orderId, String shippingProvider) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(orderId);
        if (lazadaOrderInfo == null) {
            return null;
        }
        LazadaShopInfo lazadaShopInfo = lazadaOrderInfo.getLazadaShopInfo();
        List<LazadaOrderItemsInfo> lazadaOrderItemsInfoList = lazadaOrderItemsInfoDao.findAllByLazadaOrderInfo(lazadaOrderInfo);

        LazadaApiManager lazadaApiManager = LazadaApiManager.getInstance();
        try {
            List<PackedByMarketplaceOrderItemBean> packageInfoList = lazadaApiManager.setStatusToPackedByMarketplace(lazadaShopInfo, lazadaOrderItemsInfoList, shippingProvider);
            if (packageInfoList.isEmpty()) {
                return null;
            }
            String trackingNumber = packageInfoList.get(0).getTracking_number();
            lazadaOrderInfo.setOverseasExpressNumber(trackingNumber);
            lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
            String result = lazadaApiManager.setStatusToReadyToShip(lazadaShopInfo, lazadaOrderItemsInfoList, shippingProvider, trackingNumber);
            if (result.equals("Update status succeed?true")) {
                lazadaOrderInfo.setOrderStatus(OrderBean.Status.RTS.getStatus());
                lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
            }
            System.out.println("setStatusToReadyToShip_result:" + result);
            return result;
        } catch (ApiException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    @Transactional
    public void manualSyncLazadaOrderInfo(String beginTime, String endTime) {
        System.out.println("-------------> manualSyncLazadaOrderInfo task begin <-------------");
        long methodBeginTime = System.currentTimeMillis();

        System.out.println("开始时间：" + beginTime);
        System.out.println("结束时间：" + endTime);

        syncLazadaOrderInfo(beginTime, endTime);

        long methodEndTime = System.currentTimeMillis();
        System.out.println("manualSyncLazadaOrderInfo cost time " + (methodEndTime - methodBeginTime) / 1000 + " second");
    }

    @Override
    @Transactional
    public LazadaOrderInfo syncLazadaOrderInfo(String orderId) {
        System.out.println("-------------> syncLazadaOrderInfo task begin <-------------");
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(orderId);
        if (null == lazadaOrderInfo) {
            System.out.println("syncLazadaOrderInfo:null == lazadaOrderInfo");
            return null;
        }
        LazadaShopInfo shopInfo = lazadaOrderInfo.getLazadaShopInfo();
        Long lazadaOrderId = lazadaOrderInfo.getLazadaOrderId();
        try {
            OrderBean orderBean = LazadaApiManager.getInstance().getOrder(shopInfo, lazadaOrderId);
            if (null == orderBean) {
                System.out.println("syncLazadaOrderInfo:null == orderBean");
                return null;
            }
            lazadaOrderInfo.setOrderStatus(orderBean.getStatuses().get(0));
            lazadaOrderInfo.setRemarks(orderBean.getRemarks());
            if (!TextUtils.isEmpty(orderBean.getUpdated_at())) {
                try {
                    lazadaOrderInfo.setLastUpdateTime(DateUtils.lazadaResponseDateStrToSqlDate(orderBean.getUpdated_at()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
            if (lazadaOrderInfo.getPurchaseOrderInfoSet().isEmpty()) {
                // 获取item数据
                List<OrderItemBean> orderItemBeanList = LazadaApiManager.getInstance().getOrderItems(shopInfo, lazadaOrderId);
                if (!orderItemBeanList.isEmpty()) {
                    //移除旧的OrderItem数据
                    lazadaOrderItemsInfoDao.deleteByLazadaOrderInfo(lazadaOrderInfo.getId());
                    for (OrderItemBean orderItemBean : orderItemBeanList) {
                        lazadaOrderItemsInfoDao.save(new LazadaOrderItemsInfo(lazadaOrderInfo, orderItemBean));
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return lazadaOrderInfo;
    }

    @Override
    @Transactional
    public boolean updateLazadaOrderInfoOverseasExpressPrice(String id, float overseasExpressPrice) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(id);
        if (lazadaOrderInfo == null) {
            return false;
        }
        lazadaOrderInfo.setOverseasExpressPrice(overseasExpressPrice);
        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
        return true;
    }

    /**
     * 同步Lazada服务器数据
     *
     * @param beginTime
     * @param endTime
     */
    public void syncLazadaOrderInfo(String beginTime, String endTime) {
        LazadaApiManager lazadaApiManager = LazadaApiManager.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            beginTime = DateUtils.dateToISO8601DateFormatStr(simpleDateFormat.parse(beginTime).getTime());
            endTime = DateUtils.dateToISO8601DateFormatStr(simpleDateFormat.parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        List<LazadaShopInfo> lazadaShopInfoList = lazadaShopInfoDao.findAllByOrderByShopNameAsc();
        for (LazadaShopInfo lazadaShopInfo : lazadaShopInfoList) {
            if (TextUtils.isEmpty(lazadaShopInfo.getAccessToken())) {
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 店铺名称：" + lazadaShopInfo.getShopName() + " 未含有AccessToken");
                continue;
            }
            System.out.println("初始化商店API(店铺id：" + lazadaShopInfo.getId() + " | 店铺名称：" + lazadaShopInfo.getShopName());
            lazadaApiManager.initClient(lazadaShopInfo);

            try {
                List<OrderBean> orderList = lazadaApiManager.getOrders(null, beginTime, endTime);
                System.out.println("店铺id：" + lazadaShopInfo.getId() + " 获取全部状态的订单总数为" + orderList.size());
                for (OrderBean order : orderList) {
                    //先从数据库中根据lazada平台的orderId查询是否数据库中包含该数据
                    LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findByLazadaOrderId(order.getOrder_id());
                    if (lazadaOrderInfo != null) {
                        //更新订单信息（状态、备注、UpdatedAt）
                        lazadaOrderInfo.setOrderStatus(order.getStatuses().get(0));
                        lazadaOrderInfo.setLazadaOrderRemarks(order.getRemarks());
                        if (!TextUtils.isEmpty(order.getUpdated_at())) {
                            try {
                                lazadaOrderInfo.setLastUpdateTime(DateUtils.lazadaResponseDateStrToSqlDate(order.getUpdated_at()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (lazadaOrderInfo.getExchangeRate() == null) {
                            //补充汇率信息
                            ExchangeRate exchangeRate = exchangeRateDao.findFirstByCountryCodeOrderByDateDesc(lazadaOrderInfo.getLazadaShopInfo().getCountryCode());
                            if (exchangeRate != null) {
                                lazadaOrderInfo.setExchangeRate(exchangeRate);
                            }
                        }
                        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
                        //TODO 更新订单数据
                       /* List<OrderItemBean> orderItemList = lazadaApiManager.getOrderItems(order.getOrder_id());
                        for (OrderItemBean orderItem : orderItemList) {

                        }*/
                    } else {
                        //如果不存在订单信息，则保存订单信息，并且获取详细信息
                        lazadaOrderInfo = new LazadaOrderInfo(lazadaShopInfo, order);
                        ExchangeRate exchangeRate = exchangeRateDao.findFirstByCountryCodeOrderByDateDesc(lazadaOrderInfo.getLazadaShopInfo().getCountryCode());
                        if (exchangeRate != null) {
                            lazadaOrderInfo.setExchangeRate(exchangeRate);
                        }
                        lazadaOrderInfo = lazadaOrderInfoDao.save(lazadaOrderInfo);

                        List<OrderItemBean> orderItemList = lazadaApiManager.getOrderItems(order.getOrder_id());
                        for (OrderItemBean orderItem : orderItemList) {
                            lazadaOrderItemsInfoDao.save(new LazadaOrderItemsInfo(lazadaOrderInfo, orderItem));
                        }
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }
    }

    @Override
    public Set<LazadaOrderInfo> getByOrderExpressNumber(String orderExpressNumber) {
        Set<LazadaOrderInfo> lazadaOrderInfoSet = new HashSet<>();
        List<PurchaseOrderInfo> purchaseOrderInfoList = purchaseOrderInfoDao.findAllByOrderExpressNumber(orderExpressNumber);
        for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfoList) {
            lazadaOrderInfoSet.add(purchaseOrderInfo.getLazadaOrderInfo());
        }
        return lazadaOrderInfoSet;
    }

    @Override
    @Transactional
    public boolean updateLazadaOrderInfoIsDelivery(String id, boolean isDelivery) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(id);
        if (lazadaOrderInfo == null) {
            return false;
        }
        lazadaOrderInfo.setDelivery(isDelivery);
        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
        return true;
    }

    @Override
    @Transactional
    public boolean updateLazadaOrderInfoRemark(String id, String remark) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(id);
        if (lazadaOrderInfo == null) {
            return false;
        }
        lazadaOrderInfo.setRemarks(remark);
        lazadaOrderInfoDao.saveAndFlush(lazadaOrderInfo);
        return true;
    }

    @Override
    public List<LazadaOrderInfo> getOrderDeliveryStatusIsFalse() {
        return lazadaOrderInfoDao.findAllByOverseasExpressNumberIsNotNullAndDeliveryIsFalseOrderByCreateTimeAsc();
    }

    @Override
    public List<LazadaOrderInfo> getByEmail(String email, String orderStatus, String beginTime, String endTime) {
        java.sql.Timestamp begin = new Timestamp(Long.valueOf(beginTime));
        java.sql.Timestamp end = new Timestamp(Long.valueOf(endTime));

        if (TextUtils.isEmpty(orderStatus)) {
            return lazadaOrderInfoDao.findAllByLazadaShopInfoEmailAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(email, begin, end);
        } else {
            return lazadaOrderInfoDao.findAllByLazadaShopInfoEmailAndOrderStatusAndCreateTimeBetweenOrderByLazadaShopInfoAscCreateTimeDesc(email, orderStatus, begin, end);
        }
    }

}
