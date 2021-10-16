package entities;

import java.util.Objects;

public class Item {
    private String category;
    private String name;
    private double cost;
    private int quantity;

    public Item(String category, String name, double cost, int quantity) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
