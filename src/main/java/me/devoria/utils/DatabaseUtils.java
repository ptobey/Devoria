package me.devoria.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import org.bukkit.Bukkit;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class DatabaseUtils {
    public static boolean register(UUID uuid, String username) {
        try {
            PreparedStatement ps;
            Connection connection = DatabaseUtils.getConnection();
            ps = connection.prepareStatement("INSERT INTO Players (uuid, Username) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, username);
            ps.executeUpdate();


        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert into table players");
        }

        return false;
    }


    public boolean remove(UUID uuid) {
        try {
            PreparedStatement ps;
            Connection connection = DatabaseUtils.getConnection();
            ps = connection.prepareStatement("DELETE FROM Players WHERE UUID=?)");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not delete player");
        }
        return false;
    }

    public static boolean verify(UUID uuid) throws SQLException {
        try {
            PreparedStatement ps;
            Connection connection = DatabaseUtils.getConnection();
            ps = connection.prepareStatement("SELECT * FROM Players WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table players");
            return false;
        }
    }

    public static boolean verifyIS(UUID uuid, String class_name) throws SQLException {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
            ps = connection.prepareStatement("SELECT itemstack,class_name,class_location FROM Inventory WHERE uuid=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);

            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table players");
            return false;
        }
    }

    public static void insertItemStack(UUID uuid, String class_name, String item_stack, String class_location) throws SQLException {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
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

    public static void updateItemStack(UUID uuid, String class_name, String item_stack, String class_location) throws SQLException {
        if (verifyIS(uuid, class_name)) {
            try {
                PreparedStatement ps;
                Connection connection = getConnection();
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
        } else {
            insertItemStack(uuid, class_name, item_stack, class_location);
        }
    }

    public static ItemStack[] getItemStack(UUID uuid, String class_name) {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
            ps = connection.prepareStatement("SELECT itemstack FROM Inventory WHERE uuid=? AND class_name=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ResultSet resultSet = ps.executeQuery();
            StringBuilder sb = new StringBuilder();

            while (resultSet.next()) {
                byte[] buffer = new byte[1024];
                InputStream in = resultSet.getBinaryStream("itemstack");
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
            }
            String data = sb.toString();
            ItemStack[] items = InventoryUtils.itemStackArrayFromBase64(data);
            return items;
        } catch (SQLException | IOException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not pull item stack ");
        }
        return null;
    }

    public static boolean refactorClass(UUID uuid) throws SQLException {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
            ps = connection.prepareStatement("SELECT Current_Class FROM CLASS WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table CLASS");
            return false;
        }
    }

    public static void insertClass(UUID uuid, String class_name) throws SQLException {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
            ps = connection.prepareStatement("INSERT INTO CLASS (uuid, Current_Class) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, class_name);
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not insert class ");
        }
    }

    public static String findCurrentClass(UUID uuid) {
        try {
            PreparedStatement ps;
            Connection connection = getConnection();
            ps = connection.prepareStatement("SELECT Current_Class FROM CLASS WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet results = ps.executeQuery();

            results.next();

            String c_class = results.getString("Current_Class");

            if (c_class == null) {
                insertClass(uuid, "huntsman");
            } else {
                return c_class;
            }

        } catch (SQLException e) {
            Bukkit.getLogger().info(e.toString());
            Bukkit.getLogger().info("Could not verify table players");
        }

        return null;
    }

    public static void setCurrentClass(UUID uuid, String c_class) throws SQLException {

        if (refactorClass(uuid)) {
            try {
                PreparedStatement ps;
                Connection connection = getConnection();
                ps = connection.prepareStatement("UPDATE CLASS SET Current_Class=? WHERE uuid=?");
                ps.setString(1, c_class);
                ps.setString(2, uuid.toString());
                ps.executeUpdate();
                Bukkit.getLogger().info("I`m here 2");

            } catch (SQLException e) {
                Bukkit.getLogger().info(e.toString());
                Bukkit.getLogger().info("Could not set current class ");
                Bukkit.getLogger().info("I`m here 4");
            }

        } else {
            insertClass(uuid, c_class);

        }
    }

    private static Connection connection;

    public static boolean isConnected() {
        return (connection == null ? false : true);
    }


    public static void connect() throws ClassNotFoundException, SQLException {

        String host = "mysql.mcprohosting.com";
        String port = "3306";
        String database = "test";
        String username = "server_1046151";
        String password = "NS7MRXIhHUxsdelJ#i$gw";

        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database +
                    "?useSSL=false", username, password);
        }
    }

    public static void disconnect() {

        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Do absolutely nothing.
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

