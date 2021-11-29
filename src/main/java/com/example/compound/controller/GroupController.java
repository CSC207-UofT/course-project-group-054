package com.example.compound.controller;

import com.example.compound.services.GroupService;
import com.example.compound.services.GroupServiceImpl;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    GroupService service;

    @GetMapping("/create")
    public void createGroup() {
        service.newGroup();
    }
}
