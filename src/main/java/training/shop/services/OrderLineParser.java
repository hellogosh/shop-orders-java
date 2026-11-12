package training.shop.services;

import training.shop.models.Order;
import training.shop.models.OrderStatus;

public class OrderLineParser {

    // Строка файла: номер;покупатель;сумма;статус — например, ORD-1;Ivanov;2500;PAID
    public Order parse(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Order line must have 4 fields: " + line);
        }
        int amount;
        try {
            amount = Integer.parseInt(parts[2]);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Amount must be a number: " + parts[2]);
        }
        return new Order(parts[0], parts[1], amount, OrderStatus.valueOf(parts[3]));
    }
}
