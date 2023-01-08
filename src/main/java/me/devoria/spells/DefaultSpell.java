package me.devoria.spells;

import me.devoria.cooldowns.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DefaultSpell extends Spell {
    @Override
    public void cast(Player p, CooldownManager cooldownManager) {
        p.sendMessage(ChatColor.RED + "You don't have a spell equipped in this slot.");
    }

    @Override
    public String toString() {
        return "Default";
    }
}
