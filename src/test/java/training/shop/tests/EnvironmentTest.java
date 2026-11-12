package training.shop.tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Готовый тест: проверяет, что проект открылся и запускается. Не меняйте его.
class EnvironmentTest {

    @Test
    void projectShouldRunOnJava21OrNewer() {
        assertTrue(Runtime.version().feature() >= 21, "Java version: " + Runtime.version());
    }
}
