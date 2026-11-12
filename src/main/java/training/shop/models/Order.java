package training.shop.models;

public record Order(
        String id,
        String customer,
        int amount,
        OrderStatus status
) {}