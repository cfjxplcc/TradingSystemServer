package com.fjnu.tradingsysrem.spring.service.lazada.imp;

import com.fjnu.tradingsysrem.spring.dao.LazadaOrderInfoDao;
import com.fjnu.tradingsysrem.spring.dao.LazadaOrderItemsInfoDao;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderInfo;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaOrderItemsInfo;
import com.fjnu.tradingsysrem.spring.service.lazada.LazadaOrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LCC on 2018/3/27.
 */
@Service
@Transactional(readOnly = true)
public class LazadaOrderItemsServiceImp implements LazadaOrderItemsService {

    @Autowired
    private LazadaOrderItemsInfoDao lazadaOrderItemsInfoDao;
    @Autowired
    private LazadaOrderInfoDao lazadaOrderInfoDao;

    @Override
    @Transactional
    public String save(LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        return lazadaOrderItemsInfoDao.save(lazadaOrderItemsInfo).getId();
    }

    @Override
    public LazadaOrderItemsInfo get(String id) {
        return lazadaOrderItemsInfoDao.findById(id);
    }

    @Override
    public List<LazadaOrderItemsInfo> listAll() {
        return lazadaOrderItemsInfoDao.findAll();
    }

    @Override
    public List<LazadaOrderItemsInfo> getByLazadaOrderInfo(String lazadaOrderInfoId) {
        LazadaOrderInfo lazadaOrderInfo = lazadaOrderInfoDao.findById(lazadaOrderInfoId);
        return lazadaOrderItemsInfoDao.findAllByLazadaOrderInfo(lazadaOrderInfo);
    }

    @Override
    @Transactional
    public void update(String id, LazadaOrderItemsInfo lazadaOrderItemsInfo) {
        lazadaOrderItemsInfo.setId(id);
        lazadaOrderItemsInfoDao.saveAndFlush(lazadaOrderItemsInfo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        lazadaOrderItemsInfoDao.deleteById(id);
    }
}
