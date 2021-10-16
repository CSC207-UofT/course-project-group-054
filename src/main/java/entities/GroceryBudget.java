package entities;

public class GroceryBudget extends Budget {
    public GroceryBudget(int maxSpend, int timeSpan) {
        super(new String[]{"Fruits", "Vegetables", "Meat"}, maxSpend, timeSpan);
    }
}
