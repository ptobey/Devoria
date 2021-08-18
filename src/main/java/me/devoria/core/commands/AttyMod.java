package me.devoria.core.commands;

import me.devoria.core.ItemReader.itemReader;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class AttyMod implements CommandExecutor {


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)   {

        List item_table;

        if(s.equalsIgnoreCase("Reveal"))
        {
            if(!(commandSender instanceof Player)){
                return true;
            }
            Player player = (Player) commandSender;

            if(player.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                player.sendMessage("Must be holding the item!");
                return true;
            }


            //try {
            //    item_table = itemReader.reader("Itemtest.yml");
            //} catch (Exception e) {
            //    e.printStackTrace();
            //}

            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(),"generic.attackDamage",100.00,
                    AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,modifier);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GOLD + "Revealed");

        }
        return false;






    }



}
