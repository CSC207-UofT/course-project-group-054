package com.example.compound.entities;

import javax.persistence.*;

@Entity
public class Test {
    @Id
//    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test() {}

    public Test(String data) {
        this.data = data;
    }
}
