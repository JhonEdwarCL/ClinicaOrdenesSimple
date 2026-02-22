package app.services;

import app.exceptions.BusinessException;
import app.models.ItemType;
import app.models.Order;
import app.models.OrderItem;
import app.ports.OrderPort;

import java.util.HashSet;
import java.util.Set;

public class OrderService {

    private final OrderPort orderPort;

    public OrderService(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    public void createOrder(Order order) {

        validateOrderNumber(order);
        validateItemsNotEmpty(order);
        validateItemName(order);
        validateDiagnosticRule(order);
        validateUniqueItems(order);
        validateSpecialist(order);
        validateCostAndQuantity(order);

        orderPort.save(order);
    }

    private void validateOrderNumber(Order order) {

        if (order.getOrderNumber() == null || order.getOrderNumber().isEmpty()) {
            throw new BusinessException("El número de orden es obligatorio");
        }

        if (order.getOrderNumber().length() > 6) {
            throw new BusinessException("El número de orden no puede tener más de 6 dígitos");
        }

        if (orderPort.existsByOrderNumber(order.getOrderNumber())) {
            throw new BusinessException("El número de orden ya existe");
        }
    }

    private void validateItemsNotEmpty(Order order) {

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new BusinessException("La orden debe tener al menos un ítem");
        }
    }

    private void validateItemName(Order order) {

        for (OrderItem item : order.getItems()) {

            String name = item.getName();

            if (name == null || name.trim().isEmpty()) {
                throw new BusinessException(
                        "El nombre del ítem es obligatorio");
            }

            if (name.matches("\\d+")) {
                throw new BusinessException(
                        "El nombre del ítem no puede ser solo números");
            }

            if (name.length() < 3) {
                throw new BusinessException(
                        "El nombre del ítem debe tener al menos 3 caracteres");
            }
        }
    }

    private void validateDiagnosticRule(Order order) {

        boolean hasDiagnostic = order.getItems().stream()
                .anyMatch(i -> i.getType() == ItemType.AYUDA_DIAGNOSTICA);

        boolean hasOther = order.getItems().stream()
                .anyMatch(i -> i.getType() == ItemType.MEDICAMENTO
                        || i.getType() == ItemType.PROCEDIMIENTO);

        if (hasDiagnostic && hasOther) {
            throw new BusinessException(
                    "No se puede mezclar ayuda diagnóstica con medicamentos o procedimientos");
        }
    }

    private void validateUniqueItems(Order order) {

        Set<Integer> itemNumbers = new HashSet<>();

        for (OrderItem item : order.getItems()) {

            if (!itemNumbers.add(item.getItemNumber())) {
                throw new BusinessException(
                        "No puede haber ítems repetidos dentro de la misma orden");
            }
        }
    }

    private void validateSpecialist(Order order) {

        for (OrderItem item : order.getItems()) {

            if (item.isRequiresSpecialist()
                    && (item.getSpecialistId() == null
                    || item.getSpecialistId().isEmpty())) {

                throw new BusinessException(
                        "Debe indicar el id del especialista si el ítem lo requiere");
            }
        }
    }

    private void validateCostAndQuantity(Order order) {

        for (OrderItem item : order.getItems()) {

            if (item.getCost() <= 0) {
                throw new BusinessException(
                        "El costo debe ser mayor a cero");
            }

            if (item.getQuantity() <= 0) {
                throw new BusinessException(
                        "La cantidad debe ser mayor a cero");
            }
        }
    }
}