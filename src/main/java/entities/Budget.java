package entities;

import java.util.HashMap;

public abstract class Budget {
    private HashMap<String, HashMap<String, Double>> budget;
    private int maxSpend;
    private int timeSpan;

    /**
     * Construct a new budget with the given list of categories of items, limit on spending, and time period for the
     * limit.
     *
     * @param categories an array containing the names of the categories of items in this budget
     * @param maxSpend the maximum amount of money that can be spent on items in this budget in timeSpan days
     * @param timeSpan the amount of time, in days, for which the limit on spending given by maxSpend applies
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
     * Put the given item as a key associated with the given cost as a value in the sub-mapping associated with the
     * given category of item in this budget. If the item is already in the sub-mapping, return the old cost and replace
     * it with the given cost.
     *
     * @param category the category of item associated with the mapping that is to contain item in this budget
     * @param item     the item with which the cost to be changed is associated
     * @param cost     the new cost to be associated with item
     * @return the cost previously associated with item, or -1.0 if item was associated with null, if budget does not
     *         contain the key category, or if the mapping in budget associated with category does not contain the key
     *         item
     */
    public double putItem(String category, String item, double cost) {
        if (budget.containsKey(category)) {
            Double oldCost = budget.get(category).put(item, cost);
            if (oldCost != null) {
                return oldCost;
            }
        }
        return -1.0;
    }

    public boolean removeItem(String category, String item) {
        if (budget.containsKey(category) && budget.get(category).containsKey(item)) {
            budget.get(category).remove(item);
            return true;
        } else {
            return false;
        }
    }

    public int getMaxSpend() {
        return maxSpend;
    }

    public void setMaxSpend(int maxSpend) {
        this.maxSpend = maxSpend;
    }

    public int getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(int timeSpan) {
        this.timeSpan = timeSpan;
    }

    /**
     * Return the number of items contained in the sub-mapping associated with the given category of item in this
     * budget.
     *
     * @param category a category of item associated with a mapping in this budget
     * @return the number of items contained in the mapping in this budget associated with category
     */
    public int getNumItems(String category) {
        if (budget.containsKey(category)) {
            return budget.get(category).keySet().size();
        } else {
            return -1;
        }
    }

    public double getTotalCost() {
        double totalCost = 0;
        for (String category : budget.keySet()) {
            for (String item : budget.get(category).keySet()) {
                totalCost += budget.get(category).get(item);
            }
        }
        return totalCost;
    }

    /**
     * Return a mapping from category to the total cost of all the items in the category as a percentage of the total
     * cost of all items in the budget over all categories.
     *
     * @return a mapping from category to the total cost of all the items in the category as a percentage of the total
     *         cost of all items in the budget over all categories
     */
    public HashMap<String, Double> getPercentages() {
        HashMap<String, Double> percentages = new HashMap<>();
        double totalCost = getTotalCost();

        for (String category : budget.keySet()) {
            double categoryCost = 0; // the total cost of the items in the sub-mapping associated with category
            for (String item : budget.get(category).keySet()) {
                categoryCost += budget.get(category).get(item);
            }
            percentages.put(category, categoryCost / totalCost);
        }
        return percentages;
    }
}
