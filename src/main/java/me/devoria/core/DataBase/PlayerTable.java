package me.devoria.core.DataBase;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerTable {





    public static boolean register(UUID uuid, String username) {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("INSERT INTO Players (uuid, Username) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, username);
            ps.executeUpdate();


        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert into table players");
        }

        return false;
    }


    public boolean remove(UUID uuid){


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("DELETE FROM Players WHERE UUID=?)");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();


        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not delete player");
        }

        return false;

    }

    public static boolean Verify(UUID uuid) throws SQLException {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Players WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if(results.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table players");
            return false;
        }


    }

}

