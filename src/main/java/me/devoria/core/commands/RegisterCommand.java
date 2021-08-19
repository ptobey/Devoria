package me.devoria.core.commands;

import jdk.internal.jimage.ImageLocation;
import me.devoria.core.DataBase.DBconnect;
import me.devoria.core.DataBase.PlayerTable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;
import java.util.UUID;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {
            DBconnect.connect();

        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database NOT Connected!");
        }

        Bukkit.getLogger().info("Database Connected!");


        if(label.equalsIgnoreCase("register")){

            if(sender instanceof Player){
                return true;
            }

            Player player = null;
            UUID uuid = player.getUniqueId();
            String username = player.getName();

            try {
                if(PlayerTable.Verify(uuid)){
                    sender.sendMessage("Player Already Registered!");
                    return true;
                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
                Bukkit.getLogger().info("Problem verifying player!");
            }

            PlayerTable.register(uuid,username);
            sender.sendMessage("You have been registered!");



        }


        return false;
    }
}
