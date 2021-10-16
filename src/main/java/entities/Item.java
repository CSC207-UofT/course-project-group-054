package entities;

public class Item {
    private final String category;
    private final String name;
    private final double cost;
    private final int quantity;

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
}
