package me.devoria.core.commands;


import me.devoria.core.YMLParser;
import me.devoria.core.customBows.MakeBow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.Map;


public class ItemCommand implements CommandExecutor {

    //Gives you a custom item
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YMLParser ymlParser = new YMLParser();
        Map<String, Object> attributes;

        try {
            attributes = ymlParser.parse(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        if (sender instanceof Player) {
            ((Player) sender).getInventory().addItem(MakeBow.makeBow(attributes.get("name"), attributes.get("tradeable"), attributes.get("rarity"), attributes.get("attack_speed"), attributes.get("attack_range"), attributes.get("rune_slots"), attributes.get("damage"), attributes.get("earth_damage"), attributes.get("fire_damage"), attributes.get("water_damage"), attributes.get("light_damage"), attributes.get("dark_damage")));
            return true;
        }
        return false;
    }
}
