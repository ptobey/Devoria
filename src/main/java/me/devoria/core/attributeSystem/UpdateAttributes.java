package me.devoria.core.attributeSystem;

import me.devoria.core.Core;
import me.devoria.core.WeightedPercentageGenerator;
import me.devoria.core.itemSystem.CalculateStatsWithRange;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class UpdateAttributes {

    public static void update(Player p, String weaponItemData) {


        String attributes = "";

        int walkSpeed = 0;

        HashMap<String,String> weaponStatsMap = new HashMap<>();

        if(weaponItemData != null) {


            String[] separatedWeaponStats = weaponItemData.split(",");

            for (int i = 1; i < separatedWeaponStats.length; i++) {
                String[] arr = separatedWeaponStats[i].split(":");
                weaponStatsMap.put(arr[0], arr[1]);
            }
            if (weaponStatsMap.get("walkSpeed") != null) {
                walkSpeed = Integer.parseInt(weaponStatsMap.get("walkSpeed"));
            }
        }



        attributes += ",walkSpeed:"+walkSpeed;

        p.setMetadata("attributes", new FixedMetadataValue(Core.getInstance(), attributes));
        String s = CalculateStatsWithRange.calculate("12-50", String.valueOf(walkSpeed), true);
    p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue((float)Integer.parseInt(s)/100);
  //  p.sendMessage(String.valueOf((float)Integer.parseInt(s)/100));
    }
}
