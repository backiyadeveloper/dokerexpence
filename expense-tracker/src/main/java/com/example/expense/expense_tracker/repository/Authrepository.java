package com.example.expense.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.example.expense.expense_tracker.model.AuthResponse;

import jakarta.transaction.Transactional;
@EnableJpaRepositories
public interface Authrepository extends JpaRepository<AuthResponse, Integer> {
    Boolean existsByUserid(int userid); 
   AuthResponse findByUserid(int userid);
    
}

