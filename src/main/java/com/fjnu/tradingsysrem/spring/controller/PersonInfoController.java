package com.fjnu.tradingsysrem.spring.controller;

import com.fjnu.tradingsysrem.spring.model.PersonInfo;
import com.fjnu.tradingsysrem.spring.service.PersonInfoService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LCC on 2018/3/9.
 */
@RestController
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    @PostMapping("/personinfo")
    public ResponseEntity<?> save(@RequestBody PersonInfo personInfo) {
        if (personInfoService.isLoginNameExist(personInfo.getLoginName())) {
            return ResponseEntity.ok("LoginName is existed");
        }
        String personInfoId = personInfoService.save(personInfo);
        return ResponseEntity.ok().body("New PersonInfo has been saved with ID:" + personInfoId);
       /* Map<String, Object> map = new HashMap<>();
        map.put("message", "test 1");
        map.put("message2", personInfo);
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);*/
    }

    @GetMapping("/personinfo/{id}")
    public ResponseEntity<PersonInfo> get(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(personInfoService.get(id));
    }

    @GetMapping("/personinfo")
    public ResponseEntity<List<PersonInfo>> listAll() {
        return ResponseEntity.ok().body(personInfoService.listAll());
    }

    @PutMapping("/personinfo/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody PersonInfo personInfo) {
        personInfoService.update(id, personInfo);
        return ResponseEntity.ok().body("update successful");
    }

    @DeleteMapping("/personinfo/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        personInfoService.delete(id);
        return ResponseEntity.ok().body("delete successful");
    }
}
