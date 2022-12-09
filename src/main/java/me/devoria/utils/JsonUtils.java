package me.devoria.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.devoria.adapters.PlayerStatsAdapter;
import me.devoria.player.PlayerStats;

public class JsonUtils {
    private static GsonBuilder BUILDER = new GsonBuilder().registerTypeAdapter(PlayerStats.class, new PlayerStatsAdapter()).disableHtmlEscaping();
    public static Gson GSON;

    static {
        GSON = BUILDER.setPrettyPrinting().create();
    }
}
