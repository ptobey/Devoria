package me.devoria.utils;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;

public class BlockUtils {
    private static Set<Material> WATER_LOGGED = Set.of(
            Material.BUBBLE_COLUMN,
            Material.KELP,
            Material.KELP_PLANT,
            Material.SEAGRASS,
            Material.TALL_SEAGRASS
    );
    public static boolean isWaterlogged(BlockState block) {
        BlockData data = block.getBlockData();
        if (data instanceof Waterlogged) {
            return ((Waterlogged) data).isWaterlogged();
        }
        return false;
    }

    public static boolean isWaterSource(BlockState block) {
        if (isWaterlogged(block)) {
            return true;
        }
        if (WATER_LOGGED.contains(block.getType())) {
            return true;
        }
        if (block.getType().equals(Material.WATER) && block.getBlockData() instanceof Levelled levelled) {
            return levelled.getLevel() == 0;
        }
        return false;
    }
}
