package training.shop.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {

    private static final TestConfig CONFIG = ConfigFactory.create(TestConfig.class);

    public static TestConfig config() {
        return CONFIG;
    }
}
