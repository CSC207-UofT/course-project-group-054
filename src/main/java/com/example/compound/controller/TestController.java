package com.example.compound.controller;

import com.example.compound.entities.Test;
import com.example.compound.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Qualifier("testRepo")
    @Autowired
    TestRepo repository;

    @PostMapping("/test")
    public int create(@RequestBody Map<String, Object> request) {
        String data = (String) request.get("data");
        Test tst = new Test(data);
        repository.save(tst);
        return 1;
    }
}
