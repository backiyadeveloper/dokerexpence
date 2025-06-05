package com.example.expense.expense_tracker.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.jcp.xml.dsig.internal.MacOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.expense.expense_tracker.model.AuthResponse;
import com.example.expense.expense_tracker.model.Category;
import com.example.expense.expense_tracker.model.Expense;
import com.example.expense.expense_tracker.model.Income;
import com.example.expense.expense_tracker.model.Login;
import com.example.expense.expense_tracker.repository.Authrepository;
import com.example.expense.expense_tracker.repository.CategoryRepository;
import com.example.expense.expense_tracker.repository.ExpenseRepository;
import com.example.expense.expense_tracker.repository.IncomeRepository;
import com.example.expense.expense_tracker.repository.Loginrepository;
import com.example.expense.expense_tracker.util.JwtUtil;

import jakarta.transaction.Transactional;
import logerpac.logger;

@Service
public class ExpenseService {
	@Autowired
	private IncomeRepository incomeRepo;
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private CategoryRepository categoryrepository;
	@Autowired
	private Loginrepository  Loginrepository;
	@Autowired
	private Authrepository Authrepository;
	@Autowired
	private JwtUtil jwtUtil;
    
	public Logger logger=LoggerFactory.getLogger(ExpenseService.class);
	
	@Transactional

	public Map<String, Object> getlogdata(Login logdetails) throws Exception {
	    // Check if user exists by username and password
	    Login logdata = Loginrepository.findByUsernameAndPassword(logdetails.getUsername(), logdetails.getPassword());
	    
	    if (logdata == null) {
	        throw new CustomException("User not found or invalid credentials.");
	    }
	    
	    // Check if token exists and is valid
	    AuthResponse gettoken = Authrepository.findByUserid(logdata.getUserid());
	    Map<String, Object> response = new HashMap<>();

	    if (!Authrepository.existsByUserid(logdata.getUserid())) {
	        // First time login, generate new token
	        String token = jwtUtil.generateToken(logdetails.getUsername(), logdata.getUserid());
	        AuthResponse autherization = new AuthResponse();
	        autherization.setToken(token);
	        autherization.setUserid(logdata.getUserid());
	        Authrepository.save(autherization);
	        logger.info("First token generation");
	        response.put("token", token);
	        response.put("username", logdata.getUsername());
	        response.put("userid", logdata.getUserid());
	        return response;
	    } else if (jwtUtil.TokenExpired(gettoken.getToken())) {
	        // Token expired, generate a new one
	        AuthResponse expiredToken = Authrepository.findByUserid(logdata.getUserid());
	        if (expiredToken != null) {
	            logger.info("Deleting expired token for user: " + logdata.getUserid());
	            Authrepository.delete(expiredToken);
	            Authrepository.flush();
	        }
	        
	        String newToken = jwtUtil.generateToken(logdetails.getUsername(), logdata.getUserid());
	        AuthResponse newAuthorization = new AuthResponse();
	        newAuthorization.setToken(newToken);
	        newAuthorization.setUserid(logdata.getUserid());
	        Authrepository.save(newAuthorization);
	        logger.info("New token generation");
	        response.put("token", newToken);
	        response.put("username", logdata.getUsername());
	        response.put("userid", logdata.getUserid());
	        return response;	    } else {
	        // Return the existing valid token
	        logger.info("Token is valid: " + gettoken.getToken());
	        response.put("token", gettoken.getToken());
	        response.put("username", logdata.getUsername());
	        response.put("userid", logdata.getUserid());
	        return response;	    }
	}


	public void addlogdata(Login logdata) throws CustomException {
		System.out.println(logdata.getUsername());
		 String usernameRegex = "^[A-Za-z]{3,16}$";
	        String username = logdata.getUsername();	      
	        if (username.matches(usernameRegex)) {}
		if(logdata.getUsername()==null || logdata.getPassword()==null) {
			throw new CustomException("give a data for all fields");
		}
		else if(!username.matches(usernameRegex)) {
			throw new CustomException("give a valid username with minimum three charecters");
		}
		else if( !Loginrepository.existsByUsername(logdata.getUsername())) {
			Loginrepository.save(logdata);
		}else {
			throw new CustomException("user already exist");
		}
	}
	@Transactional
	public void addIncome(Income income) throws CustomException {
		if (income.getUserId() <= 0 || income.getIncome() <= 0) {
			throw new CustomException("give a valid user id and income");
		} else if (!incomeRepo.existsByUserId(income.getUserId())) {
			incomeRepo.save(income);
		} else {
			throw new CustomException("income is already added");
		}
	}

	public void updateIncome(Income income) throws CustomException {

		if (income.getUserId() <= 0 || income.getIncome() <= 0) {
			throw new CustomException("give a valid user id or income");
		}
		if (incomeRepo.existsById(income.getUserId())) {
			incomeRepo.save(income);
		} else {
			throw new CustomException(" not updated");
		}

	}
 
	public List<Income> getincome(){
		return incomeRepo.findAll();
		
	}
	
	public List<Income> getincomebyid(int userid){
		return incomeRepo.findByUserId(userid);
	}
	
	public void addExpense(Expense expense, Category category) throws CustomException {

		if (expense.getUserId() <= 0 || expense.getCategoryId() <= 0 || expense.getAmount() <= 0
				|| expense.getDate() == null || expense.getDescription() == null || category.getCategoryid() <= 0
						&& category.getName() == null && category.getType() == null && category.getUserid() <= 0) {
			throw new CustomException("give a positive values and give a data for all fields");
		}

		else if (!expenseRepository.existsByUserIdAndCategoryId(expense.getUserId(), expense.getCategoryId())
				&& !categoryrepository.existsByUseridAndCategoryid(category.getUserid(), category.getCategoryid())) {

			expenseRepository.save(expense);
			categoryrepository.save(category);

		} else {
			throw new CustomException("user id and category id is already exist");
		}

	}

	public void updateExpense(Expense expense) throws CustomException {

		List<Expense> existingExpense = expenseRepository.findByUserIdAndCategoryId(expense.getUserId(),
				expense.getCategoryId());
		if (!existingExpense.isEmpty()) {
			for (Expense existCategory : existingExpense) {
				existCategory.setAmount(expense.getAmount());
				existCategory.setDate(expense.getDate());
				existCategory.setDescription(expense.getDescription());
				expenseRepository.save(existCategory);
			}
		} else {
			throw new CustomException("not updated");
		}
	}

	public void updateCategory(Category category) throws CustomException {
		List<Category> existCategory = categoryrepository.findByUseridAndCategoryid(category.getUserid(),
				category.getCategoryid());

		if (!existCategory.isEmpty()) {
			for (Category existingCategory : existCategory) {
				existingCategory.setName(category.getName());
				existingCategory.setType(category.getType());
				categoryrepository.saveAll(existCategory);
			}
		} else {
			throw new CustomException("not updated");
		}
	}

	public List<Map<String, Object>> getAllExpense(int userid) {

		List<Map<String, Object>> response = new ArrayList<>();
		List<Expense> expenses = expenseRepository.findByUserId(userid);
		List<Category> categories = categoryrepository.findByUserid(userid);
		Map<Integer, Category> categoryMap = new LinkedHashMap<>();

		for (Category category : categories) {
			categoryMap.put(category.getId(), category);
		}

		for (Expense expense : expenses) {
			Category category = categoryMap.get(expense.getId());
			if (category != null) {
				Map<String, Object> resultData = new LinkedHashMap<>();
				resultData.put("id", expense.getId());
				resultData.put("userId", expense.getUserId());
				resultData.put("categoryId", expense.getCategoryId());
				resultData.put("amount", expense.getAmount());
				resultData.put("categoryName", category.getName());
				resultData.put("description", expense.getDescription());
				resultData.put("categoryType", category.getType());
				resultData.put("date", expense.getDate());
				response.add(resultData);
			}

		}

		return response;
	}

	@Transactional
	public void deleteExpence(int userid, int categoryid) throws CustomException {
		if (expenseRepository.existsByUserIdAndCategoryId(userid, categoryid)) {
			expenseRepository.deleteByUserIdAndCategoryId(userid, categoryid);

		} else {
			throw new CustomException("expense does deleted");
		}
	}

	@Transactional
	public void deleteCategory(int categoryid, int userid) throws CustomException {
		if (categoryrepository.existsByUseridAndCategoryid(categoryid, userid)) {
			categoryrepository.deleteByCategoryidAndUserid(userid, categoryid);
		} else {
			throw new CustomException("expense does deleted");
		}
	}

	public List<Map<String, Object>> getByCategory(String category,int userid) throws CustomException {
	    List<Category> categoryList = categoryrepository.findAllByNameAndUserid(category,userid);
	    List<Map<String, Object>> resultList = new ArrayList<>();

	    for (Category categoryItem : categoryList) {
	        int userId = categoryItem.getUserid();
	        int categoryId = categoryItem.getCategoryid();
	        String categoryName = categoryItem.getName();
	        List<Expense> expenses = expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
	        
	        if (!expenses.isEmpty()) {
	            for (Expense expense : expenses) {
	                Map<String, Object> resultMap = new HashMap<>();
	                resultMap.put("id", userId); 
	                resultMap.put("amount", expense.getAmount());
	                resultMap.put("description", expense.getDescription());
	                resultMap.put("category", categoryName);
	                resultMap.put("date", expense.getDate());

	               
	                resultList.add(resultMap);
	            }
	          
	        }  
	    }
	    
	    return resultList; 
	}


	public List<Expense> getExpensesByDateRange(Integer userid, Date startDate, Date endDate) {
		return expenseRepository.findAllByDateRange(userid,startDate, endDate);
	}

	public Map<String, String> getcategoryreport(int userid) {
		if(incomeRepo.existsByUserId(userid)) {
			double income = incomeRepo.getIncomeByUserId(userid);
			List<Object[]> report = expenseRepository.getCategoryReport(userid);
			Map<String, String> result = new LinkedHashMap<String, String>();
			for (Object[] tempObjects : report) {
				String type = (String) tempObjects[0];
				double amount = (double) tempObjects[1];
				String percentage = (int) ((amount / income) * 100) + "%";
				result.put(type, percentage);
			}
			return result;
		}else {
			System.out.println("given user is not present");
	        return null; 
		}
		

	}
	public List<String> getdistinctname(int userid){
		return categoryrepository.findDistinctNames(userid);
	}
}
