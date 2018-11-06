package com.fjnu.tradingsysrem.spring.service;

import com.fjnu.tradingsysrem.spring.model.PersonInfo;

import java.util.List;

/**
 * Created by LCC on 2018/3/9.
 */
public interface PersonInfoService {

    String save(PersonInfo personInfo);

    PersonInfo get(String id);

    List<PersonInfo> listAll();

    void update(String id, PersonInfo personInfo);

    void delete(String id);

    boolean isLoginNameExist(String loginName);

}
