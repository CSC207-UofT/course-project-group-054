package com.example.compound.repositories;

import com.example.compound.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Test, Long> {
}
