package training.shop.services;

public class DiscountCalculator {

    // Требование: до 5000 ₽ — скидки нет; от 5000 до 19999 ₽ — 5 %; от 20000 ₽ — 10 %.
    // Отрицательная сумма — IllegalArgumentException("Amount must not be negative").
    public int discountPercent(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must not be negative");
        }
        if (amount < 5000) {
            return 0;
        }
        if (amount < 20000) {
            return 5;
        }
        return 10;
    }
}
