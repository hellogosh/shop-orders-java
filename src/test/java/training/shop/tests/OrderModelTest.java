package training.shop.tests;

import org.junit.jupiter.api.Test;
import training.shop.models.Order;
import training.shop.models.OrderStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderModelTest {
    @Test
    void orderShouldKeepItsFields(){
        Order first = new Order("ORD-1", "Ivanov", 2500, OrderStatus.PAID);
        Order second = new Order("ORD-1", "Ivanov", 2500, OrderStatus.PAID);
        Order other = new Order("ORD-1", "Ivanov", 2500, OrderStatus.NEW);

        assertEquals(first, second);      // Одинаковые заказы равны
        assertNotEquals(first, other);    // Заказы с разными статусами НЕ равны
    }
}