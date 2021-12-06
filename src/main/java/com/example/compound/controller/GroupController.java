package com.example.compound.controller;

import com.example.compound.services.GroupService;
import com.example.compound.services.GroupServiceImpl;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    GroupService service;

    @PostMapping("/create")
    public int createGroup(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        List<String> members = new ArrayList<>();
        members.add("1"); // TODO replace 1 with current user uuid

        return service.newGroup(name, description, members);
    }

    @GetMapping
    public void getGroupByUid() {

    }
}
