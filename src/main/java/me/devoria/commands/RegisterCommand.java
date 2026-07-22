package me.devoria.commands;


import me.devoria.utils.DatabaseUtils;
import me.devoria.Devoria;
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
            DatabaseUtils.connect();
            Devoria.getInstance().getLogger().info("Database connected");

        } catch (ClassNotFoundException | SQLException e) {
            sender.sendMessage("Database registration is unavailable: " + e.getMessage());
            return true;
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
                if(DatabaseUtils.verify(uuid)){
                    sender.sendMessage("You are already Registered!");
                    return true;

                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
                Devoria.getInstance().getLogger().warning("Problem verifying player: " + throwables.getMessage());
                sender.sendMessage("Could not verify registration status.");
                return true;
            }

            if (DatabaseUtils.register(uuid, username)) {
                sender.sendMessage("You have been registered!");
            } else {
                sender.sendMessage("Registration failed. Contact an administrator.");
            }
            return true;



        }

        return false;
    }
}
