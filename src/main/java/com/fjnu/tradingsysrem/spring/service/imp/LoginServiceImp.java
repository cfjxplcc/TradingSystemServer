package com.fjnu.tradingsysrem.spring.service.imp;

import com.fjnu.tradingsysrem.spring.dao.PersonInfoDao;
import com.fjnu.tradingsysrem.spring.model.PersonInfo;
import com.fjnu.tradingsysrem.spring.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LCC on 2018/3/11.
 */
@Service
@Transactional(readOnly = true)
public class LoginServiceImp implements LoginService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public PersonInfo login(String loginName, String password) {
        return personInfoDao.getByLoginNameAndLoginPassword(loginName, password);
    }
}
