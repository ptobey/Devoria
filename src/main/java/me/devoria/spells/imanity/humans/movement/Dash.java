package me.devoria.spells.imanity.humans.movement;

import me.devoria.cooldowns.CooldownManager;
import me.devoria.spells.Spell;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Dash extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.setVelocity(p.getLocation().getDirection().multiply(new Vector(2,  0 /*get rid of the y velocity to make it just straight forward.*/, 2)));

        //Particles
    }

    @Override
    public String toString() {
        return "Dash";
    }
}
