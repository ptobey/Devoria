package me.devoria.guis;

import me.devoria.player.FactionType;
import me.devoria.player.PlayerStats;
import me.devoria.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FactionGUI {
    private static final int imanitySlot = 10;
    private static final int lightbringerSlot = 16;
    private static final int hellscapersSlot = 37;
    private static final int cavedwellersSlot = 43;
    public static final String invName = "Faction Selector";

    public static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9 * 6, invName);
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ""));
        }
        for (int i = 3; i < 6; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        for (int i = 6; i < 9; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.PINK_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(9, ItemUtils.getItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ""));
        inv.setItem(imanitySlot, ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING),"Imanity", "The children of God, these beings utilize life magic in all shapes and forms."));
        inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ""));
        for (int i = 12; i < 15; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(15, ItemUtils.getItem(new ItemStack(Material.PINK_STAINED_GLASS_PANE), ""));
        inv.setItem(lightbringerSlot, ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING),"Lightseekers", "The faithful servants of the light created by Akilion to protect and serve the light."));
        inv.setItem(17, ItemUtils.getItem(new ItemStack(Material.PINK_STAINED_GLASS_PANE), ""));
        for (int i = 18; i < 21; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ""));
        }
        for (int i = 21; i < 24; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        for (int i = 24; i < 27; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.PINK_STAINED_GLASS_PANE), ""));
        }
        for (int i = 27; i < 30; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), ""));
        }
        for (int i = 30; i < 33; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        for (int i = 33; i < 36; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(36, ItemUtils.getItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), ""));
        inv.setItem(hellscapersSlot, ItemUtils.getItem(new ItemStack(Material.BARRIER),"Hellscapers", "Coming Soon..."));
        inv.setItem(38, ItemUtils.getItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), ""));
        for (int i = 39; i < 42; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(42, ItemUtils.getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), ""));
        inv.setItem(cavedwellersSlot, ItemUtils.getItem(new ItemStack(Material.BARRIER),"Cavedwellers", "Coming Soon..."));
        inv.setItem(44, ItemUtils.getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), ""));
        for (int i = 45; i < 48; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), ""));
        }
        for (int i = 48; i < 51; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        for (int i = 51; i < 54; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), ""));
        }

        player.openInventory(inv);
    }

    public static void clickedGUI(InventoryClickEvent event) {
        event.setCancelled(true);
        PlayerStats stats = PlayerStats.getStats((Player) event.getWhoClicked(), event.getWhoClicked().getUniqueId());
        switch (event.getSlot()) {
            case imanitySlot:
                stats.faction = FactionType.IMANITY;
                AffinityGUI.openGUI((Player) event.getWhoClicked());
            case lightbringerSlot:
                stats.faction = FactionType.LIGHTSEEKER;
                AffinityGUI.openGUI((Player) event.getWhoClicked());
        }
    }
}
