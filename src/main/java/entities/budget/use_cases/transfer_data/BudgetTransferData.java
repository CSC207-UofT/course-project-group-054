package entities.budget.use_cases.transfer_data;

import entities.budget.entities.Budget;
import entities.budget.entities.Item;

import java.util.HashMap;
import java.util.Map;

public class BudgetTransferData { // TODO: What should this be called?
    private String BUID;
    private String name;
    private Map<String, Map<String, ItemTransferData>> budget;
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

    public Map<String, Map<String, ItemTransferData>> convertBudget(Budget budget) {
        Map<String, Map<String, ItemTransferData>> map = new HashMap<>();
        for (String categoryName : budget.getCategories()) {
            Map<String, ItemTransferData> category = new HashMap<>();
            Map<String, Item> items = budget.getItemsOfCategory(categoryName);
            for (String itemName : items.keySet()) {
                category.put(itemName, new ItemTransferData(items.get(itemName)));
            }
            map.put(categoryName, category);
        }
        return map;
    }

    public String getBUID() {
        return BUID;
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

    public void setBUID(String BUID) {
        this.BUID = BUID;
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

    public Budget toBudget() { // TODO: Objects of this class will be used by outer layers. This method returns an Entity object. Hypothetically, objects in outer layers could access Entities. Can this method be here?
        String[] categories = this.budget.keySet().toArray(new String[0]);
        Budget budget = new Budget(BUID, name, categories, maxSpend, 0);
        for (String category : categories) {
            Map<String, ItemTransferData> categoryItemData = this.budget.get(category);
            for (String itemName : categoryItemData.keySet()) {
                budget.addItem(category, categoryItemData.get(itemName).toItem());
            }
        }
        return budget;
    }
}
