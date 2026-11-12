package training.shop.stub;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;

/**
 * Поднимает OrderStub перед API- и UI-тестами.
 * baseUrl берется из src/test/resources/test.properties, по умолчанию http://localhost:8090.
 * Если порт уже занят, проверяет, что там отвечает учебный стаб (например, запущенный ./gradlew runStub),
 * и только тогда использует его. Любое другое приложение на порту — понятная ошибка, а не странные падения тестов.
 */
public class OrderStubExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(OrderStubExtension.class, k -> startStub());
    }

    private ExtensionContext.Store.CloseableResource startStub() {
        String baseUrl = baseUrl();
        int port = URI.create(baseUrl).getPort();
        try {
            OrderStub stub = new OrderStub(port);
            stub.start();
            return stub::stop;
        } catch (BindException portIsBusy) {
            if (trainingStubAnswers(baseUrl)) {
                return () -> { };
            }
            throw new IllegalStateException("Порт " + port + " занят другим приложением, это не учебный стаб. "
                    + "Остановите его или поменяйте baseUrl в src/test/resources/test.properties", portIsBusy);
        } catch (IOException exception) {
            throw new IllegalStateException("Не удалось запустить стаб магазина на порту " + port, exception);
        }
    }

    private boolean trainingStubAnswers(String baseUrl) {
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create(baseUrl + "/orders/ORD-1"))
                            .timeout(Duration.ofSeconds(2))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && response.body().contains("\"customer\":\"Ivanov\"");
        } catch (IOException | InterruptedException | RuntimeException exception) {
            return false;
        }
    }

    private String baseUrl() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ignored) {
            // нет файла — используем адрес по умолчанию
        }
        return properties.getProperty("baseUrl", "http://localhost:8090");
    }
}
