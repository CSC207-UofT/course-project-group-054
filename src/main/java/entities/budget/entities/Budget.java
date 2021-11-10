package entities.budget.entities;

import entities.Expense;
import entities.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.*;

/**
 * A budget tracking the amount of money a user is willing to spend on a certain area of their life. A budget
 * classifies items related to that area of life into categories. For each category, the budget stores the items of that
 * category.
 *
 * A budget is associated with the length of time for which the budget is active and a limit on the amount on money that
 * can be spent on items in the budget.
 */
public class Budget implements VetoableChangeListener, PropertyChangeListener {
    private final String BUID;
    private String name;
    private final Map<String, Map<String, Item>> budget;
    private double maxSpend;
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
    public Budget(String BUID, String name, String[] categories, double maxSpend, int timeSpan) {
        this.BUID = BUID;
        this.name = name;
        budget = new HashMap<>();
        for (String category : categories) {
            budget.put(category, new HashMap<>());
        }

        this.maxSpend = maxSpend;
        this.timeSpan = timeSpan;
    }

    public String getBUID() {
        return BUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the maximum amount of money that can be spent on items in this budget.
     *
     * @return the maximum amount of money that can be spent on items in this budget
     */
    public double getMaxSpend() {
        return maxSpend;
    }

    /**
     * Set the maximum amount of money that can be spent on items in this budget to the given value.
     *
     * @param maxSpend the maximum amount on money that can be spent on items in this budget
     */
    public void setMaxSpend(double maxSpend) {
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
            item.addObserver((PropertyChangeListener) this); // TODO: Should this be in the Budget class?
            item.addObserver((VetoableChangeListener) this); // TODO: Should this be in the Budget class?
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
     * Change the quantity of the item with the given name and of the given category to the given quantity.
     *
     * @param category    the category of the item
     * @param itemName    the name of the item
     * @param newQuantity the new quantity of the item
     * @return whether the quantity was changed
     */
    public boolean changeQuantity(String category, String itemName, int newQuantity)
            throws NullPointerException {
        Item item = Objects.requireNonNull(getItem(category, itemName));
        return item.setQuantity(newQuantity);
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
                totalCost += budget.get(category).get(item).getCost() * budget.get(category).get(item).getQuantity();
            }
        }
        return totalCost;
    }

    /**
     * Return a mapping from category to the total cost of all the items of the category as a percentage of the total
     * cost of all items of all categories in the budget.
     *
     * @return a mapping from category to the total cost of all the items of the category as a percentage of the total
     *         cost of all items of all categories in the budget, or null if getTotalCost returns 0
     */
    public HashMap<String, Double> getPercentages() {
        HashMap<String, Double> percentages = new HashMap<>();
        double totalCost = getTotalCost();

        if (totalCost == 0) {
            return null;
        }

        for (String category : budget.keySet()) {
            double categoryCost = 0; // the total cost of the items of the category
            for (String item : budget.get(category).keySet()) {
                categoryCost += budget.get(category).get(item).getCost() * budget.get(category).get(item).getQuantity();
            }
            percentages.put(category, categoryCost / totalCost);
        }
        return percentages;
    }

    public List<Expense> toExpenses(Group group) {
        List<Expense> expenses = new ArrayList<>();
        for (String category : budget.keySet()) {
            for (String item : budget.get(category).keySet()) {
                expenses.add(budget.get(category).get(item).toExpense(group));
            }
        }
        return expenses;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        switch (evt.getPropertyName()) { // TODO: Is the presence of this switch statement a code smell? Is it needed?
            case "quantity" -> {
                int newQuantity = (Integer) evt.getNewValue();
                Item item = (Item) evt.getSource();
                if (getTotalCost() + (newQuantity - item.getQuantity()) * item.getCost() > maxSpend) {
                    throw new PropertyVetoException("The requested change would result in this Budget's maxSpend being "
                            + "exceeded", evt);
                }
            }
            case "cost" -> {
                double newCost = (Double) evt.getNewValue();
                Item item = (Item) evt.getSource();
                if (getTotalCost() + item.getQuantity() * (newCost - item.getCost()) > maxSpend) {
                    throw new PropertyVetoException("The requested change would result in this Budget's maxSpend being "
                            + "exceeded", evt);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            Item item = (Item) evt.getSource();
            String category = item.getCategory();
            budget.get(category).remove(item.getName());
            budget.get(category).put((String) evt.getNewValue(), item);
        }
    }
}
