package me.devoria.listeners;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.player.PlayerStats;
import me.devoria.utils.FastUtils;
import me.devoria.utils.ItemUtils;
import me.devoria.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
    private final Devoria plugin = Devoria.getInstance();
    CooldownManager cooldownManager = plugin.getCdInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        cooldownManager.createContainer(p.getUniqueId());
        PlayerStats pData = PlayerStats.getStats(p.getUniqueId());
        pData.save();

        p.sendMessage("§aWelcome to Eternia!");
        //Health Bar
        new BukkitRunnable() {
            final int log = p.getStatistic(Statistic.LEAVE_GAME);

            @Override
            public void run() {
                if (p.getStatistic(Statistic.LEAVE_GAME) != log) {
                    cancel(); // this cancels it when they leave
                }
                PlayerUtils.updateHealthBar(p);
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 40L);
        //HPR
        new BukkitRunnable() {
            final int log = p.getStatistic(Statistic.LEAVE_GAME);

            @Override
            public void run() {
                if (p.getStatistic(Statistic.LEAVE_GAME) != log) {
                    cancel(); // this cancels it when they leave
                }

                int hpr = PlayerUtils.calculate(p);
                PlayerUtils.changeHealth(p, hpr, null, false);
                PlayerUtils.updateHealthBar(p);
            }
        }.runTaskTimer(Devoria.getInstance(), 100L, 100L);

        if (p.getMetadata("healthStats").size() == 0) {
            p.setMetadata("healthStats", new FixedMetadataValue(Devoria.getInstance(), ",currentHealth:1000"));
        }
        ItemUtils.updateAttributes(p, -1);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        CooldownManager cooldownManager = plugin.getCdInstance();
        cooldownManager.removeContainer(uuid);
        PlayerStats playerData = PlayerStats.getStats(uuid);
        playerData.saveAndDelete();
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Entity arrow = event.getProjectile();
        Player player = (Player) event.getEntity();

        if (!(player.getScoreboardTags().contains("homing"))) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isDead()) {
                    World world = arrow.getWorld();
                    Location loc = arrow.getLocation();
                    world.spawnParticle(Particle.FLAME, loc, 175, 0, 0, 0, 0.175);
                    world.spawnParticle(Particle.SMOKE_LARGE, loc, 50, 0, 0, 0, 0.3);
                    world.spawnParticle(Particle.EXPLOSION_NORMAL, loc, 50, 0, 0, 0, 0.3);
                    player.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                    for (Entity nearbyEntity : loc.getNearbyEntities(2, 2, 2)) {
                        if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.equals(player)) continue;
                        LivingEntity target = (LivingEntity) nearbyEntity;
                        target.damage(5, player);
                    }
                    this.cancel();
                } else if (arrow.isOnGround()) {
                    this.cancel();
                }

                List<Entity> nearest = arrow.getNearbyEntities(20, 20, 20);
                Entity target = null;
                for (Entity near : nearest) {
                    if (near != player && near instanceof LivingEntity && !near.isDead() && player.hasLineOfSight(near)) {
                        if (target == null) {
                            target = near;
                        } else if (arrow.getLocation().distanceSquared(near.getLocation()) < arrow.getLocation().distanceSquared(target.getLocation())) {
                            target = near;
                        }
                    }
                }
                if (target == null) return;
                arrow.setVelocity(target.getLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(2));
            }
        }.runTaskTimer(plugin, 5L, 1L);
    }

    @EventHandler
    public void updateIem(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        ItemUtils.updateAttributes(p, e.getNewSlot());
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) throws FileNotFoundException {
        if (e.getMaterial().equals(Material.PINK_WOOL)) {

            Player p = e.getPlayer();
            String stats = p.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();

            try {
                p.getInventory().setItemInMainHand((ItemUtils.updateItem(stats)));


                e.setCancelled(true);

                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {


                    Arrow arrow = p.getWorld().spawn(p.getEyeLocation(),
                            Arrow.class);
                    arrow.setShooter(p);
                    arrow.setVelocity(p.getLocation().getDirection().multiply(2.25));
                }
            } catch (NullPointerException ignore) {
            }
        }

    }

    @EventHandler
    public void playerLeftClick(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        // PlayerInteractEvent doesn't work with LEFT_CLICK_BLOCK in adventure mode, so using this for that.
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || event.getAnimationType() != PlayerAnimationType.ARM_SWING || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType()))
            return;
        long cooldown = 10;
        PlayerStats playerStats = PlayerStats.getStats(player.getUniqueId());

        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(Action.LEFT_CLICK_AIR);
            cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);
        }
    }

    @EventHandler
    public void playerHitByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow && event.getDamager().getScoreboardTags().contains("arrowRainArrow")) {
            event.setDamage(5);
        }

        if (!(event.getEntity() instanceof Player)) return;
        if (event.getDamager() instanceof Arrow && event.getDamager().getScoreboardTags().contains(event.getEntity().getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || action == Action.LEFT_CLICK_AIR || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType()))
            return;
        long cooldown = (10);
        PlayerStats playerStats = PlayerStats.getStats(player.getUniqueId());

        cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);

        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(action);
            return;
        }

        playerStats.spellTriggers.enterSpellMode();
    }


    @EventHandler
    public void playerHit(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        switch (event.getCause()) {
            case FALL:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerAttemptPickupItemEvent event) {
        HashMap<String, String> map = FastUtils.map(event.getItem().getItemStack().getItemMeta().getLocalizedName());

        if (!event.getPlayer().equals(Bukkit.getPlayer(UUID.fromString(map.get("owner"))))) {
            event.setCancelled(true);
        }
    }

}
