package me.devoria.spells.imanity.humans.util;

import java.util.ArrayList;
import java.util.List;
import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import me.devoria.utils.FastUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AdventurersAura extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
        new BukkitRunnable() {
            List<ArmorStand> armorStandList = new ArrayList<>();
            int ticks = 0;
            final int maxTicks = 40;

            @Override
            public void run() {
                ticks++;
                if (ticks >= maxTicks) {
                    this.cancel();
                    for (ArmorStand armorStand : armorStandList) armorStand.remove();
                } else if (ticks > 20) {
                    for (ArmorStand armorStand : armorStandList) {
                        double movement = FastUtils.randomDoubleInRange(0, 0.5);
                        if (movement < 0.4) continue;
                        armorStand.teleport(armorStand.getLocation().add(0, movement, 0));
                        armorStand.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, armorStand.getLocation(), 1, 0, 0, 0);
                        armorStand.getWorld().spawnParticle(Particle.CLOUD, armorStand.getLocation(), 1, 0, 0, 0);
                    }
                } else {
                    for (ArmorStand armorStand : armorStandList) {
                        Vector vFrom = armorStand.getLocation().toVector();
                        Vector vTo = p.getLocation().toVector();
                        Vector diff = vTo.subtract(vFrom);
                        Vector normalized = diff.normalize();
                        armorStand.teleport(armorStand.getLocation().setDirection(normalized).add(normalized));
                        armorStand.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, armorStand.getLocation(), 1, 0, 0, 0);
                    }
                    Vector randomPoint = new Vector(FastUtils.randomDoubleInRange(-3, 3), FastUtils.randomDoubleInRange(-3, 3), FastUtils.randomDoubleInRange(-3, 3));
                    ArmorStand armorStand = p.getWorld().spawn(p.getLocation().add(randomPoint), ArmorStand.class);
                    armorStand.setMarker(true);
                    armorStand.setVisible(false);
                    armorStandList.add(armorStand);
                }
            }
        }.runTaskTimer(Devoria.getInstance(), 0L, 2L);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.2f, 0.8f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_HURT, 0.7f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1.2f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 0.7f, 0.4f);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 1.2f, 0.4f);
    }

    @Override
    public String toString() {
        return "AdventurersAura";
    }
}
