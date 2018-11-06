package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.ExchangeRate;

import java.util.List;
import java.util.Map;

/**
 * Created by LCC on 2018/5/10.
 */
public interface ExchangeRateService {

    Map<String, String> getCountry();

    ExchangeRate save(ExchangeRate exchangeRate);

    void update(String id, ExchangeRate exchangeRate);

    List<ExchangeRate> getAll();

    ExchangeRate getById(String id);

    List<ExchangeRate> getByDate(String beginDate, String endDate);

    List<ExchangeRate> getByCountry(String countryCode);

    void delete(String id);
}
