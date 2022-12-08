package me.devoria.player;

import java.util.HashMap;
import java.util.UUID;
import me.devoria.spells.Spell;
import me.devoria.spells.SpellTriggers;
import me.devoria.spells.lightseekers.elves.ElfSpells;
import org.bukkit.entity.Player;

public class PlayerStats {
    public static HashMap<UUID, PlayerStats> playerStats = new HashMap<>();
    public Player player;
    public SpellTriggers spellTriggers;
    public boolean spellMode = false;
    public FactionType faction;
    public AffinityType affinity;
    public Spell[] spells = {
            ElfSpells.LIGHT_SPEAR,
            ElfSpells.EYE_OF_LIGHT,
            ElfSpells.ARROW_RAIN,
            ElfSpells.LEAP_OF_FATE
    };

    public PlayerStats(Player player, SpellTriggers spellTriggers, FactionType faction, AffinityType affinity) {
        this.player = player;
        this.spellTriggers = spellTriggers;
        this.faction = faction;
        this.affinity = affinity;
    }
    
    public static PlayerStats getStats(Player player, UUID pUUID) {
        if (playerStats.containsKey(pUUID)) {
            return playerStats.get(pUUID);
        } else {
            return createStats(player, pUUID);
        }
    }
    
    public static PlayerStats createStats(Player player, UUID pUUID) {
        PlayerStats stats = new PlayerStats(player, new SpellTriggers(player), FactionType.LIGHTSEEKER, AffinityType.ANGEL);
        playerStats.put(pUUID, stats);
        return stats;
    }
}
