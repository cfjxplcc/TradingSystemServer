package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.model.ExchangeRate;
import com.fjnu.tradingsysrem.spring.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by LCC on 2018/5/10.
 */
@RestController
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @PostMapping("/exchangerate/")
    public ResponseEntity<ExchangeRate> save(@RequestBody ExchangeRate exchangeRate) {
        return ResponseEntity.ok().body(exchangeRateService.save(exchangeRate));
    }

    @GetMapping("/exchangerate/")
    public ResponseEntity<List<ExchangeRate>> getByCountry(@RequestParam("countrycode") String countryCode) {
        return ResponseEntity.ok().body(exchangeRateService.getByCountry(countryCode));
    }

    @GetMapping("/exchangerate/getCountryMap")
    public ResponseEntity<Map<String, String>> getCountryMap() {
        return ResponseEntity.ok().body(exchangeRateService.getCountry());
    }

}
