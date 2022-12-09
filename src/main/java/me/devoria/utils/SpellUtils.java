package me.devoria.utils;

import java.util.UUID;
import me.devoria.Devoria;
import me.devoria.player.PlayerStats;
import me.devoria.spells.Spell;
import me.devoria.spells.SpellType;
import org.bukkit.entity.Player;

public class SpellUtils {
    public static void redirect(Player player, UUID pUUID, SpellType spellType) {
        player.sendMessage("Redirecting...");
        PlayerStats stats = PlayerStats.getStats(pUUID);
        Spell spell = getSpell(spellType, stats);
        if (spell == null) return;
        player.sendMessage("Spell has been found!");
        spell.cast(player, Devoria.getInstance().getCdInstance());
    }

    public static Spell getSpell(SpellType spellType, PlayerStats stats) {
        switch(spellType) {
            case BASE:
                return stats.getSpells()[0];
            case UTIL:
                return stats.getSpells()[1];
            case HEAVY:
                return stats.getSpells()[2];
            case MOVEMENT:
                return stats.getSpells()[3];
        }
        return null;
    }
}
