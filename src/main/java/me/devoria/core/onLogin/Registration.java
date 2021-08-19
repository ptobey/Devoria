package me.devoria.core.onLogin;

import me.devoria.core.DataBase.DBconnect;
import me.devoria.core.DataBase.PlayerTable;
import me.devoria.core.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class Registration {


    public static void registerUser(UUID uuid, String username){


        try {
            DBconnect.connect();
            Bukkit.getLogger().info("Database Connected!");

        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getLogger().info("Database NOT Connected!");
        }



            try {
                if(PlayerTable.Verify(uuid)){
                    //sender.sendMessage("You are already Registered!");
                    Bukkit.getLogger().info("Welcome back-> " + username);



                }
                if(!(PlayerTable.Verify(uuid))){

                    PlayerTable.register(uuid,username);
                    //sender.sendMessage("You have been registered!");
                    Bukkit.getLogger().info("Welcome to Devoria-> " + username);


                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
                Bukkit.getLogger().info("Problem verifying player!");
                Bukkit.getLogger().info(throwables.toString());
            }







    }
}
