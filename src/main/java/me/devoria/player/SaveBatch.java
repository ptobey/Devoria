package me.devoria.player;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;

/** Attempts every save and evicts only values confirmed durable. */
final class SaveBatch {

    private SaveBatch() {
    }

    static <K, V> void saveAndRemoveSuccessful(Map<K, V> values,
            Predicate<V> saveOperation) {
        for (Map.Entry<K, V> entry : new ArrayList<>(values.entrySet())) {
            if (saveOperation.test(entry.getValue())) {
                values.remove(entry.getKey(), entry.getValue());
            }
        }
    }
}
