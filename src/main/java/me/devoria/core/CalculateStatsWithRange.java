package me.devoria.core;

public class CalculateStatsWithRange {
    public static void main(String[] args) {
        System.out.println(calculate("10-20","34"));
    }
    public static String calculate(Object range, String percentage) {
        String[] splitRange = String.valueOf(range).split(",");
        int min = Integer.parseInt(splitRange[0]);
        int max = Integer.parseInt(splitRange[1]);

        double eachPercent = ((max-min)/100.0);
        double newStat = min + eachPercent*Integer.parseInt(percentage);
        return String.valueOf((int) Math.round(newStat));
    }
}
