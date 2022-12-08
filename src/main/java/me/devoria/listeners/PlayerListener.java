package me.devoria.listeners;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.player.PlayerStats;
import me.devoria.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
    private final Devoria plugin = Devoria.getInstance();
    CooldownManager cooldownManager = plugin.getCdInstance();
    public PlayerListener(Devoria plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        UUID pUUID = event.getPlayer().getUniqueId();
        CooldownManager cooldownManager = plugin.getCdInstance();
        cooldownManager.createContainer(pUUID);
        //PlayerStats stats = PlayerStats.getStats(event.getPlayer());
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
                        if (!(nearbyEntity instanceof LivingEntity)) continue;
                        LivingEntity target = (LivingEntity) nearbyEntity;
                        target.damage(5);
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
    public void playerLeftClick(PlayerAnimationEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Player player = event.getPlayer();
        // PlayerInteractEvent doesn't work with LEFT_CLICK_BLOCK in adventure mode, so using this for that.
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || event.getAnimationType() != PlayerAnimationType.ARM_SWING || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType())) return;
        long cooldown = 10;
        PlayerStats playerStats = PlayerStats.getStats(player, player.getUniqueId());
        cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);
        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(Action.LEFT_CLICK_AIR);
        }
    }

    @EventHandler
    public void playerHit(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        switch (event.getCause()) {
            case FALL:
                event.setDamage(event.getDamage() * 0.8);
                break;
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
    public void playerRightClick(PlayerInteractEvent event) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (!cooldownManager.isCooldownDone(player.getUniqueId(), "Spell Click") || action == Action.LEFT_CLICK_AIR || !ItemUtils.weapons.contains(player.getInventory().getItemInMainHand().getType())) return;
        long cooldown = (10);
        PlayerStats playerStats = PlayerStats.getStats(player, player.getUniqueId());

        cooldownManager.setCooldownFromNow(player.getUniqueId(), "Spell Click", cooldown);

        if (playerStats.spellTriggers.spellMode) {
            playerStats.spellTriggers.continueNormalSpell(action);
            return;
        }

        playerStats.spellTriggers.enterSpellMode();
    }
}
