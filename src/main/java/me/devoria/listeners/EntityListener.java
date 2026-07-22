package me.devoria.listeners;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ItemUtils;
import me.devoria.utils.MiscellaneousUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EntityListener implements Listener {
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent ev) {
        Entity entity = ev.getEntity();
        if (entity.getType() == EntityType.ARROW
                && (entity.getScoreboardTags().contains("devoriaProjectile")
                || entity.getScoreboardTags().contains("arrowRainArrow")
                || entity.getScoreboardTags().contains("nodamage"))) {
            entity.remove();
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity e = event.getEntity();
        if (e.getMetadata("damagers").isEmpty() || e.getMetadata("attributes").isEmpty()) {
            return;
        }

        try {
            String damagers = e.getMetadata("damagers").get(0).asString();
            HashMap<String, String> damagersMap = FastUtils.map(damagers);

            String entityStats = e.getMetadata("attributes").get(0).asString();
            HashMap<String, String> entityStatsMap = FastUtils.map(entityStats);
            if (!damagersMap.containsKey("total") || !entityStatsMap.containsKey("xp")) {
                return;
            }

            int xp = Integer.parseInt(entityStatsMap.get("xp"));
            int totalDamage = Math.abs(Integer.parseInt(damagersMap.get("total")));
            if (totalDamage == 0) {
                return;
            }

            event.getDrops().clear();
            for (String d : damagersMap.keySet()) {
                if (d.equals("total")) {
                    continue;
                }

                UUID playerId = UUID.fromString(d);
                int playerDamage = Math.abs(Integer.parseInt(damagersMap.get(d)));
                Player player = Bukkit.getPlayer(playerId);

                if (playerDamage >= totalDamage * 0.15) {
                    try {
                        Collection<ItemStack> drops = ItemUtils.generate("huntsman", "15");
                        for (ItemStack drop : drops) {
                            ItemMeta itemMeta = drop.getItemMeta();
                            if (itemMeta == null) {
                                continue;
                            }
                            String itemInfo = itemMeta.getLocalizedName() + ",owner:" + d;
                            itemMeta.setLocalizedName(itemInfo);
                            drop.setItemMeta(itemMeta);
                            e.getLocation().getWorld().dropItemNaturally(e.getLocation(), drop);
                        }
                    } catch (FileNotFoundException err) {
                        Devoria.getInstance().getLogger().warning(
                                "Could not generate mob loot: " + err.getMessage());
                    }
                }

                int percentXp = (int) (xp * (playerDamage / (double) totalDamage));
                if (player != null) {
                    player.sendMessage("+" + percentXp + " xp!");
                }
            }
        } catch (IllegalArgumentException exception) {
            Devoria.getInstance().getLogger().warning(
                    "Ignored malformed Devoria mob metadata: " + exception.getMessage());
        }
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {
        Entity victim = e.getEntity();
        if (victim.getMetadata("healthStats").isEmpty()
                || victim.getMetadata("attributes").isEmpty()) {
            return;
        }

        String damagerStats = "";
        boolean isPlayer = false;
        Entity damagerEntity = e.getDamager();
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            ItemMeta heldItemMeta = damager.getInventory().getItemInMainHand().getItemMeta();
            if (heldItemMeta == null || !heldItemMeta.hasLocalizedName()) {
                return;
            }
            damagerStats = heldItemMeta.getLocalizedName();
            isPlayer = true;
        } else {
            Entity damager = e.getDamager();
            if (damager.getMetadata("attributes").isEmpty()) {
                return;
            }
            damagerStats = damager.getMetadata("attributes").get(0).asString();
        }

        ArrayList<String> damages = ItemUtils.getItemDamage(damagerStats);
        if (damages.size() <= 6) {
            return;
        }

        int damage;
        try {
            damage = Integer.parseInt(damages.get(6));
        } catch (NumberFormatException exception) {
            Devoria.getInstance().getLogger().warning("Ignored invalid damage metadata");
            return;
        }

        e.setDamage(0);

        if (isPlayer) {
            MiscellaneousUtils damageIndicator = new MiscellaneousUtils();

            damageIndicator.spawnDamageIndicator(victim.getWorld(), damages, victim.getLocation().add(1, 1, 0));
            e.getDamager().sendMessage(damages.get(6));
        }

        PlayerUtils.changeHealth(victim, -Math.abs(damage), damagerEntity, true);

        if (victim instanceof Player) {
            PlayerUtils.updateHealthBar((Player) e.getEntity());
        } else {
            PlayerUtils.updateHealthBar(victim);
        }

    }
}
