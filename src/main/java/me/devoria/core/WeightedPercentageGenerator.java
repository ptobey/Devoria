package me.devoria.core;

import java.util.Random;

public class WeightedPercentageGenerator {
    public static String generate() {
        Random randomNumbers = new Random();

        int randomNumber1 = randomNumbers.ints(0, 51).findAny().getAsInt();
        int randomNumber2 = randomNumbers.ints(0, 51).findAny().getAsInt();

        return String.valueOf(randomNumber1+randomNumber2);


    }

}
