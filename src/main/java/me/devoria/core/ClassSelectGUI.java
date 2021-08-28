package me.devoria.core;

import me.devoria.core.DataBase.ClassTable;
import me.devoria.core.DataBase.Item_Stack;
import me.devoria.core.DataBase.LocationSv;
import me.devoria.core.Iventory.SerializeInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
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
    public void onInventoryClick(final InventoryClickEvent e) throws SQLException {
        if (e.getInventory() != inv) return;

        final org.bukkit.entity.Player p = (org.bukkit.entity.Player) e.getWhoClicked();
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        assert clickedItem != null;

        //handling player inventory
        PlayerInventory playerInventory = p.getInventory();
        String itemStack = SerializeInventory.playerInventoryToBase64(playerInventory);
        ItemStack[] items ={};

        //finding current class
        String c_class = "huntsman";
        if (!ClassTable.Verify_Class(p.getUniqueId())) {
            ClassTable.SetCurrentClass(p.getUniqueId(), c_class);
        }

        c_class = ClassTable.FindCurrentClass(p.getUniqueId());


        //class location
        String world = p.getLocation().getWorld().getName();
        Integer loc_x = p.getLocation().getBlockX();
        Integer loc_y = p.getLocation().getBlockY();
        Integer loc_z = p.getLocation().getBlockZ();

        String class_location = world + "|" + loc_x.toString() + "|" + loc_y.toString() + "|" + loc_z.toString();

        //updating previous class locations and inventory on db
        //LocationSv.SetClassLoc(p.getUniqueId(),class_location,c_class);
        if(!(Item_Stack.Verify_IS(p.getUniqueId(),c_class))){
            Item_Stack.insertItemStack(p.getUniqueId(),c_class," ", class_location);
        }
        Item_Stack.updateItemStack(p.getUniqueId(),c_class,itemStack,class_location);


        //updating player inventory and class
        if (clickedItem.getItemMeta().getDisplayName().equals("Huntsman")) {
            p.sendMessage("You selected the Huntsman class!");
            try {
                ClassTable.SetCurrentClass(p.getUniqueId(),"huntsman");
            } catch (SQLException throwables) {
                Bukkit.getLogger().info(throwables.toString());
                Bukkit.getLogger().info("Could not set class hunt on db");

            }
            p.getInventory().clear();
            items = Item_Stack.getItemStack(p.getUniqueId(),"huntsman");
            p.getInventory().setContents(items);
            p.closeInventory();
            p.updateInventory();

        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Knight")) {
            p.sendMessage("You selected the Knight class!");
            try {
                ClassTable.SetCurrentClass(p.getUniqueId(),"knight");

            } catch (SQLException throwables) {
                Bukkit.getLogger().info(throwables.toString());
                Bukkit.getLogger().info("Could not set class knight on db");
            }
            p.getInventory().clear();
            items = Item_Stack.getItemStack(p.getUniqueId(),"Knight");
            p.getInventory().setContents(items);
            p.closeInventory();
            p.updateInventory();

        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Bard")) {
            p.sendMessage("You selected the Bard class!");
            try {
                ClassTable.SetCurrentClass(p.getUniqueId(),"bard");
            } catch (SQLException throwables) {
                Bukkit.getLogger().info(throwables.toString());
                Bukkit.getLogger().info("Could not set class bard on db");
            }
            p.getInventory().clear();
            items = Item_Stack.getItemStack(p.getUniqueId(),"Bard");
            p.getInventory().setContents(items);
            p.closeInventory();
            p.updateInventory();

        }
        else if (clickedItem.getItemMeta().getDisplayName().equals("Sorcerer")) {
            p.sendMessage("You selected the Sorcerer class!");
            try {
                ClassTable.SetCurrentClass(p.getUniqueId(),"sorcerer");
            } catch (SQLException throwables) {
                Bukkit.getLogger().info(throwables.toString());
                Bukkit.getLogger().info("Could not set class sorcerer on db");
            }
            p.getInventory().clear();
            items = Item_Stack.getItemStack(p.getUniqueId(),"sorcerer");
            p.getInventory().setContents(items);
            p.closeInventory();
            p.updateInventory();
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