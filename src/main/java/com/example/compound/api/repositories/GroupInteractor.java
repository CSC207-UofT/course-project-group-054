package com.example.compound.api.repositories;

import com.example.compound.api.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupInteractor extends JpaRepository<Group, Integer> {
}
