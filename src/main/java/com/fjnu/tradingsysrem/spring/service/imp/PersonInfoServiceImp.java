package com.fjnu.tradingsysrem.spring.service.imp;

import com.fjnu.tradingsysrem.spring.dao.PersonInfoDao;
import com.fjnu.tradingsysrem.spring.model.PersonInfo;
import com.fjnu.tradingsysrem.spring.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LCC on 2018/3/9.
 */
@Service
@Transactional(readOnly = true)
public class PersonInfoServiceImp implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Transactional
    @Override
    public String save(PersonInfo personInfo) {
        return personInfoDao.save(personInfo).getId();
    }

    @Override
    public PersonInfo get(String id) {
        return personInfoDao.getById(id);
    }

    @Override
    public List<PersonInfo> listAll() {
        return personInfoDao.findAllByOrderByLoginName();
    }

    @Override
    public boolean isLoginNameExist(String loginName) {
        List<PersonInfo> personInfoList = personInfoDao.getByLoginName(loginName);
        if (personInfoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void update(String id, PersonInfo personInfo) {
        personInfo.setId(id);
        personInfoDao.saveAndFlush(personInfo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        PersonInfo personInfo = personInfoDao.getById(id);
        personInfoDao.delete(personInfo);
    }
}
