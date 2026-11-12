package training.shop.stub;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Учебный стаб магазина для итогового практикума.
 * GET /orders/{id} отдаёт JSON заказа, а браузеру (Accept: text/html) — страницу заказа.
 * Неизвестный номер — 404 и JSON {"error":"Order not found","id":"<номер>"}.
 * Запуск отдельно: ./gradlew runStub, адрес http://localhost:8090/orders/ORD-1
 *
 * Упрощения: стаб отдаёт фиксированные данные (не OrderService и не H2),
 * не различает HTTP-методы, а HTML выбирает поиском "text/html" в заголовке Accept.
 */
public class OrderStub {

    private static final Map<String, String[]> ORDERS = Map.of(
            "ORD-1", new String[]{"ORD-1", "Ivanov", "2500", "PAID"},
            "ORD-2", new String[]{"ORD-2", "Petrova", "18000", "SHIPPED"}
    );

    private final HttpServer server;

    public OrderStub(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/orders/", this::handleOrder);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    private void handleOrder(HttpExchange exchange) throws IOException {
        String id = exchange.getRequestURI().getPath().substring("/orders/".length());
        String[] order = ORDERS.get(id);
        String accept = exchange.getRequestHeaders().getFirst("Accept");
        boolean browser = accept != null && accept.contains("text/html");

        if (order == null) {
            send(exchange, 404, "application/json", "{\"error\":\"Order not found\",\"id\":\"%s\"}".formatted(id));
        } else if (browser) {
            send(exchange, 200, "text/html; charset=utf-8", page(order));
        } else {
            send(exchange, 200, "application/json", json(order));
        }
    }

    private String json(String[] order) {
        return """
                {"id":"%s","customer":"%s","amount":%s,"status":"%s"}"""
                .formatted((Object[]) order);
    }

    private String page(String[] order) {
        return """
                <!doctype html>
                <html lang="ru"><head><meta charset="utf-8"><title>Заказ %1$s</title></head>
                <body>
                  <h1>Заказ <span data-testid="order-id">%1$s</span></h1>
                  <p>Покупатель: <span data-testid="order-customer">%2$s</span></p>
                  <p>Сумма: <span data-testid="order-amount">%3$s</span> ₽</p>
                  <p>Статус: <span data-testid="order-status">%4$s</span></p>
                </body></html>
                """.formatted((Object[]) order);
    }

    private void send(HttpExchange exchange, int status, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8090;
        new OrderStub(port).start();
        System.out.println("Стаб магазина запущен: http://localhost:" + port + "/orders/ORD-1 (Ctrl+C — остановить)");
    }
}
