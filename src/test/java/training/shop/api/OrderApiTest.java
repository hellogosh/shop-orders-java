package training.shop.api;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import training.shop.stub.OrderStubExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static training.shop.config.ConfigProvider.config;

@Tag("api")
@ExtendWith(OrderStubExtension.class)
public class OrderApiTest {

    @Test
    void existingOrderShouldBeReturned() {
       OrderResponse order = given()
               .baseUri(config().baseUrl())
               .when()
               .get("/orders/ORD-1")
               .then()
               .statusCode(200)
               .contentType("application/json")
               .extract()
               .as(OrderResponse.class);

       assertEquals(new OrderResponse("ORD-1", "Ivanov", 2500, "PAID"), order);
    }

    @Test
    void unknownOrderShouldReturn404WithError() {
        given()
                .baseUri(config().baseUrl())
                .when()
                .get("/orders/ORD-999")
                .then()
                .statusCode(404)
                .contentType("application/json")
                .body("error", equalTo("Order not found"))
                .body("id", equalTo("ORD-999"));
    }
}
