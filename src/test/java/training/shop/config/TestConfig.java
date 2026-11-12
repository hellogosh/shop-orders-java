package training.shop.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;
import org.aeonbits.owner.Config.Sources;

// Только реально используемые настройки. Браузер не настраивается: UI-тесты курса работают в Chromium.
@Sources("classpath:test.properties")
public interface TestConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("dbUrl")
    String dbUrl();
}
