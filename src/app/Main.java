package app;

import app.adapters.OrderRepository;
import app.exceptions.BusinessException;
import app.models.ItemType;
import app.models.Order;
import app.models.OrderItem;
import app.services.OrderService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        OrderRepository repository = new OrderRepository();
        OrderService service = new OrderService(repository);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println("\n===== SISTEMA DE ÓRDENES =====");
            System.out.println("1. Crear nueva orden");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (option) {

                case 1:
                    try {

                        System.out.print("Número de orden: ");
                        String orderNumber = scanner.nextLine();

                        System.out.print("ID del paciente: ");
                        String patientId = scanner.nextLine();

                        System.out.print("ID del médico: ");
                        String doctorId = scanner.nextLine();

                        Order order = new Order(orderNumber, patientId, doctorId);

                        boolean addingItems = true;

                        while (addingItems) {

                            System.out.println("\n--- Agregar Ítem ---");

                            System.out.print("Número del ítem: ");
                            int itemNumber = scanner.nextInt();

                            System.out.println("Tipo (1=MEDICAMENTO, 2=PROCEDIMIENTO, 3=AYUDA_DIAGNOSTICA): ");
                            int typeOption = scanner.nextInt();
                            scanner.nextLine(); // limpiar buffer

                            ItemType type;

                            switch (typeOption) {
                                case 1:
                                    type = ItemType.MEDICAMENTO;
                                    break;
                                case 2:
                                    type = ItemType.PROCEDIMIENTO;
                                    break;
                                case 3:
                                    type = ItemType.AYUDA_DIAGNOSTICA;
                                    break;
                                default:
                                    System.out.println("Tipo inválido.");
                                    continue;
                            }

                            System.out.print("Nombre: ");
                            String name = scanner.nextLine();

                            System.out.print("Costo: ");
                            double cost = scanner.nextDouble();

                            System.out.print("Cantidad: ");
                            int quantity = scanner.nextInt();
                            scanner.nextLine(); // limpiar buffer

                            // 🔥 REQUIERE ESPECIALISTA (si/no en cualquier formato)
                            System.out.print("¿Requiere especialista? (si/no): ");
                            String response = scanner.nextLine().trim().toLowerCase();

                            boolean requiresSpecialist =
                                    response.equals("si") || response.equals("sí");

                            String specialistId = null;

                            if (requiresSpecialist) {
                                System.out.print("ID del especialista: ");
                                specialistId = scanner.nextLine();
                            }

                            OrderItem item = new OrderItem(
                                    itemNumber,
                                    type,
                                    name,
                                    cost,
                                    quantity,
                                    requiresSpecialist,
                                    specialistId
                            );

                            order.addItem(item);

                            // 🔥 AGREGAR OTRO ITEM
                            System.out.print("¿Agregar otro ítem? (si/no): ");
                            String another = scanner.nextLine().trim().toLowerCase();
                            addingItems = another.equals("si") || another.equals("sí");
                        }

                        service.createOrder(order);
                        order.printOrder();

                        System.out.println("✅ Orden creada correctamente");

                    } catch (BusinessException e) {
                        System.out.println("❌ Error de negocio: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Error inesperado: " + e.getMessage());
                    }
                    break;

                case 2:
                    running = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }

        scanner.close();
    }
}