package com.expense.SmartExpenseTracker.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.expense.SmartExpenseTracker.entity.Expense;
import com.expense.SmartExpenseTracker.entity.User;
import com.expense.SmartExpenseTracker.repository.UserRepository;
import com.expense.SmartExpenseTracker.service.ExpenseService;
import com.expense.SmartExpenseTracker.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;
    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public AuthController(
            UserService userService,
            ExpenseService expenseService,
            UserRepository userRepository) {

        this.userService = userService;
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            Model model) {

        if (userService.usernameExists(user.getUsername())) {

            model.addAttribute(
                    "error",
                    "Username already exists!");

            return "register";
        }

        if (userService.emailExists(user.getEmail())) {

            model.addAttribute(
                    "error",
                    "Email already exists!");

            return "register";
        }

        userService.registerUser(user);

        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Expense> expenses =
                expenseService.getExpensesByUser(user);

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        int expenseCount = expenses.size();

        model.addAttribute("username", username);
        model.addAttribute("total", total);
        model.addAttribute("expenseCount", expenseCount);
        model.addAttribute("expenses", expenses);

        return "dashboard";
    }

    @GetMapping("/reports")
    public String reports(
            Authentication authentication,
            Model model) {

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Expense> expenses =
                expenseService.getExpensesByUser(user);

        double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        int expenseCount = expenses.size();

        model.addAttribute("total", total);
        model.addAttribute("expenseCount", expenseCount);

        java.util.Map<String, Double> categoryTotals =
                new java.util.LinkedHashMap<>();

        for (Expense expense : expenses) {

            String category = expense.getCategory();

            double amount = expense.getAmount();

            categoryTotals.put(
                    category,
                    categoryTotals.getOrDefault(category, 0.0)
                            + amount);
        }

        model.addAttribute(
                "categoryTotals",
                categoryTotals);

        return "reports";
    }
}