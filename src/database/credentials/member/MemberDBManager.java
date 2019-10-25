/**
 * Mulla Book Store
 * 
 * Database manager for Admin login & registration module.
 * 
 * Database: Apache Derby
 * Link: https://db.apache.org/derby
 * 
 * @author D. Kasi Pavan Kumar
 * @version 1.0
 * @date 20 October 2019
 */

package database.credentials.member;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDBManager {
    private static final String DB_URL = "jdbc:derby:AdminData;create=true;";
    private static final String TABLE_NAME = "AdminDataBase";
    private static Connection connection = null;
    private static Statement statement = null;
    
    /* Method to establish connection with the database */
    public void EstablishConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);
        } catch(Exception sqlE) { }
    }

    /* Method to shutdown the database when not in use */
    public void Shutdown() {
        try {
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                DriverManager.getConnection(DB_URL + ";shutdown=true");
                connection.close();
            }
        } catch (SQLException sqlE) { }
    }

    /* Method to create a table in the database for admin data */
    public void CreateTable() {
        try {
            statement = connection.createStatement();
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet table = metadata.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(table.next()) {
                return;
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "(" + "username VARCHAR(16) PRIMARY KEY NOT NULL, " + " password VARCHAR(16) NOT NULL" + ");");
            }
        } catch(SQLException sqlE) { }
    }

    /* Method to delete the whole table in the database for admin data */
    public void DeleteTable() {
        try {
            statement = connection.createStatement();
            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet table = metadata.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(table.next()) {
                statement.execute("DELETE FROM " + TABLE_NAME);
            } else {
                System.out.println("\nThere are no entries in the table.");
            }
        } catch(SQLException sqlE) { }
    }

    /* Method to enter login / registration credentials in the database */
    public Boolean InsertCredentials(String username, String password) {
        Boolean insertSuccess = false;
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO " + TABLE_NAME + " VALUES ('" + username + "', '" + password + "')");
            statement.close();
            insertSuccess = true;
        } catch(SQLException sqlE) { }

        if(insertSuccess == true) {
            return(true);
        }

        return(false);
    }

    /* Method to get username & password from the database */
    public boolean GetCredentialsFromDB(String username, String password) {
        Boolean authSuccess = false;
        try {
            statement = connection.createStatement();
            ResultSet credentials = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE username = '" + username + "'");
            while(credentials.next()) {
                String uname = credentials.getString("username");
                String pass = credentials.getString("password");
                if(uname.equals(username) && pass.equals(password)) {
                    authSuccess = true;
                }
            }
            credentials.close();
        } catch(SQLException sqlE) { }
        return(authSuccess);
    }

    /* Method to get all database content */
    public void GetAllCredentials() {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            ResultSetMetaData resultMeta = result.getMetaData();
            int columnCount = resultMeta.getColumnCount();
            for(int i = 1; i <= columnCount; ++i) {
                System.out.print(resultMeta.getColumnLabel(i) + "\t\t"); 
            }
            System.out.print("\n---------------------------------------------\n");

            while(result.next()) {
                String username = result.getString(1);
                String password = result.getString(2);
                System.out.println(username + "\t\t" + password);
            }
        } catch(SQLException sqlE) { }
    }
}