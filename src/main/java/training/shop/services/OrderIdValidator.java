package training.shop.services;

public class OrderIdValidator {

    // Требование: номер заказа — "ORD-", затем от 1 до 6 цифр. Например, ORD-1, ORD-123456.
    // null и пустая строка — невалидны.
    public boolean isValid(String id) {
        if (id == null || !id.startsWith("ORD-")) {
            return false;
        }
        String digits = id.substring(4);
        if (digits.isEmpty() || digits.length() > 6) {
            return false;
        }
        for (char symbol : digits.toCharArray()) {
            if (!Character.isDigit(symbol)) {
                return false;
            }
        }
        return true;
    }
}
