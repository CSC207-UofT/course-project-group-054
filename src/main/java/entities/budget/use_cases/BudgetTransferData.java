package entities.budget.use_cases;

import java.util.HashMap;
import java.util.Map;

public class BudgetTransferData { // TODO: What should this be called?
    private String name;
    private Map<String, Map<String, ItemTransferData>> budget;
    private double maxSpend;

    public BudgetTransferData(String name, double maxSpend) {
        this.name = name;
        this.maxSpend = maxSpend;
        this.budget = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Map<String, ItemTransferData>> getBudget() {
        return budget;
    }

    public double getMaxSpend() {
        return maxSpend;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(Map<String, Map<String, ItemTransferData>> budget) {
        this.budget = budget;
    }

    public void setMaxSpend(double maxSpend) {
        this.maxSpend = maxSpend;
    }
}
