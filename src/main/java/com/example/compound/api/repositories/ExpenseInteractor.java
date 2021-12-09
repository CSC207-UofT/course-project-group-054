package com.example.compound.api.repositories;

import com.example.compound.api.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseInteractor extends JpaRepository<Expense, Integer> {
}
