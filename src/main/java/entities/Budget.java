package entities;

import java.util.*;

/**
 * A budget tracking the amount of money a user is willing to spend on a certain area of their life. A budget
 * classifies items related to that area of life into categories. For each category, the budget stores the items of that
 * category.
 *
 * A budget is associated with the length of time for which the budget is active and a limit on the amount on money that
 * can be spent on items in the budget.
 */
public abstract class Budget {
    private Map<String, Map<String, Item>> budget;
    private int maxSpend;
    private int timeSpan;

    /**
     * Construct a new budget with the given list of categories of items, limit on spending, and time period for the
     * limit applies.
     *
     * @param categories an array containing the names of the categories of items in this budget
     * @param maxSpend the maximum amount of money that can be spent on items in this budget in timeSpan days
     * @param timeSpan the amount of time, in days, for which the budget is active and the limit on spending given by
     *                 maxSpend applies
     */
    public Budget(String[] categories, int maxSpend, int timeSpan) {
        budget = new HashMap<>();
        for (String category : categories) {
            budget.put(category, new HashMap<>());
        }

        this.maxSpend = maxSpend;
        this.timeSpan = timeSpan;
    }

    /**
     * Return the maximum amount of money that can be spent on items in this budget.
     *
     * @return the maximum amount of money that can be spent on items in this budget
     */
    public int getMaxSpend() {
        return maxSpend;
    }

    /**
     * Set the maximum amount of money that can be spent on items in this budget to the given value.
     *
     * @param maxSpend the maximum amount on money that can be spent on items in this budget
     */
    public void setMaxSpend(int maxSpend) {
        this.maxSpend = maxSpend;
    }

    /**
     * Return the amount of time, in days, for which this budget is active.
     *
     * @return the amount of time, in days, for which this budget is active
     */
    public int getTimeSpan() {
        return timeSpan;
    }

    /**
     * Set the amount of time, in days, for which this budget is active to the given value.
     *
     * @param timeSpan the amount of time, in days, for which this budget is active
     */
    public void setTimeSpan(int timeSpan) {
        this.timeSpan = timeSpan;
    }

    /**
     * Add the given item of the given category to the budget if the item is not in the budget and if adding the item
     * would not result in the budget's total cost exceeding maxSpend.
     *
     * @param category the category of the item
     * @param item     the item to be added
     * @return whether the given item was added
     */
    public boolean addItem(String category, Item item) {
        // If adding item to this budget would result in this budget's total cost exceeding maxSpend, do not add item
        if ((this.getTotalCost() + item.getQuantity() * item.getCost() <= this.maxSpend)
                && budget.containsKey(category)
                && !budget.get(category).containsKey(item.getName())) {
            budget.get(category).put(item.getName(), item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the item with the given name and of the given category.
     *
     * @param category the category of the item
     * @param item     the name of the item
     * @return the item with the given name and of the given category, or null if the given category or item is not in
     *         this budget
     */
    public Item getItem(String category, String item) {
        if (budget.containsKey(category) && budget.get(category).containsKey(item)) {
            return budget.get(category).get(item);
        } else {
            return null;
        }
    }

    /**
     * Remove the item with the given name and of the given category from this budget.
     *
     * @param category the category of the item
     * @param item     the name of the item to be removed
     * @return whether the given item was removed
     */
    public boolean removeItem(String category, String item) {
        if (budget.containsKey(category) && budget.get(category).containsKey(item)) {
            budget.get(category).remove(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the number of items of the given category in this budget.
     *
     * @param category a category of item in this budget
     * @return the number of items of the given category in this budget, or -1 if the given category is not in this
     *         budget
     */
    public int getNumItems(String category) {
        if (budget.containsKey(category)) {
            return budget.get(category).keySet().size();
        } else {
            return -1;
        }
    }

    /**
     * Return the total cost of all items in this budget.
     *
     * @return the total cost of all items contained in this budget
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (String category : budget.keySet()) {
            for (String item : budget.get(category).keySet()) {
                totalCost += budget.get(category).get(item).getCost();
            }
        }
        return totalCost;
    }

    /**
     * Return a mapping from category to the total cost of all the items of the category as a percentage of the total
     * cost of all items of all categories in the budget.
     *
     * @return a mapping from category to the total cost of all the items of the category as a percentage of the total
     *         cost of all items of all categories in the budget
     */
    public HashMap<String, Double> getPercentages() {
        HashMap<String, Double> percentages = new HashMap<>();
        double totalCost = getTotalCost();

        for (String category : budget.keySet()) {
            double categoryCost = 0; // the total cost of the items of the category
            for (String item : budget.get(category).keySet()) {
                categoryCost += budget.get(category).get(item).getCost();
            }
            percentages.put(category, categoryCost / totalCost);
        }
        return percentages;
    }
}
