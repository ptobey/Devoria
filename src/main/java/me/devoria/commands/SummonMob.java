package me.devoria.commands;


import com.google.common.collect.ImmutableSet;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import me.devoria.Devoria;
import me.devoria.utils.ItemUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;


public class SummonMob implements CommandExecutor {

    private static final int MAX_RANGE = 64;
    private static final Set<Material> transparent = ImmutableSet.of(Material.AIR, Material.CAVE_AIR, Material.TALL_GRASS);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        try {
            Map<String, String> stats = ItemUtils.parseItemFile("mobs", args[0]);

            if (!(sender instanceof Player)) {
                sendError(sender, "May only be used in-game");
                return true;
            }

            String name = stats.get("name");
            String model = stats.get("model");
            String type = stats.get("type");
            Object maxHealth = stats.get("max_health");
            Object damage = stats.get("damage");
            Object xp = stats.get("xp");

            Material mainHand = stats.get("main_hand") != null ? Material.valueOf(stats.get("main_hand").toUpperCase()) : Material.AIR;
            Material offHand = stats.get("off_hand") != null ? Material.valueOf(stats.get("off_hand").toUpperCase()) : Material.AIR;


            EntityType entityType;
            try {
                entityType = EntityType.valueOf(type.toUpperCase());
            } catch (Exception ex) {
                sendError(sender, "Invalid mob type: " + ChatColor.WHITE + type);
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


            mob.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:" + maxHealth));
            mob.setMetadata("attributes", new FixedMetadataValue(Devoria.getInstance(), ",health:" + maxHealth + ",damage:" + damage + ",xp:" + xp));


            Objects.requireNonNull(mob.getEquipment()).setItemInMainHand(new ItemStack(mainHand));
            Objects.requireNonNull(mob.getEquipment()).setItemInOffHand(new ItemStack(offHand));

            ActiveModel activeModel = ModelEngineAPI.createActiveModel(model);
            if (activeModel == null) {
                sendError(sender, "Failed to load model: " + ChatColor.WHITE + model);
                return true;
            }

            ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(mob);
            if (modeledEntity == null) {
                sendError(sender, "Failed to create modelled entity");
                return false;
            }

            modeledEntity.addModel(activeModel, true);
            modeledEntity.setBaseEntityVisible(false);
            PlayerUtils.updateHealthBar(mob);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void sendError(CommandSender sender, String string) {
        sender.sendMessage("ERROR: " + string);
    }
}
