package me.devoria.commands;


import com.google.common.collect.ImmutableSet;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.bone.Nameable;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import me.devoria.Devoria;
import me.devoria.mobs.MobDefinition;
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

        if (!(sender instanceof Player player)) {
            sendError(sender, "May only be used in-game");
            return true;
        }

        MobDefinition definition;
        try {
            Map<String, String> stats = ItemUtils.parseItemFile("mobs", args[0]);
            definition = MobDefinition.from(stats);
        } catch (FileNotFoundException | IllegalArgumentException exception) {
            sendError(sender, "Invalid mob definition '" + args[0] + "': "
                    + exception.getMessage());
            return true;
        }

        EntityType entityType;
        try {
            entityType = EntityType.valueOf(
                    definition.entityType().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            sendError(sender, "Invalid mob type: " + ChatColor.WHITE
                    + definition.entityType());
            return true;
        }

        Block block = player.getTargetBlock(transparent, MAX_RANGE);
        if (transparent.contains(block.getType())) {
            sendError(sender, "You must be looking at a block");
            return true;
        }

        ActiveModel activeModel;
        try {
            activeModel = ModelEngineAPI.createActiveModel(definition.model());
        } catch (RuntimeException | LinkageError error) {
            reportModelFailure(sender, definition.model(), error);
            return true;
        }
        if (activeModel == null) {
            sendError(sender, "Failed to load model: " + ChatColor.WHITE
                    + definition.model());
            return true;
        }

        Block spawnBlock = block.getRelative(BlockFace.UP);
        Entity entity;
        try {
            entity = spawnBlock.getWorld().spawnEntity(
                    spawnBlock.getLocation(), entityType);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            discardActiveModel(activeModel);
            sendError(sender, "Could not spawn entity type: "
                    + ChatColor.WHITE + definition.entityType());
            return true;
        }
        if (!(entity instanceof Mob mob)) {
            entity.remove();
            discardActiveModel(activeModel);
            sendError(sender, "Configured entity type is not a mob: "
                    + ChatColor.WHITE + definition.entityType());
            return true;
        }

        try {
            configureMob(mob, definition, activeModel);
            return true;
        } catch (RuntimeException | LinkageError error) {
            mob.remove();
            reportModelFailure(sender, definition.model(), error);
            return true;
        }
    }

    private void configureMob(Mob mob, MobDefinition definition,
            ActiveModel activeModel) {
        Devoria plugin = Devoria.getInstance();
        mob.setMetadata("healthStats", new FixedMetadataValue(plugin,
                ",currentHealth:" + definition.maxHealth()));
        mob.setMetadata("attributes", new FixedMetadataValue(plugin,
                ",health:" + definition.maxHealth()
                        + ",damage:" + definition.damage()
                        + ",xp:" + definition.experience()));

        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(mob);
        if (modeledEntity == null) {
            throw new IllegalStateException("ModelEngine did not create a modeled entity");
        }

        modeledEntity.addModel(activeModel, true);
        modeledEntity.setBaseEntityVisible(false);
        PlayerUtils.updateHealthBar(mob);

        Nameable nameBone = activeModel.getNametagHandler().getBones().get("nametag");
        if (nameBone != null) {
            nameBone.setCustomNameVisible(true);
            nameBone.setCustomName(definition.name());
        }
    }

    private void reportModelFailure(CommandSender sender, String model,
            Throwable error) {
        Devoria.getInstance().getLogger().warning(
                "Could not summon model '" + model + "': " + error.getMessage());
        sendError(sender, "ModelEngine could not create model: "
                + ChatColor.WHITE + model);
    }

    private void discardActiveModel(ActiveModel activeModel) {
        try {
            activeModel.destroy();
        } catch (RuntimeException | LinkageError error) {
            Devoria.getInstance().getLogger().warning(
                    "Could not discard unused ModelEngine model: " + error.getMessage());
        }
    }

    protected void sendError(CommandSender sender, String string) {
        sender.sendMessage("ERROR: " + string);
    }
}
