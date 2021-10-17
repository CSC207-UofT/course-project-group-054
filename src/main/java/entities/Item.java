package entities;

/**
 * An item for use with the Budget class with a category, name, cost in dollars and quantity.
 */
public class Item {
    private final String category;
    private final String name;
    private final double cost;
    private final int quantity;

    /**
     * Construct a new item with the given category, name, cost, and quantity.
     *
     * @param category the category of this item
     * @param name     the name of this item
     * @param cost     the cost of this item (in dollars)
     * @param quantity the quantity of this item
     */
    public Item(String category, String name, double cost, int quantity) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
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

    public Expense toExpense(String payerUUID, Group group) {
        return new Expense(name, quantity * cost, payerUUID, group.getGroupMembers(), category);
    }
}
