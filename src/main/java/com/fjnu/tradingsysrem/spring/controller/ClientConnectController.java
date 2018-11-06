package com.fjnu.tradingsysrem.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LCC on 2018/3/30.
 */
@RestController
public class ClientConnectController {

    @GetMapping("/clientconnect/")
    public ResponseEntity<String> clientConnect() {
        return ResponseEntity.ok().body("Connect successful");
    }
}
