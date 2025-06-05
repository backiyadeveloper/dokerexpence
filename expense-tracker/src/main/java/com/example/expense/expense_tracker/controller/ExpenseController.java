package com.example.expense.expense_tracker.controller;

import com.example.expense.expense_tracker.util.JwtUtil;

import java.io.Console;
import java.security.PublicKey;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.expense_tracker.model.AuthResponse;
import com.example.expense.expense_tracker.model.Category;
import com.example.expense.expense_tracker.model.Expense;

import com.example.expense.expense_tracker.model.Income;
import com.example.expense.expense_tracker.model.Login;
import com.example.expense.expense_tracker.service.CustomException;
import com.example.expense.expense_tracker.service.ExpenseService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
//import com.example.jwtproject.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.Status;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ExpenseController {

	@Autowired
	private ExpenseService service;

	@Autowired
	private ObjectMapper objectmapper;

	private final JwtUtil jwtUtil;

	public ExpenseController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("user")
	public ResponseEntity<?> addUser(@RequestBody Login logDetails) {
		try {
			service.addlogdata(logDetails); 
			Map<String,Object> response=new HashMap<String, Object>();
			response.put("status", true);
			response.put("message", "add successfully");
			return ResponseEntity.ok(response); 
		} catch (CustomException e) {
			Map<String,Object> response=new HashMap<String, Object>();
			response.put("status", false);
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response); 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while adding user details");
		}
	}

	@PostMapping("login")
	public ResponseEntity<?> getlogdata(@RequestBody Login logdetails) {
	    try {
	        if (logdetails.getUsername() == null || logdetails.getPassword() == null) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("status", false);
	            response.put("message", "Username and password must be provided.");
	            return ResponseEntity.badRequest().body(response);
	        }

	        Map<String, Object> token = service.getlogdata(logdetails);

	        if (token != null) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("status", true);
	            response.put("message", "Login successful");
	            response.put("data", token); 
	            return ResponseEntity.ok(response);
	        } else {
	            Map<String, Object> response = new HashMap<>();
	            response.put("status", false);
	            response.put("message", "Invalid username or password. Please check your credentials.");
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    } catch (CustomException e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", false);
	        response.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(response);
	    } catch (Exception e) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", false);
	        response.put("message", "An error occurred: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}



	@PostMapping("/adddata")
	public ResponseEntity<?> addIncome(@RequestBody Income income) {
        Map<String, Object> response = new HashMap<>();

		try {
			
			service.addIncome(income);
			response.put("status",true);
			response.put("message", "expense added successfully");
			return ResponseEntity.ok(response);

		} catch (CustomException e) {
			response.put("status",false);
			response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error is occores while adding income" + e);
		}

	}

	@PostMapping("/updateincome")

	public ResponseEntity<?> updateIncome(@RequestBody Income income) {
		 Map<String, Object> response = new HashMap<>();
        
		try {
			service.updateIncome(income);
			 response.put("status", true);
	         response.put("message","income updated succesfully");
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			 response.put("status", false);
	         response.put("message",e.getMessage());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error isoccores while adding income" + e);
		}

	}

	@PostMapping("/getincome")
	public ResponseEntity<?> getincome() {
		List<Income> incomes = service.getincome();
		return ResponseEntity.ok(incomes);
	}

	@PostMapping("/getincomebyid")
	public ResponseEntity<?> getincombyid(@RequestBody String userid) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(userid);
            int userId = jsonNode.get("userid").asInt();
			List<Income> getuserIncomes = service.getincomebyid(userId);
			if (!getuserIncomes.isEmpty()) {
				return ResponseEntity.ok(getuserIncomes);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("empty set");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error isoccores while adding income" + e);
		}
	}

	@PostMapping("/addexpensecategory")
	public ResponseEntity<?> addExpenseCategory(@RequestBody Map<String, Object> request) {
		Map<String,Object> response=new HashMap<String, Object>();

		try {
			Expense expense = objectmapper.convertValue(request.get("expense"), Expense.class);
			Category category = objectmapper.convertValue(request.get("category"), Category.class);
			service.addExpense(expense, category);
			response.put("status", "success");
			response.put("message", "expense and category added successfully");
			return ResponseEntity.ok(response);
		} catch (CustomException e) {
			response.put("status", "faild");
			response.put("message",e.getMessage());
			return ResponseEntity.badRequest().body(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error iccores while adding expense and catetgories" + e);
		}
	}

	@PostMapping("/updatecategoryexpense")
	public ResponseEntity<?> update(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

		try {
			Expense expense = objectmapper.convertValue(request.get("expense"), Expense.class);
			Category category = objectmapper.convertValue(request.get("category"), Category.class);
			service.updateExpense(expense);
			service.updateCategory(category);
			response.put("status", true);
            response.put("message", "updated successfull");
            return ResponseEntity.ok(response);
			
		} catch (CustomException e) {
			response.put("status", false);
            response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error occured on update expenses and category");
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deletexpence(@RequestBody Expense request) {
        Map<String, Object> response = new HashMap<>();

		try {
			//int categoryid = request.get("categoryid");
			service.deleteCategory(request.getUserId(), request.getCategoryId());
			service.deleteExpence(request.getUserId(), request.getCategoryId());
			response.put("status", true);
            response.put("message", "deleted successfull");
            return ResponseEntity.ok(response);
		} catch (CustomException e) {
			response.put("status", false);
            response.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("error occured on delete expenses and category");

		}
	}

	@PostMapping("/getExpenses")
	public ResponseEntity<?> getExpense(@RequestBody String userid) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(userid);
            int userId = jsonNode.get("userid").asInt();
			List<Map<String, Object>> expense = service.getAllExpense(userId);
			if (!expense.isEmpty()) {
				return ResponseEntity.ok(expense);
			}

			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("empty set");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error Occure");
		}

	}

	@PostMapping("/getcategory")
	public ResponseEntity<?> getByCategory(@RequestBody Category category) {
		try {
			
			List<Map<String, Object>> expenses = service.getByCategory(category.getType(),category.getUserid());
			if (!expenses.isEmpty()) {
				return ResponseEntity.ok(expenses);
			} else {
				Map<String, String> noContentResponse = new HashMap<>();
				noContentResponse.put("message", "No expenses found for this category.");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(noContentResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/bydaterange")
	public ResponseEntity<?> getExpensesByDateRange(@RequestBody Map<String, Object> request) {
		try {
            int userId = (int) request.get("userId");
            String start= (String) request.get("startDate");
            Date startDate=Date.valueOf(start);
            String end = (String) request.get("endDate");
            Date endDate=Date.valueOf(end);
			List<Expense> daterangExpenses = service.getExpensesByDateRange(userId, startDate, endDate);
			if (!daterangExpenses.isEmpty()) {
				return ResponseEntity.ok(daterangExpenses);
			} else {
				Map<String, String> daterange = new HashMap<>();
				daterange.put("message", "No expenses found for this category.");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(daterange);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occurred while getting expenses by date range");
		}
	}

	@PostMapping("/getcategoryreport")

	public ResponseEntity<?> getCategoryReport(@RequestBody Expense response) {
		try {
			Map<String, String> report = service.getcategoryreport(response.getUserId());

			if (report == null) {
				System.out.println("User not present.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
			} else if (report.isEmpty()) {
				System.out.println("Report is empty.");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No data found for this user’s report.");
			} else {
				System.out.println("Report is not empty.");
				return ResponseEntity.ok(report);
			}
		} catch (Exception e) {
			System.out.println("Error occurred while getting the category report.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while retrieving the category report.");
		}
	}

	@PostMapping("getdistinctname")
	public ResponseEntity<?> getdistinctname(@RequestBody Expense userid) {
		try {
			return ResponseEntity.ok(service.getdistinctname(userid.getUserId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error occuredError while retriving th Theme category");
		}

	}
}
