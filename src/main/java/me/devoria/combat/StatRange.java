package me.devoria.combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record StatRange(int minimum, int maximum) {

    private static final Pattern RANGE_PATTERN = Pattern.compile("(-?\\d+)-(-?\\d+)");

    public StatRange {
        if (minimum > maximum) {
            throw new IllegalArgumentException("Range minimum cannot exceed its maximum.");
        }
    }

    public static StatRange parse(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Range is required.");
        }

        Matcher matcher = RANGE_PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid stat range: " + value);
        }

        return new StatRange(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)));
    }

    public int atPercentile(int percentile) {
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Percentile must be between 0 and 100.");
        }

        double value = minimum + ((maximum - (double) minimum) / 100.0) * percentile;
        return Math.toIntExact(Math.round(value));
    }
}
