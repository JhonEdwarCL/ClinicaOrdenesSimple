package app.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private String orderNumber;
    private String patientId;
    private String doctorId;
    private LocalDateTime creationDate;
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderNumber, String patientId, String doctorId) {
        this.orderNumber = orderNumber;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.creationDate = LocalDateTime.now();
    }
    public double calculateTotal() {
    double total = 0;
    for (OrderItem item : items) {
        total += item.getCost() * item.getQuantity();
    }
    return total;
    }
    public void printOrder() {

    System.out.println("\n================ ORDEN MEDICA =================");
    System.out.println("Número de orden: " + orderNumber);
    System.out.println("Paciente ID: " + patientId);
    System.out.println("Médico ID: " + doctorId);
    System.out.println("Fecha de creación: " + creationDate);

    System.out.println("\n----------- ITEMS ------------");

    for (OrderItem item : items) {
        System.out.println("\nItem #" + item.getItemNumber());
        System.out.println("Tipo: " + item.getType());
        System.out.println("Nombre: " + item.getName());
        System.out.println("Cantidad: " + item.getQuantity());
        System.out.println("Costo: " + item.getCost());
        System.out.println("Requiere especialista: " + 
            (item.isRequiresSpecialist() ? "Sí" : "No"));
    }

    System.out.println("\n--------------------------------");
    System.out.println("TOTAL ORDEN: " + calculateTotal());
    System.out.println("================================\n");
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}