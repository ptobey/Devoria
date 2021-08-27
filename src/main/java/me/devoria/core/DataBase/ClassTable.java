package me.devoria.core.DataBase;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClassTable {

    public static boolean Verify_Class(UUID uuid) throws SQLException {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT Current_Class FROM CLASS WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if(results.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table CLASS");
            return false;
        }


    }

    public static void insertClass(UUID uuid, String class_name) throws SQLException {


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("INSERT INTO CLASS (uuid, Current_Class) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ps.executeUpdate();




        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert class ");

        }


    }




    public static String FindCurrentClass(UUID uuid){


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT Current_Class FROM CLASS WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            results.next();

            String c_class = results.getString("Current_Class");

            if(c_class == null){
                insertClass(uuid,"huntsman");
            }
            else {
                return c_class;
            }



        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table players");

        }

        return null;



    }

    public static boolean SetCurrentClass(UUID uuid, String c_class) throws SQLException {

        if (Verify_Class(uuid)) {
            try {
                PreparedStatement ps;
                Connection connection = DBconnect.getConnection();
                ps = connection.prepareStatement("UPDATE CLASS SET Current_Class=? WHERE uuid=?");
                ps.setString(1, c_class);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
                Bukkit.getLogger().info("I`m here 2");

                return true;


            } catch (SQLException e) {
                Bukkit.getLogger().info(e.toString());
                Bukkit.getLogger().info("Could not set current class ");
                Bukkit.getLogger().info("I`m here 4");
                return false;

            }

         }else{
            insertClass(uuid,c_class);
            return true;

        }
    }
}


