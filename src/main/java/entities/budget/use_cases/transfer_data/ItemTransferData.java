package entities.budget.use_cases.transfer_data;

import entities.budget.entities.Item;

public class ItemTransferData {
    private String IUID;
    private String category;
    private String name;
    private double cost;
    private int quantity;

    public ItemTransferData(String IUID, String category, String name, double cost, int quantity) {
        this.IUID = IUID;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
    }

    public ItemTransferData(Item item) {
        this.IUID = item.getIUID();
        this.category = item.getCategory();
        this.name = item.getName();
        this.cost = item.getCost();
        this.quantity = item.getQuantity();
    }

    public String getIUID() {
        return IUID;
    }

    public void setIUID(String IUID) {
        this.IUID = IUID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item toItem() {
        return new Item(IUID, category, name, cost, quantity);
    }
}
