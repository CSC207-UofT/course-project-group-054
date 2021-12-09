package com.example.compound.use_cases.transfer_data;

import com.example.compound.entities.Budget;
import com.example.compound.entities.Item;

import java.util.*;

public class BudgetTransferData { // TODO: What should this be called?
    private String BUID;
    private String name;
    private Map<String, ItemTransferData> budget;
    private double maxSpend;

    public BudgetTransferData(String BUID, String name, double maxSpend, Integer[] IUIDs) {
        this.BUID = BUID;
        this.name = name;
        this.maxSpend = maxSpend;
        this.budget = new HashMap<>();
        for (int IUID : IUIDs) {
            budget.put(Integer.toString(IUID), null);
        }
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
        for (String IUID : items.keySet()) {
            map.put(IUID, new ItemTransferData(items.get(IUID)));
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

    public Integer[] getItemIUIDs() {
        List<String> strings = new ArrayList<>(budget.keySet());
        Integer[] items = new Integer[strings.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = Integer.parseInt(strings.get(i));
        }
        return items;
    }

    public void addItem(String IUID, ItemTransferData itemTransferData) {
        budget.put(IUID, itemTransferData);
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
        for (String IUID : this.budget.keySet()) {
            budget.addItem(this.budget.get(IUID).toItem());
        }
        return budget;
    }
}
