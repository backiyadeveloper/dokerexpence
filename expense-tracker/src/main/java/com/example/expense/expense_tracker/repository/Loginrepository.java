package com.example.expense.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense.expense_tracker.model.Login;

public interface Loginrepository extends JpaRepository<Login, Integer>{
  boolean existsByUsername(String username);
  //boolean existsByUsernameAndPassword(String username,String password);
  Login findByUsernameAndPassword(String username,String password);
}
