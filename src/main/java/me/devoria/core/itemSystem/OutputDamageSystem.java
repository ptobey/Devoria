package me.devoria.core.itemSystem;

import me.devoria.core.WeightedPercentageGenerator;

import java.util.ArrayList;
import java.util.HashMap;


public class OutputDamageSystem {

    public static void main(String[] args) {
        System.out.println(getDamage(",damage:1-100,earthDamage:100,"));

    }

    public static String getDamage(String itemData) {

        HashMap<String,String> map = new HashMap<>();
        ArrayList<String> damages = new ArrayList<>();

        int earthDamage;
        int fireDamage;
        int waterDamage;
        int lightDamage;
        int darkDamage;


        String[] seperatedStats = itemData.split(",");

        for(int i=1;i<seperatedStats.length;i++){
            String[] arr = seperatedStats[i].split(":");
            map.put(arr[0], arr[1]);
        }

        String ed = map.get("earthDamage");
        String fd = map.get("fireDamage");
        String wd = map.get("waterDamage");
        String ld = map.get("lightDamage");
        String dd = map.get("darkDamage");


        String damagePercentage = WeightedPercentageGenerator.generate();

        int normalDamage = Integer.parseInt(CalculateStatsWithRange.calculate(map.get("damage"), damagePercentage, true));


        if(ed == null)
            earthDamage = 0;
        else {
            earthDamage = Integer.parseInt(ed);
        }


        if(fd == null)
            fireDamage = 0;
        else {
            fireDamage = Integer.parseInt(fd);
        }


        if(wd == null)
            waterDamage = 0;
        else {
            waterDamage = Integer.parseInt(wd);
        }


        if(ld == null)
            lightDamage = 0;
        else {
            lightDamage = Integer.parseInt(ld);
        }


        if(dd == null)
            darkDamage = 0;
        else {
            darkDamage = Integer.parseInt(dd);
        }




        int totalDamage = normalDamage+earthDamage+fireDamage+waterDamage+lightDamage+darkDamage;

        damages.add(String.valueOf(normalDamage));
        damages.add(String.valueOf(earthDamage));
        damages.add(String.valueOf(fireDamage));
        damages.add(String.valueOf(waterDamage));
        damages.add(String.valueOf(lightDamage));
        damages.add(String.valueOf(darkDamage));
        damages.add(String.valueOf(totalDamage));

        return damages.toString();



    }

}
