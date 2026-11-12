package training.shop.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import training.shop.services.DiscountCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscountCalculatorTest {
    private final DiscountCalculator calculator = new DiscountCalculator();

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "4999, 0",
            "5000, 5",
            "19999, 5",
            "20000, 10"
    })
    void discountShouldMatchRequirement(int amount, int expectedPercent) {
        assertEquals(expectedPercent, calculator.discountPercent(amount));
    }
    @Test
    void negativeAmountShouldBeRejected() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.discountPercent(-1)
        );
        assertEquals("Amount must not be negative", exception.getMessage());
    }
}

