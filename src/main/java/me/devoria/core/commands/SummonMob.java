package me.devoria.core.commands;




import com.google.common.collect.ImmutableSet;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.generator.ModelGenerator;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.mount.MountablePart;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import me.devoria.core.Core;
import me.devoria.core.Listeners;
import me.devoria.core.damageSystem.UpdateHealthBar;
import me.devoria.core.itemSystem.UpdateItem;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;


public class SummonMob implements CommandExecutor {

    private static final int MAX_RANGE = 64;
    private static final Set<Material> transparent = ImmutableSet.of(Material.AIR, Material.CAVE_AIR, Material.TALL_GRASS);

    //Gives you random loot
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sendError(sender, "May only be used in-game");
            return true;
        }

        String modelName = args[0];
        String mobType = args.length > 1 ? args[1] : "husk";
        EntityType entityType;
        try {
            entityType = EntityType.valueOf(mobType.toUpperCase());
        } catch (Exception ex) {
            sendError(sender, "Invalid mob type: " + ChatColor.WHITE + mobType);
            return true;
        }
        Player player = (Player) sender;
        Block block = player.getTargetBlock(transparent, MAX_RANGE);
        if (transparent.contains(block.getType())) {
            sendError(sender, "You must be looking at a block");
            return true;
        }
        block = block.getRelative(BlockFace.UP);


        Entity entity = block.getWorld().spawnEntity(block.getLocation(), entityType);


        Mob mob;
        if (entity instanceof Mob) {
            mob = (Mob) entity;
        } else {
            return true;
        }


        mob.setMetadata("healthStats", new FixedMetadataValue(Core.getInstance(), ",currentHealth:5000"));
        mob.setMetadata("attributes", new FixedMetadataValue(Core.getInstance(), ",health:5000"));


     //   ArmorStand healthBar = mob.getWorld().spawn(new Location(mob.getWorld(), 0, 0 ,0), ArmorStand.class);
      //  healthBar.setInvisible(true);
     //   healthBar.setMarker(true);
      //  healthBar.setCustomNameVisible(true);
      //  healthBar.setCustomName(ChatColor.DARK_RED + "❤ " + "5000" + "/" + "5000");


      //  mob.addPassenger(healthBar);

        try {
            mob.getEquipment().setItemInMainHand(UpdateItem.update(",fileName:common"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //mob.addPassenger(healthBar);


        ActiveModel model = ModelEngineAPI.api.getModelManager().createActiveModel(modelName);
        if (model == null) {
            sendError(sender, "Failed to load model: " + ChatColor.WHITE + modelName);
            return true;
        }

        ModeledEntity modeledEntity = ModelEngineAPI.api.getModelManager().createModeledEntity(mob);
        if (modeledEntity == null) {
            sendError(sender, "Failed to create modelled entity");
            return false;
        }

        modeledEntity.addActiveModel(model);
        modeledEntity.detectPlayers();
        modeledEntity.setInvisible(true);
        modeledEntity.getNametagHandler().setCustomNameVisibility("nametag", true);
        modeledEntity.getNametagHandler().setCustomNameVisibility("healthbar", true);
        modeledEntity.getNametagHandler().setCustomName("nametag","BOB");
        UpdateHealthBar.update(mob);

        return true;
    }

    protected void sendError(CommandSender sender, String string) {
        sender.sendMessage("ERROR: " + string);
    }
}
