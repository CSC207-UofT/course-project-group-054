package com.example.compound.api.controller;

import com.example.compound.api.entities.Expense;
import com.example.compound.api.repositories.ExpenseInteractor;
import com.example.compound.api.use_cases.ExpenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Qualifier("expenseInteractor")
    @Autowired
    ExpenseInteractor repository;

    final ExpenseManager manager = new ExpenseManager();

    /**
     * API Endpoint to create an expense
     * @param request JSON object containing information of the new expense
     * @return 1 if the new expense was saved to the database, 0 otherwise
     */
    @PostMapping("/create")
    public Integer createExpense(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            Double amount = (Double) request.get("amount");
            Integer payer = (Integer) request.get("payer");
            Map<String, Double> map = (HashMap<String, Double>) request.get("people");

            System.out.println(map);

            Map<Integer, Double> people = new HashMap<>();
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                Integer uuid = Integer.parseInt(entry.getKey());
                Double amountOwed = entry.getValue();
                people.put(uuid, amountOwed);
            }

            Expense expense = new Expense(title, amount, payer, people);

            repository.save(expense);

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * API Endpoint to return a list of all expenses in the database.
     * @return list of all expenses in the database
     */
    @GetMapping("/all")
    public List<Expense> showAllExpenses() {
        return repository.findAll();
    }


    /**
     * API Endpoint to get an expense from euid
     * @param euid UID of the concerned expense
     * @return JSON object containing details of the expense if it exists, null otherwise
     */
    @GetMapping("/{euid}")
    public Optional<Expense> getById(@PathVariable Integer euid) {
        return repository.findById(euid);
    }


    /**
     * API endpoint to delete an expense from euid
     * @param euid UID of the concerned expense
     * @return 1 if the expense was successfully deleted or does not exist, 0 otherwise
     */
    @GetMapping("/{euid}/delete")
    public Integer deleteById(@PathVariable Integer euid) {
        repository.deleteById(euid);
        return 1;
    }


    /**
     * API endpoint to get a particular attribute on an expense
     * @param euid UID of the concerned expense
     * @return JSON object containing details of the expense attribute
     */
    @GetMapping("/{euid}/{attribute}")
    public Object getAttribute(@PathVariable Integer euid, @PathVariable String attribute) {
        try {
            Expense expense = repository.getById(euid);
            if (attribute.equals("title")) {
                return expense.getTitle();
            } else if (attribute.equals("amount")) {
                return expense.getAmount();
            } else if (attribute.equals("payer")) {
                return expense.getPayer();
            } else if (attribute.equals("people")) {
                return expense.getPeople();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    /**
     * API endpoint to update amounts owed in an expense
     * @param euid UID of the concerned expense
     * @param attribute Attribute to be modified
     * @param request JSON object containing information about the modification
     * @return Updated attribute
     */
    @PostMapping("/{euid}/update")
    public Map<Integer, Double> updatePeople(@PathVariable Integer euid,
                                   @PathVariable String attribute,
                                   @RequestBody Map<String, Object> request) {
        Integer uuid = (Integer) request.get("uuid");
        Double amountPaid = (Double) request.get("amountPaid");
        boolean borrowed = (boolean) request.get("borrowed");

        Expense expense = repository.getById(euid);
        manager.settleExpense(expense, uuid, amountPaid, borrowed);

        repository.save(expense);

        return expense.getPeople();
    }


}
