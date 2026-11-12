package training.shop.tests;

import org.junit.jupiter.api.Test;
import training.shop.models.Order;
import training.shop.models.OrderStatus;
import training.shop.services.OrderFileReader;
import training.shop.services.OrderLineParser;
import training.shop.services.OrderService;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderFileTest {

    private final OrderLineParser parser = new OrderLineParser();

    @Test
    void paidOrdersFromFileShouldBeFound() throws Exception {
        OrderFileReader reader = new OrderFileReader();
        List<Order> orders = new ArrayList<>();

        for (String line : reader.readLines(resourcePath("orders.txt"))) {
            orders.add(parser.parse(line));
        }

        List<Order> paidOrders = new OrderService().ordersWithStatus(orders, OrderStatus.PAID);

        // В вашем файле orders.txt со статусом PAID находятся заказы ORD-1 и ORD-4
        assertThat(paidOrders)
                .extracting(Order::id)
                .containsExactly("ORD-1", "ORD-4");
    }

    @Test
    void correctLineShouldBeParsed() {
        Order order = parser.parse("ORD-1;Ivanov;2500;PAID");
        assertEquals(new Order("ORD-1", "Ivanov", 2500, OrderStatus.PAID), order);
    }

    @Test
    void lineWithThreeFieldsShouldBeRejected() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("ORD-1;Ivanov;2500")
        );
        assertEquals("Order line must have 4 fields: ORD-1;Ivanov;2500", exception.getMessage());
    }

    @Test
    void amountThatIsNotNumberShouldBeRejected() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> parser.parse("ORD-1;Ivanov;abc;PAID")
        );
        assertEquals("Amount must be a number: abc", exception.getMessage());
    }

    private Path resourcePath(String fileName) throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource(fileName);
        if (url == null) {
            throw new IllegalStateException("Test resource not found: " + fileName);
        }
        return Path.of(url.toURI());
    }
}