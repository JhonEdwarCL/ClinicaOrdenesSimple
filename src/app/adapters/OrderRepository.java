package app.adapters;

import app.models.Order;
import app.ports.OrderPort;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements OrderPort {

    private List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        orders.add(order);
    }

    @Override
    public boolean existsByOrderNumber(String orderNumber) {
        return orders.stream()
                .anyMatch(o -> o.getOrderNumber().equals(orderNumber));
    }
}