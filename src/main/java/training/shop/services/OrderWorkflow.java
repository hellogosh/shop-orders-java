package training.shop.services;

import training.shop.models.Order;
import training.shop.models.OrderStatus;

public class OrderWorkflow {

    // Оплатить можно только новый заказ
    public Order pay(Order order) {
        if (order.status() != OrderStatus.NEW) {
            throw new IllegalStateException("Only NEW order can be paid");
        }
        return new Order(order.id(), order.customer(), order.amount(), OrderStatus.PAID);
    }

    // Отправить можно только оплаченный заказ
    public Order ship(Order order) {
        if (order.status() != OrderStatus.PAID) {
            throw new IllegalStateException("Only PAID order can be shipped");
        }
        return new Order(order.id(), order.customer(), order.amount(), OrderStatus.SHIPPED);
    }

    // Отменить можно любой заказ, кроме отправленного
    public Order cancel(Order order) {
        if (order.status() == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Shipped order cannot be cancelled");
        }
        return new Order(order.id(), order.customer(), order.amount(), OrderStatus.CANCELLED);
    }
}
