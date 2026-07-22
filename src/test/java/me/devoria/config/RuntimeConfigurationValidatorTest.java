package me.devoria.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.MemoryConfiguration;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

class RuntimeConfigurationValidatorTest {

    @Test
    void bundledDefaultsAreValid() {
        assertTrue(validate(defaultConfig(), Map.of()).isEmpty());
    }

    @Test
    void rejectsInvalidTypesAndNegativeTickSpeed() {
        MemoryConfiguration config = defaultConfig();
        config.set("world-rules.enabled", "sometimes");
        config.set("world-rules.random-tick-speed", -1);
        config.set("integrations.model-engine.enabled", 1);

        assertEquals(List.of(
                "world-rules.enabled must be true or false",
                "integrations.model-engine.enabled must be true or false",
                "world-rules.random-tick-speed must not be negative"),
                validate(config, Map.of()));
    }

    @Test
    void enabledDatabaseRequiresSecureMysqlSettingsAndCredentials() {
        MemoryConfiguration config = defaultConfig();
        config.set("database.enabled", true);
        config.set("database.url", "jdbc:postgresql://localhost/devoria?sslmode=disabled");

        assertEquals(List.of(
                "database.url must use the jdbc:mysql scheme",
                "database.url must not disable TLS",
                "database.username or DEVORIA_DB_USERNAME must be configured",
                "database.password or DEVORIA_DB_PASSWORD must be configured"),
                validate(config, Map.of()));
    }

    @Test
    void environmentCredentialsOverrideBlankConfigValues() {
        MemoryConfiguration config = defaultConfig();
        config.set("database.enabled", true);

        assertTrue(validate(config, Map.of(
                "DEVORIA_DB_USERNAME", "devoria",
                "DEVORIA_DB_PASSWORD", "secret")).isEmpty());
    }

    @Test
    void rejectsExplicitTlsDisableRegardlessOfCase() {
        MemoryConfiguration config = defaultConfig();
        config.set("database.enabled", true);
        config.set("database.url", "jdbc:mysql://localhost/devoria?sslMode=DISABLED");

        List<String> errors = validate(config, Map.of(
                "DEVORIA_DB_USERNAME", "devoria",
                "DEVORIA_DB_PASSWORD", "secret"));

        assertEquals(List.of("database.url must not disable TLS"), errors);
    }

    private static List<String> validate(MemoryConfiguration config,
            Map<String, String> environment) {
        return RuntimeConfigurationValidator.validate(config, environment);
    }

    private static MemoryConfiguration defaultConfig() {
        InputStream stream = RuntimeConfigurationValidatorTest.class
                .getResourceAsStream("/config.yml");
        if (stream == null) {
            throw new IllegalStateException("Bundled config.yml was not on the test classpath");
        }

        try (stream) {
            Yaml yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
            Map<String, Object> values = yaml.load(stream);
            MemoryConfiguration config = new MemoryConfiguration();
            addValues(config, "", values);
            return config;
        } catch (Exception exception) {
            throw new IllegalStateException("Could not read bundled config.yml", exception);
        }
    }

    private static void addValues(MemoryConfiguration config, String prefix,
            Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String path = prefix.isEmpty()
                    ? entry.getKey()
                    : prefix + "." + entry.getKey();
            if (entry.getValue() instanceof Map<?, ?> childValues) {
                @SuppressWarnings("unchecked")
                Map<String, Object> typedChildren = (Map<String, Object>) childValues;
                addValues(config, path, typedChildren);
            } else {
                config.set(path, entry.getValue());
            }
        }
    }
}
