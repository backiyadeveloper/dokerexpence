package com.example.expense.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.example.expense.expense_tracker.model.Income;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
public interface IncomeRepository extends JpaRepository<Income, Integer> {

	@Transactional
	boolean existsByUserId(int userId);
	

	
	List<Income> findByUserId(int userId);

	@Query("SELECT i.income FROM Income i WHERE i.userId = :userid")
	double getIncomeByUserId(@Param("userid") int userid);
}
