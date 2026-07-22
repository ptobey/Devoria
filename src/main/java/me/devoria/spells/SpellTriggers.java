package me.devoria.spells;

import me.devoria.Devoria;
import me.devoria.cooldowns.CooldownManager;
import me.devoria.player.PlayerStats;
import me.devoria.utils.PlayerUtils;
import me.devoria.utils.SpellUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SpellTriggers {
    private static final String EMPTY_INPUT_MESSAGE = ChatColor.GREEN.toString()
            + ChatColor.UNDERLINE + "R" + ChatColor.RESET + ChatColor.RED + " _ _";

    public Devoria plugin = Devoria.getInstance();
    public CooldownManager cooldownManager = plugin.getCdInstance();
    public Player player;

    public SpellTriggers(Player player) {
        this.player = player;
    }

    //region Normal Spell
    public String currentMessage = EMPTY_INPUT_MESSAGE;
    public boolean spellMode = false;
    public int clicksSoFar = 0;
    public boolean[] spellClicks = new boolean[2]; //left = false, right = true.
    private BukkitTask inactivityTimer;

    public void enterSpellMode(Player player) {
        cancelPendingInput();
        this.player = player;
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5f, 1f);
        spellMode = true;
        PlayerUtils.updateHealthBar(player);
        inactivityTimer = new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.RED + "Spell cancelled due to inactivity.");
                inactivityTimer = null;
                resetInputState();
                PlayerUtils.updateHealthBar(player);
            }
        }.runTaskLater(plugin, 60L);
    }

    public void continueNormalSpell(Action clickType) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5f, 0.8f);
        boolean click = false;
        if (clickType.equals(Action.RIGHT_CLICK_AIR) || clickType.equals(Action.RIGHT_CLICK_BLOCK)) {
            click = true;
        }
        if (clicksSoFar == 0) {
            currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R R" + ChatColor.RESET + ChatColor.RED + " _" :
                    /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R L" + ChatColor.RESET + ChatColor.RED + " _";
        } else {
            // If our first click was a left click
            if (!spellClicks[0]) {
                currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R L R" :
                        /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R L L";
            } else {
                currentMessage = (click) ? /*IF WE ARE RIGHT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R R R" :
                        /*IF WE ARE LEFT CLICKING*/ ChatColor.GREEN.toString() + ChatColor.UNDERLINE + "R R L";
            }
        }
        PlayerUtils.updateHealthBar(player);
        if (clicksSoFar < 2) {
            spellClicks[clicksSoFar] = click;
        }
        clicksSoFar += 1;
        if (clicksSoFar == 2) {
            finishSpell();
            resetInputState();
        }
    }

    public void finishSpell() {
        cancelInactivityTimer();
        boolean firstClick = spellClicks[0];
        boolean secondClick = spellClicks[1];
        SpellType type = spellType(firstClick, secondClick);
        PlayerStats stats = PlayerStats.getStats(player.getUniqueId());
        SpellCastRule rule = plugin.getRuntimeConfiguration()
                .spellCasts().ruleFor(type);

        SpellCastTransaction.Result result = SpellCastTransaction.attempt(
                resources(stats), rule, type.cooldownKey(),
                isCastStateValid(stats, type),
                () -> SpellUtils.redirect(player, player.getUniqueId(), type));
        showCastResult(type, stats, result);
    }

    private SpellType spellType(boolean firstClick, boolean secondClick) {
        if (!firstClick && secondClick) {
            return SpellType.BASE;
        }
        if (firstClick && !secondClick) {
            return SpellType.UTIL;
        }
        if (!firstClick) {
            return SpellType.HEAVY;
        }
        return SpellType.MOVEMENT;
    }

    private boolean isCastStateValid(PlayerStats stats, SpellType type) {
        if (!player.isOnline() || player.isDead() || !stats.spellMode) {
            return false;
        }
        if (type != SpellType.MOVEMENT) {
            return true;
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.SLOW_FALLING)) {
                return false;
            }
        }
        return true;
    }

    private SpellCastTransaction.Resources resources(PlayerStats stats) {
        return new SpellCastTransaction.Resources() {
            @Override
            public int currentMana() {
                return stats.getMana();
            }

            @Override
            public int maxMana() {
                return stats.getMaxMana();
            }

            @Override
            public long cooldownRemaining(String cooldownKey) {
                return cooldownManager.getCooldownLeft(
                        player.getUniqueId(), cooldownKey);
            }

            @Override
            public void commit(int newMana, String cooldownKey,
                    int cooldownMillis) {
                stats.setMana(newMana);
                cooldownManager.setCooldownFromNow(player.getUniqueId(),
                        cooldownKey, (long) cooldownMillis);
            }
        };
    }

    private void showCastResult(SpellType type, PlayerStats stats,
            SpellCastTransaction.Result result) {
        switch (result.outcome()) {
            case SUCCESS -> {
                currentMessage = ChatColor.GREEN + "Casted! " + stats.getMana()
                        + "/" + stats.getMaxMana() + " mana";
                player.sendMessage(ChatColor.GREEN + "Cast " + type.displayName()
                        + ". Mana: " + stats.getMana() + "/" + stats.getMaxMana() + ".");
            }
            case ACTIVE_COOLDOWN -> {
                currentMessage = ChatColor.RED + "Cooldown: "
                        + result.remainingCooldownSeconds() + "s";
                player.sendMessage(ChatColor.RED + "That ability is on cooldown for "
                        + result.remainingCooldownSeconds() + " more second(s).");
                playFailureSound();
            }
            case INSUFFICIENT_MANA -> {
                currentMessage = ChatColor.RED + "Need " + result.manaCost() + " mana";
                player.sendMessage(ChatColor.RED + "That ability requires "
                        + result.manaCost() + " mana; you have " + stats.getMana() + ".");
                playFailureSound();
            }
            case INVALID_STATE -> {
                currentMessage = ChatColor.RED + "Cast cancelled";
                player.sendMessage(ChatColor.RED
                        + "You cannot cast that ability in your current state.");
                playFailureSound();
            }
            case REJECTED -> {
                currentMessage = ChatColor.RED + "No spell equipped";
                player.sendMessage(ChatColor.RED
                        + "You do not have a spell equipped in that slot.");
                playFailureSound();
            }
        }
        PlayerUtils.updateHealthBar(player);
    }

    private void playFailureSound() {
        player.getWorld().playSound(player.getLocation(),
                Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 0.5f);
    }

    /** Cancels an unfinished click sequence without casting or starting cooldowns. */
    public void cancelPendingInput() {
        cancelInactivityTimer();
        resetInputState();
    }

    public boolean hasPendingInput() {
        return spellMode;
    }

    private void cancelInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.cancel();
            inactivityTimer = null;
        }
    }

    private void resetInputState() {
        spellMode = false;
        clicksSoFar = 0;
        spellClicks = new boolean[2];
        currentMessage = EMPTY_INPUT_MESSAGE;
    }
    //endregion
    //region Weapon Spell
    //endregion
    //region Ultimate Spell

    //endregion
}
