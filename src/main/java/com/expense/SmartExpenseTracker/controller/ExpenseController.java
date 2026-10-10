package com.expense.SmartExpenseTracker.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.expense.SmartExpenseTracker.entity.Expense;
import com.expense.SmartExpenseTracker.entity.User;
import com.expense.SmartExpenseTracker.repository.UserRepository;
import com.expense.SmartExpenseTracker.service.ExpenseService;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public ExpenseController(
            ExpenseService expenseService,
            UserRepository userRepository) {

        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/add-expense")
    public String showAddExpensePage(Model model) {

        model.addAttribute("expense", new Expense());

        return "add-expense";
    }

    @PostMapping("/add-expense")
    public String addExpense(
            @ModelAttribute("expense") Expense expense,
            Principal principal) {

        User user = getLoggedInUser(principal);

        expense.setUser(user);

        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        expenseService.addExpense(expense);

        return "redirect:/expenses";
    }

    @GetMapping("/expenses")
    public String viewExpenses(
            @RequestParam(required = false) String keyword,
            Model model,
            Principal principal) {

        User user = getLoggedInUser(principal);

        List<Expense> expenses =
                expenseService.searchExpenses(user, keyword);

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        model.addAttribute("expenses", expenses);
        model.addAttribute("total", total);
        model.addAttribute("keyword", keyword);

        return "view-expenses";
    }

    @GetMapping("/edit-expense/{id}")
    public String showEditExpensePage(
            @PathVariable Long id,
            Model model,
            Principal principal) {

        Expense expense = expenseService.getExpenseById(id);

        if (expense == null) {
            return "redirect:/expenses";
        }

        if (!expense.getUser().getUsername()
                .equals(principal.getName())) {

            return "redirect:/expenses";
        }

        model.addAttribute("expense", expense);

        return "edit-expense";
    }

    @PostMapping("/edit-expense")
    public String updateExpense(
            @ModelAttribute("expense") Expense expense,
            Principal principal) {

        Expense existing =
                expenseService.getExpenseById(expense.getId());

        if (existing == null) {
            return "redirect:/expenses";
        }

        if (!existing.getUser().getUsername()
                .equals(principal.getName())) {

            return "redirect:/expenses";
        }

        existing.setTitle(expense.getTitle());
        existing.setAmount(expense.getAmount());
        existing.setCategory(expense.getCategory());
        existing.setDate(expense.getDate());
        existing.setDescription(expense.getDescription());

        expenseService.updateExpense(existing);

        return "redirect:/expenses";
    }

    @GetMapping("/delete-expense/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            Principal principal) {

        Expense expense =
                expenseService.getExpenseById(id);

        if (expense == null) {
            return "redirect:/expenses";
        }

        if (!expense.getUser().getUsername()
                .equals(principal.getName())) {

            return "redirect:/expenses";
        }

        expenseService.deleteExpense(id);

        return "redirect:/expenses";
    }

    private User getLoggedInUser(Principal principal) {

        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}