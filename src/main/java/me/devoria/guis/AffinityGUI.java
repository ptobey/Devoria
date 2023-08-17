package me.devoria.guis;

import me.devoria.player.AffinityType;
import me.devoria.player.FactionType;
import me.devoria.player.PlayerStats;
import me.devoria.spells.Spell;
import me.devoria.spells.imanity.demigods.DemigodSpells;
import me.devoria.spells.imanity.humans.HumanSpells;
import me.devoria.spells.imanity.knights.KnightSpells;
import me.devoria.spells.imanity.mages.MageSpells;
import me.devoria.spells.lightseekers.angels.AngelSpells;
import me.devoria.spells.lightseekers.aquans.AquanSpells;
import me.devoria.spells.lightseekers.astreans.AstreanSpells;
import me.devoria.spells.lightseekers.elves.ElfSpells;
import me.devoria.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AffinityGUI {
    private static final int dpsSlot = 10;
    private static final int rangedSlot = 16;
    private static final int tankSlot = 37;
    private static final int balanceSlot = 43;
    public static final String invName = "Affinity Selector";

    public static void openGUI(Player player, FactionType type) {
        ItemStack[] classes = switch (type) {
            case IMANITY -> new ItemStack[]{
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Demigod", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Mage", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Ancient Knight", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Human", "Lore Required")
            };
            case LIGHTSEEKER -> new ItemStack[]{
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Aquan", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Elf", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Angel", "Lore Required"),
                    ItemUtils.getItem(new ItemStack(Material.TOTEM_OF_UNDYING), "Astrean", "Lore Required")
            };
            default -> new ItemStack[]{
                    ItemUtils.getItem(new ItemStack(Material.BARRIER), "ERROR", "Please contact a moderator if you are seeing this."),
                    ItemUtils.getItem(new ItemStack(Material.BARRIER), "ERROR", "Please contact a moderator if you are seeing this."),
                    ItemUtils.getItem(new ItemStack(Material.BARRIER), "ERROR", "Please contact a moderator if you are seeing this."),
                    ItemUtils.getItem(new ItemStack(Material.BARRIER), "ERROR", "Please contact a moderator if you are seeing this.")
            };
        };

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
        inv.setItem(dpsSlot, classes[0]);
        inv.setItem(11, ItemUtils.getItem(new ItemStack(Material.GREEN_STAINED_GLASS_PANE), ""));
        for (int i = 12; i < 15; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(15, ItemUtils.getItem(new ItemStack(Material.PINK_STAINED_GLASS_PANE), ""));
        inv.setItem(rangedSlot, classes[1]);
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
        inv.setItem(tankSlot, classes[2]);
        inv.setItem(38, ItemUtils.getItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), ""));
        for (int i = 39; i < 42; i++) {
            inv.setItem(i, ItemUtils.getItem(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE), ""));
        }
        inv.setItem(42, ItemUtils.getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), ""));
        inv.setItem(balanceSlot, classes[3]);
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
        PlayerStats stats = PlayerStats.getStats(event.getWhoClicked().getUniqueId());
        switch (event.getSlot()) {
            case dpsSlot:
                switch (stats.getFaction()) {
                    case IMANITY -> {
                        stats.setAffinity(AffinityType.DEMIGOD);
                        stats.setSpells(new Spell[]{
                                DemigodSpells.SPINSLASH,
                                DemigodSpells.GOD_SCREAM,
                                DemigodSpells.GODS_CLAWS,
                                DemigodSpells.LUNGE
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    case LIGHTSEEKER -> {
                        stats.setAffinity(AffinityType.AQUAN);
                        stats.setSpells(new Spell[]{
                                AquanSpells.AQUAJET,
                                AquanSpells.OCEAN_POWER,
                                AquanSpells.WATER_PRISON,
                                AquanSpells.RIPWHIRL,
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    default -> {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Please contact an administrator or SleepingRaven#0604 on Discord if you see this message.");
                        event.getWhoClicked().closeInventory();
                    }
                }
                break;
            case rangedSlot:
                switch (stats.getFaction()) {
                    case IMANITY -> {
                        stats.setAffinity(AffinityType.MAGE);
                        stats.setSpells(new Spell[]{
                                MageSpells.MANA_PULL,
                                MageSpells.CHANNELING,
                                MageSpells.MANA_BURST,
                                MageSpells.TELEPORT,
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    case LIGHTSEEKER -> {
                        stats.setAffinity(AffinityType.ELF);
                        stats.setSpells(new Spell[]{
                                ElfSpells.LIGHT_SPEAR,
                                ElfSpells.EYE_OF_LIGHT,
                                ElfSpells.ARROW_RAIN,
                                ElfSpells.LEAP_OF_FATE
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    default -> {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Please contact an administrator or SleepingRaven#0604 on Discord if you see this message.");
                        event.getWhoClicked().closeInventory();
                    }
                }
                break;
            case tankSlot:
                switch (stats.getFaction()) {
                    case IMANITY -> {
                        stats.setAffinity(AffinityType.KNIGHT);
                        stats.setSpells(new Spell[]{
                                KnightSpells.HEAVY_SLAM,
                                KnightSpells.DIVINE_PROTECTION,
                                KnightSpells.KNIGHTS_JUSTICE,
                                KnightSpells.RAGE_LEAP,
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    case LIGHTSEEKER -> {
                        stats.setAffinity(AffinityType.ANGEL);
                        stats.setSpells(new Spell[]{
                                AngelSpells.ANGELIC_CHAOS,
                                AngelSpells.LAMENT,
                                AngelSpells.DIVINE_WRATH,
                                AngelSpells.FLIGHT,
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    default -> {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Please contact an administrator or SleepingRaven#0604 on Discord if you see this message.");
                        event.getWhoClicked().closeInventory();
                    }
                }
                break;
            case balanceSlot:
                switch (stats.getFaction()) {
                    case IMANITY -> {
                        stats.setAffinity(AffinityType.HUMAN);
                        stats.setSpells(new Spell[]{
                                HumanSpells.HEROIC_STRIKE,
                                HumanSpells.ADVENTURERS_AURA,
                                HumanSpells.ENERGY_BURST,
                                HumanSpells.DASH
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    case LIGHTSEEKER -> {
                        stats.setAffinity(AffinityType.ASTREAN);
                        stats.setSpells(new Spell[]{
                                AstreanSpells.LIGHT_SURGE,
                                AstreanSpells.LIGHT_PLAGUE,
                                AstreanSpells.OMNIBLAST,
                                AstreanSpells.WARP
                        });
                        stats.save();
                        event.getWhoClicked().closeInventory();
                    }
                    default -> {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Please contact an administrator or SleepingRaven#0604 on Discord if you see this message.");
                        event.getWhoClicked().closeInventory();
                    }
                }
                break;
        }
    }
}
