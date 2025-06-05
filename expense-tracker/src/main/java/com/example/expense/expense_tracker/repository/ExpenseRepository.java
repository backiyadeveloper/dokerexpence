package com.example.expense.expense_tracker.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.expense.expense_tracker.model.Expense;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	boolean existsByUserIdAndCategoryId(int userId, int categoryId);
    
	List<Expense> findByUserId(int userId);
	
	void deleteByUserIdAndCategoryId(int userId, int categoryId);

	 @Query("SELECT e FROM Expense e WHERE e.userId = :userid AND e.date BETWEEN :startDate AND :endDate")
	    List<Expense> findAllByDateRange(@Param("userid") int userid, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


	 @Query("SELECT c.type, SUM(e.amount) AS total FROM Expense e JOIN Category c ON e.categoryId = c.categoryid AND e.userId = c.userid WHERE e.userId = :userid GROUP BY c.type")
	 List<Object[]> getCategoryReport(@Param("userid") int userid);


	List<Expense> findByUserIdAndCategoryId(int userId, int categoryId);

}
