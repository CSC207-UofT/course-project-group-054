package com.example.compound.api.repositories;

import com.example.compound.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test, Integer> {
}
