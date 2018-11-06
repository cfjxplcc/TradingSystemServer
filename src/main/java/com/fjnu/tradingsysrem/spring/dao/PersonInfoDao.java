package com.fjnu.tradingsysrem.spring.dao;

import com.fjnu.tradingsysrem.spring.model.PersonInfo;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created by LCC on 2018/3/8.
 */
@RepositoryDefinition(domainClass = PersonInfo.class, idClass = String.class)
public interface PersonInfoDao {

    PersonInfo save(PersonInfo personInfo);

    PersonInfo getById(String id);

    List<PersonInfo> findAllByOrderByLoginName();

    void saveAndFlush(PersonInfo personInfo);

    void delete(PersonInfo personInfo);

    PersonInfo getByLoginNameAndLoginPassword(String loginName, String password);

    List<PersonInfo> getByLoginName(String loginName);
}
