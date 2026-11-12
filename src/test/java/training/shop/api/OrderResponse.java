package training.shop.api;

public record OrderResponse(String id, String customer, int amount, String status) {
}
