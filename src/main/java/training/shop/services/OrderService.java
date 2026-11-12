package training.shop.services;

import training.shop.models.Order;
import training.shop.models.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    // Заказы с нужным статусом, в том же порядке, что и в исходном списке
    public List<Order> ordersWithStatus(List<Order> orders, OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.status() == status) {
                result.add(order);
            }
        }
        return result;
    }

    // Сумма всех заказов в рублях; пустой список — 0
    public int totalAmount(List<Order> orders) {
        int total = 0;
        for (Order order : orders) {
            total += order.amount();
        }
        return total;
    }
}
