package me.devoria.core.DataBase;

import me.devoria.core.Player;
import org.bukkit.Bukkit;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerTable {





    public static boolean register(Player uuid, String username) {

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


    public boolean remove(){
        DBconnect.disconnect();
        return false;
    }

    public static boolean Verify(Player uuid) throws SQLException {

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
        }


        return true;

        }

}

