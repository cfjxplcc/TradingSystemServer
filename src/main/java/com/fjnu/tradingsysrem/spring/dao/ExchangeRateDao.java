package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by LCC on 2018/5/10.
 */
@RepositoryDefinition(domainClass = ExchangeRate.class, idClass = String.class)
public interface ExchangeRateDao {

    ExchangeRate save(ExchangeRate exchangeRate);

    void saveAndFlush(ExchangeRate exchangeRate);

    ExchangeRate findById(String id);

    void deleteById(String id);

    ExchangeRate findByCountryCodeAndDate(String countryCode, java.sql.Date date);

    List<ExchangeRate> findAll();

    List<ExchangeRate> findTop10ByCountryCodeOrderByDateDesc(String countryCode);

    ExchangeRate findFirstByCountryCodeOrderByDateDesc(String countryCode);

}
