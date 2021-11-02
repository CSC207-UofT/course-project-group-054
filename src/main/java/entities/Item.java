package entities;

import java.beans.*;

/**
 * An item for use with the Budget class with a category, name, cost in dollars and quantity.
 */
public class Item {
    private final String category;
    private String name;
    private double cost;
    private int quantity;

    private final VetoableChangeSupport observableVetoable;
    private final PropertyChangeSupport observableProperty;

    /**
     * Construct a new item with the given category, name, cost, and quantity.
     *  @param category the category of this item
     * @param name     the name of this item
     * @param cost     the cost of this item (in dollars)
     * @param quantity the quantity of this item
     */
    public Item(String category, String name, double cost, int quantity) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.observableVetoable = new VetoableChangeSupport(this);
        this.observableProperty = new PropertyChangeSupport(this);
    }

    public double getCost() {
        return cost;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Expense toExpense(Group group) {
        return new Expense(name, quantity * cost, group.getGroupMembers());
    }

    public void addObserver(VetoableChangeListener observer) {
        observableVetoable.addVetoableChangeListener(observer);
    }

    public void addObserver(PropertyChangeListener observer) {
        observableProperty.addPropertyChangeListener(observer);
    }

    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        observableProperty.firePropertyChange("name", oldName, newName);
    }

    public boolean setCost(double newCost) {
        double oldCost = this.cost;
        try {
            observableVetoable.fireVetoableChange("cost", oldCost, newCost);
        } catch (PropertyVetoException e) {
            return false;
        }
        this.cost = newCost;
        return true;
    }

    public boolean setQuantity(int newQuantity) {
        int oldQuantity = this.quantity;
        try {
            observableVetoable.fireVetoableChange("quantity", oldQuantity, newQuantity);
        } catch (PropertyVetoException e) {
            return false;
        }
        this.quantity = newQuantity;
        return true;
    }
}
