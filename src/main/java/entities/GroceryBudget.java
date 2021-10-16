package entities;

/**
 * A Budget for groceries. The categories are "Fruits", "Vegetables", and "Meat".
 */
public class GroceryBudget extends Budget {
    /**
     * Construct a new GroceryBudget with the given limit on time span.
     *
     * @param maxSpend the maximum amount of money that can be spent on items in this budget in timeSpan days
     * @param timeSpan the amount of time, in days, for which the budget is active and the limit on spending given by
     *                 maxSpend applies
     */
    public GroceryBudget(int maxSpend, int timeSpan) {
        super(new String[]{"Fruits", "Vegetables", "Meat"}, maxSpend, timeSpan);
    }
}
