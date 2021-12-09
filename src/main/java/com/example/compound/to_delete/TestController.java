package com.example.compound.to_delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/test-list")
    public int createList(@RequestBody Map<String, Object> request) {
        String data = (String) request.get("data");
        String user = (String) request.get("user");

        List<String> members = new ArrayList<>();
//        members.add(user);
//        members.add("Tata");

        Test tst = new Test(data, members);
        repository.save(tst);
        return 1;
    }


    @GetMapping("/test/{id}")
    public Optional<Test> getById(@PathVariable Integer id) {
        try {
            Optional<Test> test = repository.findById(id);
            if (test.isPresent()) {
                System.out.println(test.get().getData());
            }
            return test;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
