package com.example.compound.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String data;

    @ElementCollection
    private List<String> members;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private List<String> getMembers() {
        return members;
    }

    public Test() {}

    public Test(String data) {
        this.data = data;
        this.members = new ArrayList<>();
    }

    public Test(String data, List<String> members) {
        this.data = data;
        this.members = members;
    }

}
