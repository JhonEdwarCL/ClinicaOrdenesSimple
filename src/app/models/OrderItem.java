package app.models;

public class OrderItem {

    private int itemNumber;
    private ItemType type;
    private String name;
    private double cost;
    private int quantity;
    private boolean requiresSpecialist;
    private String specialistId;

    public OrderItem(int itemNumber,
                     ItemType type,
                     String name,
                     double cost,
                     int quantity,
                     boolean requiresSpecialist,
                     String specialistId) {

        this.itemNumber = itemNumber;
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.requiresSpecialist = requiresSpecialist;
        this.specialistId = specialistId;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public ItemType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isRequiresSpecialist() {
        return requiresSpecialist;
    }

    public String getSpecialistId() {
        return specialistId;
    }
}