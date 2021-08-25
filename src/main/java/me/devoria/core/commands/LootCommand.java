package me.devoria.core.commands;

import me.devoria.core.itemSystem.GenerateLoot;
import me.devoria.core.itemSystem.MakeUnidentifiedItem;
import me.devoria.core.itemSystem.UpdateWeapon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.Map;

public class LootCommand implements CommandExecutor {

    //Gives you random loot
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Map<String, Object> attributes;
            try {
                attributes = GenerateLoot.generate("huntsman", 0, "15");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            if(attributes.get("rarity").equals("common")) {
                try {
                    ((Player) sender).getInventory().setItemInMainHand(UpdateWeapon.update(",fileName:"+attributes.get("fileName")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            else {

                ((Player) sender).getInventory().addItem(MakeUnidentifiedItem.makeUnidentifiedItem(attributes.get("file_name"), attributes.get("rarity"), attributes.get("type"), attributes.get("level")));
            }

            return true;

        }
        return false;
    }
}
