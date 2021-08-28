package me.devoria.core.DataBase;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LocationSv {


    public static boolean Verify_location(UUID uuid, String className) throws SQLException {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT class_location FROM Inventory WHERE uuid=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, className);
            ResultSet results = ps.executeQuery();

            if(results.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table iventory for class_location");
            return false;
        }


    }

    public static void insertLocation(UUID uuid, String class_location, String class_name) throws SQLException {


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("INSERT INTO Inventory (class_location) VALUES (?) WHERE uuid=? AND class_name=?");
            ps.setString(1, class_location);
            ps.setString(2, uuid.toString());
            ps.setString(3, class_name);
            ps.executeUpdate();




        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert class location ");

        }


    }


    public static String FindClassLoc(UUID uuid, String class_name){


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT class_location FROM Iventory WHERE UUID=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ResultSet results = ps.executeQuery();

            results.next();

            String location = results.getString("class_location");




        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not find class_location");

        }

        return null;



    }

    public static boolean SetClassLoc(UUID uuid, String class_location,String class_name) throws SQLException {

        if (Verify_location(uuid,class_name)) {
            try {
                PreparedStatement ps;
                Connection connection = DBconnect.getConnection();
                ps = connection.prepareStatement("UPDATE Inventory SET class_location=? WHERE uuid=? AND class_name=?");
                ps.setString(1, class_location);
                ps.setString(2, uuid.toString());
                ps.setString(3,class_name);
                ps.executeUpdate();

                return true;


            } catch (SQLException e) {
                Bukkit.getLogger().info(e.toString());
                Bukkit.getLogger().info("Could not set current class location ");
                return false;

            }

        }else{
            insertLocation(uuid,class_location,class_name);
            return true;

        }
    }



}
