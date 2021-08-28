package me.devoria.core.commands;


import me.devoria.core.DataBase.DBconnect;
import me.devoria.core.DataBase.PlayerTable;
import me.devoria.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try {
            DBconnect.connect();
            Bukkit.getLogger().info("Database Connected!");

        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database NOT Connected!");
        }





        if(label.equalsIgnoreCase("register")){


            if(!(sender instanceof Player)){

                return true;

            }


            String username = sender.getName();


            UUID uuid = ((Player) sender).getUniqueId();
            sender.sendMessage("Player username ->" + username);
            sender.sendMessage("Player uuid ->" + uuid);



            try {
                if(PlayerTable.Verify(uuid)){
                    sender.sendMessage("You are already Registered!");
                    return true;

                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
                Bukkit.getLogger().info("Problem verifying player!");
            }

            PlayerTable.register(uuid,username);
            sender.sendMessage("You have been registered!");
            return true;



        }

        return false;
    }
}
