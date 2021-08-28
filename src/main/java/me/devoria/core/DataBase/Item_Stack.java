package me.devoria.core.DataBase;

import me.devoria.core.Iventory.SerializeInventory;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.Blob;

public class Item_Stack {


    public static boolean Verify_IS(UUID uuid,String class_name) throws SQLException {

        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT itemstack,class_name,class_location FROM Inventory WHERE uuid=? AND class_name=?");
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

    public static void insertItemStack(UUID uuid, String class_name, String item_stack,String class_location) throws SQLException {


        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("INSERT INTO Inventory (uuid, class_name, itemstack, class_location) VALUES (?, ?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ps.setString(3, item_stack.toString());
            ps.setString(4, class_location);
            ps.executeUpdate();




        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert item stack ");

        }


    }




    public static void updateItemStack(UUID uuid, String class_name, String item_stack,String class_location) throws SQLException {

        if(Verify_IS(uuid, class_name)) {

            try {
                PreparedStatement ps;
                Connection connection = DBconnect.getConnection();
                ps = connection.prepareStatement("UPDATE Inventory SET itemstack=?,class_location=? WHERE uuid=? AND class_name=? ");
                ps.setString(1, item_stack.toString());
                ps.setString(2, class_location);
                ps.setString(3, uuid.toString());
                ps.setString(4, class_name);
                ps.executeUpdate();




            } catch (SQLException e) {
                Bukkit.getLogger().info(e.toString());
                Bukkit.getLogger().info("Could not update item stack ");

            }

        }else{

            insertItemStack(uuid,class_name,item_stack,class_location);


        }


    }


    public static ItemStack[] getItemStack(UUID uuid, String class_name) {



        try {
            PreparedStatement ps;
            Connection connection = DBconnect.getConnection();
            ps = connection.prepareStatement("SELECT itemstack FROM Inventory WHERE uuid=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ResultSet resultSet = ps.executeQuery();
            StringBuilder sb = new StringBuilder();

            while (resultSet.next()){
                //String items  =resultSet.getString("itemstack");

                byte[] buffer =new byte[1024];
                InputStream in = resultSet.getBinaryStream("itemstack");
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line = br.readLine();
                while(line != null){

                    sb.append(line);
                    line =br.readLine();

                }

            }
            String data = sb.toString();

            //Blop Blop = resultSet.getBlob("itemstack");

            ItemStack[] items =SerializeInventory.itemStackArrayFromBase64(data);

            return items;


        }catch (SQLException | IOException e){
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not pull item stack ");
        }


        return null;
    }

}
