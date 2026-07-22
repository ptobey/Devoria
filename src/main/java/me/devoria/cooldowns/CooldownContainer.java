package me.devoria.cooldowns;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.LongSupplier;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class CooldownContainer implements ConfigurationSerializable {

    // A hashmap is a data structure which maps a key to a value. You can get the value by inputting the key
    private final Map<String, Long> cooldownMap = new HashMap<>();
    private final LongSupplier currentTimeMillis;

    public CooldownContainer(Map<String, Object> map) {
        this(map, System::currentTimeMillis);
    }

    public CooldownContainer() {
        this(new HashMap<>(), System::currentTimeMillis);
    }

    CooldownContainer(LongSupplier currentTimeMillis) {
        this(new HashMap<>(), currentTimeMillis);
    }

    CooldownContainer(Map<String, Object> map, LongSupplier currentTimeMillis) {
        this.currentTimeMillis = Objects.requireNonNull(currentTimeMillis, "currentTimeMillis");
        map.forEach((key, value) -> cooldownMap.put(key, ((Number) value).longValue()));
    }

    public void setCooldownFromNow(String name, long millis) {
        // System.currentTimeMillis() gets the current timestamp. so + millis is that many milliseconds in the future.
        cooldownMap.put(name, currentTimeMillis.getAsLong() + millis);
    }

    public long getCooldownLeft(String name) {
        return cooldownMap.getOrDefault(name, 0L) - currentTimeMillis.getAsLong();
    }

    public boolean isCooldownDone(String name) {
        return getCooldownLeft(name) <= 0;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<>(cooldownMap);
    }
}
