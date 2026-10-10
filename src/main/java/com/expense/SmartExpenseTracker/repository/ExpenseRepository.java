package com.expense.SmartExpenseTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expense.SmartExpenseTracker.entity.Expense;
import com.expense.SmartExpenseTracker.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses of a particular user
    List<Expense> findByUser(User user);

    // Search expenses by title
    List<Expense> findByUserAndTitleContainingIgnoreCase(
            User user, String title);

    // Get expenses by category
    List<Expense> findByUserAndCategory(
            User user, String category);
}