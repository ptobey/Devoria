package me.devoria.core.DataBase;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Item_Stack {


    public static boolean Verify_IS(UUID uuid,String class_name) throws SQLException {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT itemstack FROM Inventory WHERE uuid=? AND class_name=? ");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
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

    public static void insertItemStack(UUID uuid, String class_name, String item_stack) throws SQLException {


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("INSERT INTO Inventory (uuid, Username, itemstack) VALUES (?, ?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ps.setString(3, item_stack.toString());
            ps.executeUpdate();




        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert item stack ");

        }


    }




    public static void updateItemStack(UUID uuid, String class_name, String item_stack) throws SQLException {

        if(Verify_IS(uuid, class_name)) {

            try {
                PreparedStatement ps;
                Connection connection = DBconnect.getConnection();
                ps = connection.prepareStatement("UPDATE Inventory SET itemstack=? WHERE uuid=? AND class_name=? ");
                ps.setString(1, item_stack.toString());
                ps.setString(2, uuid.toString());
                ps.setString(3, class_name);
                ps.executeUpdate();




            } catch (SQLException e) {
                Bukkit.getLogger().info(e.toString());
                Bukkit.getLogger().info("Could not update item stack ");

            }

        }else{

            insertItemStack(uuid,class_name,item_stack);


        }


    }


    public static String getItemStack(UUID uuid, String class_name) {


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT itemstack FROM Inventory WHERE uuid=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ResultSet resultSet = ps.executeQuery();

            return  resultSet.toString();


        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not pull item stack ");
        }


        return null;
    }

}
