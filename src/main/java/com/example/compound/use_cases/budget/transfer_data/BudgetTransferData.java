package com.example.compound.use_cases.budget.transfer_data;

import com.example.compound.entities.Budget;
import com.example.compound.entities.Item;

import java.util.HashMap;
import java.util.Map;

public class BudgetTransferData { // TODO: What should this be called?
    private String BUID;
    private String name;
    private Map<String, ItemTransferData> budget;
    private double maxSpend;
    // TODO: eliminate timeSpan?

    public BudgetTransferData(String BUID, String name, double maxSpend) {
        this.BUID = BUID;
        this.name = name;
        this.maxSpend = maxSpend;
        this.budget = new HashMap<>();
    }

    public BudgetTransferData(Budget budget) {
        this.BUID = budget.getBUID();
        this.name = budget.getName();
        this.maxSpend = budget.getMaxSpend();
        this.budget = convertBudget(budget);
    }

    public Map<String, ItemTransferData> convertBudget(Budget budget) {
        Map<String, ItemTransferData> map = new HashMap<>();
        Map<String, Item> items = budget.getItems();
        for (String itemName : items.keySet()) {
            map.put(itemName, new ItemTransferData(items.get(itemName)));
        }
        return map;
    }

    public String getBUID() {
        return BUID;
    }

    public String getName() {
        return name;
    }

    public Map<String, ItemTransferData> getBudget() {
        return budget;
    }

    public double getMaxSpend() {
        return maxSpend;
    }

    public void setBUID(String BUID) {
        this.BUID = BUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(Map<String, ItemTransferData> budget) {
        this.budget = budget;
    }

    public void setMaxSpend(double maxSpend) {
        this.maxSpend = maxSpend;
    }

    public Budget toBudget() { // TODO: Objects of this class will be used by outer layers. This method returns an Entity object. Hypothetically, objects in outer layers could access Entities. Can this method be here?
        Budget budget = new Budget(BUID, name, maxSpend);
        for (String itemName : this.budget.keySet()) {
            budget.addItem(this.budget.get(itemName).toItem());
        }
        return budget;
    }
}
