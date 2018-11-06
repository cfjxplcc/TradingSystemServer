package com.fjnu.tradingsysrem.spring.service.imp;

import com.fjnu.tradingsysrem.spring.dao.LazadaShopInfoDao;
import com.fjnu.tradingsysrem.spring.model.lazada.LazadaShopInfo;
import com.fjnu.tradingsysrem.spring.service.LazadaShopInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LCC on 2018/3/17.
 */
@Service
@Transactional(readOnly = true)
public class LazadaShopInfoImp implements LazadaShopInfoService {

    @Autowired
    private LazadaShopInfoDao lazadaShopInfoDao;

    @Override
    @Transactional
    public LazadaShopInfo save(LazadaShopInfo shopInfo) {
        return lazadaShopInfoDao.save(shopInfo);
    }

    @Override
    public LazadaShopInfo get(String id) {
        return lazadaShopInfoDao.getById(id);
    }

    @Override
    @Transactional
    public void update(String id, LazadaShopInfo lazadaShopInfo) {
        lazadaShopInfo.setId(id);
        lazadaShopInfoDao.saveAndFlush(lazadaShopInfo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        LazadaShopInfo lazadaShopInfo = lazadaShopInfoDao.getById(id);
        lazadaShopInfoDao.delete(lazadaShopInfo);
    }

    @Override
    public List<LazadaShopInfo> getAll() {
        return lazadaShopInfoDao.findAllByOrderByShopNameAsc();
    }


}
