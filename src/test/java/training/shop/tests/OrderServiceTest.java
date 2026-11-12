package training.shop.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import training.shop.models.Order;
import training.shop.models.OrderStatus;
import training.shop.services.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    private OrderService orderService;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        orders = List.of(
                new Order("ORD-1", "Ivanov", 2500, OrderStatus.PAID),
                new Order("ORD-2", "Petrova", 18000, OrderStatus.SHIPPED),
                new Order("ORD-3", "Sidorov", 700, OrderStatus.PAID)
        );
    }

    @Test
    void shouldReturnOnlyPaidOrders() {
        List<Order> result = orderService.ordersWithStatus(orders, OrderStatus.PAID);
        assertThat(result)
                .extracting(Order::id)
                .containsExactly("ORD-1", "ORD-3");
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersWithStatus() {
        List<Order> result = orderService.ordersWithStatus(orders, OrderStatus.CANCELLED);
        assertThat(result).isEmpty();
    }

    @Test
    void totalAmountShouldBeSumOfOrders() {
        int sum = orderService.totalAmount(orders);
        assertEquals(21200, sum);
    }

    @Test
    void totalAmountOfEmptyListShouldBeZero() {
        int sum = orderService.totalAmount(List.of());
        assertEquals(0, sum);
    }
}