package me.devoria.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CastingUtils {
    // Create a map to store castingPlayers for each spell or class
    private static final Map<String, Set<Player>> castingPlayersMap = new HashMap<>();

    // Add a player to the set associated with the specified identifier
    public static void add(String identifier, Player player) {
        castingPlayersMap.computeIfAbsent(identifier, k -> new HashSet<>()).add(player);
    }

    // Remove a player from the set associated with the specified identifier
    public static void remove(String identifier, Player player) {
        Set<Player> castingPlayers = castingPlayersMap.get(identifier);
        if (castingPlayers != null) {
            castingPlayers.remove(player);
        }
    }

    // Check if a player is casting for the specified identifier
    public static boolean contains(String identifier, Player player) {
        Set<Player> castingPlayers = castingPlayersMap.get(identifier);
        return castingPlayers != null && castingPlayers.contains(player);
    }
}