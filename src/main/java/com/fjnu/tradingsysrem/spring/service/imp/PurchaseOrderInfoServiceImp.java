package com.fjnu.tradingsysrem.spring.service.imp;

import com.fjnu.tradingsysrem.spring.dao.LazadaOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaShopInfoDao;
import com.fjnu.tradingsysrem.spring.dao.PurchaseOrderInfoDao;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.PurchaseOrderInfo;
import com.fjnu.tradingsysrem.spring.service.PurchaseOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCC on 2018/3/28.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderInfoServiceImp implements PurchaseOrderInfoService {

    @Autowired
    private PurchaseOrderInfoDao purchaseOrderInfoDao;
    @Autowired
    private LazadaOrderInfoDao lazadaOrderInfoDao;
    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;

    @Override
    @Transactional
    public String save(PurchaseOrderInfo purchaseOrderInfo) {
        return purchaseOrderInfoDao.save(purchaseOrderInfo).getId();
    }

    @Override
    public PurchaseOrderInfo get(String id) {
        return purchaseOrderInfoDao.findById(id);
    }

    @Override
    public List<PurchaseOrderInfo> listAll() {
        return purchaseOrderInfoDao.findAll();
    }

    @Override
    public List<PurchaseOrderInfo> getByLazadaOrderInfo(String lazadaOrderInfoId) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(lazadaOrderInfoId);
        return purchaseOrderInfoDao.findAllByLazadaOrderInfo(lazadaOrderInfo);
    }

    @Override
    @Transactional
    public void update(String id, PurchaseOrderInfo purchaseOrderInfo) {
        purchaseOrderInfo.setId(id);
        purchaseOrderInfoDao.saveAndFlush(purchaseOrderInfo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        purchaseOrderInfoDao.deleteById(id);
    }

    @Override
    public List<PurchaseOrderInfo> getByLazadaShopInfo(String lazadaShopInfoId, String beginTime, String endTime) {
        LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.getById(lazadaShopInfoId);
        if (lazadaShopInfo != null) {
            java.sql.Timestamp begin = new Timestamp(Long.valueOf(beginTime));
            java.sql.Timestamp end = new Timestamp(Long.valueOf(endTime));
            List<LazadaOrderInfo> lazadaOrderInfoList = lazadaOrderInfoDao.findAllByLazadaShopInfoAndCreateTimeBetweenOrderByCreateTime(lazadaShopInfo, begin, end);
            return purchaseOrderInfoDao.findAllByLazadaOrderInfoIn(lazadaOrderInfoList);
        }
        return new ArrayList<>();
    }
}
