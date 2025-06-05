package com.example.expense.expense_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Income")
public class Income {

	@Id
	@Column(name = "userid")
	private int userId;

	@Column(name = "income")
	private double income;

	public Income() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		userId = id;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
}
