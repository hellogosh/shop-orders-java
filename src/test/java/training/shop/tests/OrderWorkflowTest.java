package training.shop.tests;

import org.junit.jupiter.api.Test;
import training.shop.models.Order;
import training.shop.models.OrderStatus;
import training.shop.services.OrderWorkflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderWorkflowTest {

    private final OrderWorkflow workflow = new OrderWorkflow();

    @Test
    void newOrderShouldBePaid() {
        Order order = new Order("ORD-3", "Sidorov", 700, OrderStatus.NEW);
        Order paid = workflow.pay(order);
        assertEquals(new Order("ORD-3", "Sidorov", 700, OrderStatus.PAID), paid);
    }

    @Test
    void paidOrderShouldBeShipped() {
        Order order = new Order("ORD-1", "Ivanov", 2500, OrderStatus.PAID);
        Order shipped = workflow.ship(order);
        assertEquals(OrderStatus.SHIPPED, shipped.status());
    }

    @Test
    void newOrderShouldNotBeShipped() {
        Order order = new Order("ORD-3", "Sidorov", 700, OrderStatus.NEW);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> workflow.ship(order)
        );
        assertEquals("Only PAID order can be shipped", exception.getMessage());
    }

    @Test
    void shippedOrderShouldNotBeCancelled() {
        Order order = new Order("ORD-2", "Petrova", 18000, OrderStatus.SHIPPED);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> workflow.cancel(order)
        );
        assertEquals("Shipped order cannot be cancelled", exception.getMessage());
    }
}