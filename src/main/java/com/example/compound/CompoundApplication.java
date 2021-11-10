package com.example.compound;

import com.example.compound.data.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompoundApplication {

    public static void main(String[] args) {
        Data.initializeData();
        SpringApplication.run(CompoundApplication.class, args);
    }

}
