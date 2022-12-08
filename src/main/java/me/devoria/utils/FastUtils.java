package me.devoria.utils;

import java.util.HashMap;

public class FastUtils {
    public static HashMap<String, String> map(String data) {

        HashMap<String, String> map = new HashMap<>();

        String[] separatedStats = data.split(",");

        for (int i = 1; i < separatedStats.length; i++) {
            String[] arr = separatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }
        return map;
    }
}
