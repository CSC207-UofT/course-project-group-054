//package com.example.compound.services;
//
//import com.example.compound.entities.Group;
////import com.example.compound.api.repositories.GroupRepository;
//import com.example.compound.api.repositories.GroupRepository;
//import com.example.compound.use_cases.GroupManager;
//import com.example.compound.use_cases.gateways.GroupRepositoryGateway;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
////@Service
//public class GroupServiceImpl implements GroupService {
////    @Qualifier("groupRepositoryImpl")
////    @Autowired
////    GroupRepository repository;
//
//    private GroupManager manager = new GroupManager();
//
//    @Override
//    public int newGroup(String name, String description, List<String> members) {
//        try {
////            Group newGroup = manager.createGroupObject(name, description, new ArrayList<>(), new ArrayList<>());
//
//            List<Integer> members2 = new ArrayList();
//            members2.add(1);
//
//            Group newGroup = new Group(name, description, members2, new ArrayList<>());
//            System.out.println(newGroup.getMembers());
//
////            System.out.println(newGroup.getGUID());
//            repository.save(newGroup);
//            return 1;
//        } catch (Exception e) {
//            System.out.println(e);
//            return 0;
//        }
//    }
//
//    @Override
//    public boolean editGroup() {
//        return false;
//    }
//
//    @Override
//    public List<String> getExpenses(int num) {
//        return null;
//    }
//
//    @Override
//    public List<String> getUsers() {
//        return null;
//    }
//}
