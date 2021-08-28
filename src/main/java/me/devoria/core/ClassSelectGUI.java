package me.devoria.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ClassSelectGUI implements Listener {
    private final Inventory inv;

    public ClassSelectGUI() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 27, "Select a Class");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.setItem(10, createGuiItem(Material.BOW, "Huntsman", "§aTemp", "§bTemp"));
        inv.setItem(12, createGuiItem(Material.DIAMOND_SWORD, "Knight", "§aTemp", "§bTemp"));
        inv.setItem(14, createGuiItem(Material.DIAMOND_PICKAXE, "Bard", "§aTemp", "§bTemp"));
        inv.setItem(16, createGuiItem(Material.STICK, "Sorcerer", "§aTemp", "§bTemp"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (e.getInventory() != inv) return;

        final org.bukkit.entity.Player p = (org.bukkit.entity.Player) e.getWhoClicked();
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        assert clickedItem != null;
        if (clickedItem.getItemMeta().getDisplayName().equals("Huntsman")) {
            p.sendMessage("You selected the Huntsman class!");
            p.closeInventory();
            //Add database class select
        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Knight")) {
            p.sendMessage("You selected the Knight class!");
            p.closeInventory();
            //Add database class select
        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Bard")) {
            p.sendMessage("You selected the Bard class!");
            p.closeInventory();
            //Add database class select
        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Sorcerer")) {
            p.sendMessage("You selected the Sorcerer class!");
            p.closeInventory();
            //Add database class select
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
        }
    }
}