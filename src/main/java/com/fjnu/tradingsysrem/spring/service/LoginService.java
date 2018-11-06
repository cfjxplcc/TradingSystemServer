package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.PersonInfo;

/**
 * Created by LCC on 2018/3/11.
 */
public interface LoginService {

    PersonInfo login(String loginName, String password);
}
