package me.devoria.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import me.devoria.Devoria;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * Legacy JDBC access retained for compatibility while persistence is redesigned.
 * The integration is disabled by default and never contains built-in credentials.
 */
public final class DatabaseUtils {
    private static Connection connection;

    private DatabaseUtils() {
    }

    public static boolean register(UUID uuid, String username) {
        String sql = "INSERT INTO Players (uuid, Username) VALUES (?, ?)";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, username);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            logDatabaseError("Could not insert player", exception);
            return false;
        }
    }

    public static boolean remove(UUID uuid) {
        try (PreparedStatement statement = requireConnection()
                .prepareStatement("DELETE FROM Players WHERE UUID=?")) {
            statement.setString(1, uuid.toString());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            logDatabaseError("Could not delete player", exception);
            return false;
        }
    }

    public static boolean verify(UUID uuid) throws SQLException {
        try (PreparedStatement statement = requireConnection()
                .prepareStatement("SELECT 1 FROM Players WHERE UUID=?")) {
            statement.setString(1, uuid.toString());
            try (ResultSet results = statement.executeQuery()) {
                return results.next();
            }
        }
    }

    public static boolean verifyIS(UUID uuid, String className) throws SQLException {
        String sql = "SELECT 1 FROM Inventory WHERE uuid=? AND class_name=?";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, className);
            try (ResultSet results = statement.executeQuery()) {
                return results.next();
            }
        }
    }

    public static void insertItemStack(UUID uuid, String className, String itemStack,
            String classLocation) throws SQLException {
        String sql = "INSERT INTO Inventory (uuid, class_name, itemstack, class_location) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, className);
            statement.setString(3, itemStack);
            statement.setString(4, classLocation);
            statement.executeUpdate();
        }
    }

    public static void updateItemStack(UUID uuid, String className, String itemStack,
            String classLocation) throws SQLException {
        if (!verifyIS(uuid, className)) {
            insertItemStack(uuid, className, itemStack, classLocation);
            return;
        }

        String sql = "UPDATE Inventory SET itemstack=?, class_location=? WHERE uuid=? AND class_name=?";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, itemStack);
            statement.setString(2, classLocation);
            statement.setString(3, uuid.toString());
            statement.setString(4, className);
            statement.executeUpdate();
        }
    }

    public static ItemStack[] getItemStack(UUID uuid, String className) {
        String sql = "SELECT itemstack FROM Inventory WHERE uuid=? AND class_name=?";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            statement.setString(2, className);
            try (ResultSet results = statement.executeQuery()) {
                if (!results.next()) {
                    return new ItemStack[0];
                }
                return InventoryUtils.itemStackArrayFromBase64(results.getString("itemstack"));
            }
        } catch (SQLException | IOException exception) {
            logDatabaseError("Could not load item stack", exception);
            return new ItemStack[0];
        }
    }

    public static boolean refactorClass(UUID uuid) throws SQLException {
        try (PreparedStatement statement = requireConnection()
                .prepareStatement("SELECT 1 FROM CLASS WHERE uuid=?")) {
            statement.setString(1, uuid.toString());
            try (ResultSet results = statement.executeQuery()) {
                return results.next();
            }
        }
    }

    public static void insertClass(UUID uuid, String className) throws SQLException {
        try (PreparedStatement statement = requireConnection()
                .prepareStatement("INSERT INTO CLASS (uuid, Current_Class) VALUES (?, ?)")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, className);
            statement.executeUpdate();
        }
    }

    public static String findCurrentClass(UUID uuid) {
        String sql = "SELECT Current_Class FROM CLASS WHERE UUID=?";
        try (PreparedStatement statement = requireConnection().prepareStatement(sql)) {
            statement.setString(1, uuid.toString());
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return results.getString("Current_Class");
                }
            }
            insertClass(uuid, "huntsman");
            return "huntsman";
        } catch (SQLException exception) {
            logDatabaseError("Could not load current class", exception);
            return null;
        }
    }

    public static void setCurrentClass(UUID uuid, String className) throws SQLException {
        if (!refactorClass(uuid)) {
            insertClass(uuid, className);
            return;
        }

        try (PreparedStatement statement = requireConnection()
                .prepareStatement("UPDATE CLASS SET Current_Class=? WHERE uuid=?")) {
            statement.setString(1, className);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        }
    }

    public static boolean isConnected() {
        if (connection == null) {
            return false;
        }
        try {
            return !connection.isClosed();
        } catch (SQLException exception) {
            return false;
        }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        if (isConnected()) {
            return;
        }

        FileConfiguration config = Devoria.getInstance().getConfig();
        if (!config.getBoolean("database.enabled", false)) {
            throw new SQLException("Database integration is disabled in config.yml");
        }

        String url = config.getString("database.url", "").trim();
        String username = environmentOrConfig("DEVORIA_DB_USERNAME", "database.username");
        String password = environmentOrConfig("DEVORIA_DB_PASSWORD", "database.password");
        if (url.isBlank() || username.isBlank() || password.isBlank()) {
            throw new SQLException("Database URL and credentials must be configured before connecting");
        }
        if (url.toLowerCase().contains("usessl=false") || url.toLowerCase().contains("sslmode=disabled")) {
            throw new SQLException("Insecure database URLs are not allowed");
        }

        connection = DriverManager.getConnection(url, username, password);
    }

    public static void disconnect() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException exception) {
            logDatabaseError("Could not close database connection", exception);
        } finally {
            connection = null;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    private static Connection requireConnection() throws SQLException {
        if (!isConnected()) {
            throw new SQLException("Database is not connected");
        }
        return connection;
    }

    private static String environmentOrConfig(String environmentName, String configPath) {
        String environmentValue = System.getenv(environmentName);
        if (environmentValue != null && !environmentValue.isBlank()) {
            return environmentValue;
        }
        return Devoria.getInstance().getConfig().getString(configPath, "");
    }

    private static void logDatabaseError(String message, Exception exception) {
        Devoria.getInstance().getLogger().warning(message + ": " + exception.getMessage());
    }
}
