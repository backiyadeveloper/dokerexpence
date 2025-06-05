package com.example.expense.expense_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.example.expense.expense_tracker.model.Category;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	boolean existsByUseridAndCategoryid(int userid, int categoryid);
	List<Category> findByUserid(int userid);

	@Transactional
	void deleteByCategoryidAndUserid(int userid, int categoryid);

	List<Category> findAllByNameAndUserid(String name,int userid);

	List<Category> findByUseridAndCategoryid(int userid, int categoryid);
    
	@Query("SELECT DISTINCT c.name FROM Category c where c.userid= :userid")
    List<String> findDistinctNames(@Param("userid") int userid);

}
