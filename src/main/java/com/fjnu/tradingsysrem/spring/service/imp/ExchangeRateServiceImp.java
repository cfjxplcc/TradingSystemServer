package com.fjnu.tradingsysrem.spring.service.imp;

import com.fjnu.tradingsysrem.spring.dao.ExchangeRateDao;
import com.fjnu.tradingsysrem.spring.model.Country;
import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import com.fjnu.tradingsysrem.spring.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LCC on 2018/5/10.
 */
@Service
@Transactional(readOnly = true)
public class ExchangeRateServiceImp implements ExchangeRateService {

    @Autowired
    private ExchangeRateDao exchangeRateDao;

    @Override
    public Map<String, String> getCountry() {
        Map map = new HashMap<String, String>();
        for (Country country : Country.values()) {
            map.put(country.getCode(), country.getName());
        }
        return map;
    }

    @Override
    @Transactional
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return exchangeRateDao.save(exchangeRate);
    }

    @Override
    @Transactional
    public void update(String id, ExchangeRate exchangeRate) {
        exchangeRate.setId(id);
        exchangeRateDao.saveAndFlush(exchangeRate);
    }

    @Override
    public List<ExchangeRate> getAll() {
        return exchangeRateDao.findAll();
    }

    @Override
    public ExchangeRate getById(String id) {
        return exchangeRateDao.findById(id);
    }

    @Override
    public List<ExchangeRate> getByDate(String beginDate, String endDate) {
        return null;
    }

    @Override
    public List<ExchangeRate> getByCountry(String countryCode) {
        return exchangeRateDao.findTop10ByCountryCodeOrderByDateDesc(countryCode);
    }

    @Override
    @Transactional
    public void delete(String id) {

    }
}
