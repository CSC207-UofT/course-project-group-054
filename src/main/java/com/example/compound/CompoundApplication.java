package com.example.compound;

import com.example.compound.data.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A class containing the main method, which runs the program using Spring.
 */
@SpringBootApplication
public class CompoundApplication {
    /**
     * Run the program using Spring.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Data data = new Data();
        data.initializeData();
        SpringApplication.run(CompoundApplication.class, args);
    }
}
