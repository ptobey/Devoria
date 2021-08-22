package me.devoria.core.itemSystem;

public class CalculateStatsWithRange {
    public static String calculate(Object range, String percentage) {
        String[] splitRange = String.valueOf(range).split(",");
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);

        double eachPercent = ((max - min) / 100.0);
        double newStat = min + eachPercent * Integer.parseInt(percentage);
        return String.valueOf((int) Math.round(newStat));
    }

    public static String calculate(Object range, String percentage, boolean dash) {
        String[] splitRange = String.valueOf(range).split("-");
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);

        double eachPercent = ((max - min) / 100.0);
        double newStat = min + eachPercent * Integer.parseInt(percentage);
        return String.valueOf((int) Math.round(newStat));
    }
}
