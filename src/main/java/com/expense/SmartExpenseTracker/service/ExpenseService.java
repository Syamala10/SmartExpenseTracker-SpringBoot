package com.expense.SmartExpenseTracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.expense.SmartExpenseTracker.entity.Expense;
import com.expense.SmartExpenseTracker.entity.User;
import com.expense.SmartExpenseTracker.repository.ExpenseRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getExpensesByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    public List<Expense> searchExpenses(User user, String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return expenseRepository.findByUser(user);
        }

        return expenseRepository
                .findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByCategory(
            User user, String category) {

        return expenseRepository.findByUserAndCategory(
                user, category);
    }
}