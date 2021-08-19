package me.devoria.core.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {

    private static Connection connection;





    public static boolean isConnect(){
        return (connection == null ? false : true);
    }


    public static void connect() throws ClassNotFoundException, SQLException {

        String host = "mysql.mcprohosting.com";
        String port = "3306";
        String database = "test";
        String username = "server_1046151";
        String password = "NS7MRXIhHUxsdelJ#i$gw";

        if (!isConnect()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database +
                    "?useSSL=false", username, password);
        }


    }


    public static void disconnect(){

        if(isConnect()){
            try{
                connection.close();
            }catch (SQLException e){

            }
        }
    }


    public static Connection getConnection(){
        return connection;
    }




}
