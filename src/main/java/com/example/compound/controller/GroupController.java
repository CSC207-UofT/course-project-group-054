//package com.example.compound.controller;
//
//import com.example.compound.entities.Group;
//import com.example.compound.api.repositories.GroupRepository;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
////@RestController
////@RequestMapping("/api/group")
//public class GroupController {
//
////    @PostMapping("/create")
////    public int createGroup(@RequestBody Map<String, Object> request) {
////        String name = (String) request.get("name");
////        String description = (String) request.get("description");
////        List<String> members = new ArrayList<>();
////        members.add("1"); // TODO replace 1 with current user uuid
////
////        try {
//////            return service.newGroup(name, description, members);
////            return 0;
////        } catch (Exception e) {
////            System.out.println(e);
////            return 0;
////        }
////
//////        return service.newGroup(name, description, members);
////    }
//
//    @PostMapping("/create-group")
//    public int createGroup(@RequestBody Map<String, Object> request) {
//        try {
////            Group newGroup = new Group(
////                    "Toronto",
////                    "A group for managing expenses in Toronto trip.",
////                    new ArrayList<>(),
////                    new ArrayList<>()
////            );
//
//            Group newGroup = new Group();
//            System.out.println(newGroup);
//            System.out.println(repository);
//            repository.save(newGroup);
//
//
//            return 1;
//        } catch (Exception ignored) {}
//
//        return 0;
//    }
//
//    @GetMapping("/all")
//    public List<Group> getGroupByUid() {
//        System.out.println("I'm here");
//        return repository.findAll();
//    }
//}
