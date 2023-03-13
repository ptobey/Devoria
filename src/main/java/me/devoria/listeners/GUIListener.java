package me.devoria.listeners;

import me.devoria.guis.AffinityGUI;
import me.devoria.guis.FactionGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        switch (event.getView().getTitle()) {
            case FactionGUI.invName -> FactionGUI.clickedGUI(event);
            case AffinityGUI.invName -> AffinityGUI.clickedGUI(event);
        }
    }
}
