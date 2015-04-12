package databaseInitialization;
//this package is only used for the initialization, so it executes only one time

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*Access remote Mysql database on the server*/
public class MySqlConnection {

    static String DRIVER = "com.mysql.jdbc.Driver";
    static String URL = "jdbc:mysql://localhost:3306/discountshop";
    static String USERNAME = "discountshop";
    static String PASSWORD = "discountshop";    //Please enter the personal root password

    public static Connection getConnection() throws SQLException, IOException {

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /*This function is only for initialize table*/
    public static Connection getConnection(String initUrl) throws SQLException, IOException {

        return DriverManager.getConnection(initUrl, USERNAME, PASSWORD);
    }
}
