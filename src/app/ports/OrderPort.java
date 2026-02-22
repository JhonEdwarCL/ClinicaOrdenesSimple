package app.ports;

import app.models.Order;

public interface OrderPort {

    void save(Order order);

    boolean existsByOrderNumber(String orderNumber);
}