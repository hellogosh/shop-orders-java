package training.shop.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import training.shop.stub.OrderStubExtension;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static training.shop.config.ConfigProvider.config; // Или ваш импорт конфигурации

@Tag("ui")
@ExtendWith(OrderStubExtension.class)
public class OrderPageSmokeTest {

    @Test
    void orderPageShouldShowOrderId() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch()) {

            Page page = browser.newPage();
            page.navigate(config().baseUrl() + "/orders/ORD-1");

            assertThat(page.locator("[data-testid='order-id']")).hasText("ORD-1");
        }
    }
}