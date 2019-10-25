/*
 * Book Management Software
 * This package is used to perform CURD of book
 * details in database
 * 
 * Database: Apache Derby
 * Link: https://db.apache.org/derby
 * 
 * @author D. Kasi Pavan Kumar
 * @version 1.0
 * @date 10 October 2019
 */

package database.books;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseHandler {
    
    /* Variables declaration */
    private static final String DB_URL = "jdbc:derby:BookDB;create=true;";
    private static final String TABLE_NAME = "Books";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private final static Scanner input = new Scanner(System.in);
    
    /* main() method */
    public void AdminPortal() {
        
        int choice = 0;
        String bookName;
        String quantity;
        
        do {
            CreateConnection();
            CreateTable();

            choice = DisplayMenu();
            
            switch(choice) {
                case 1:
                    GetBookDetails();
                    ShutdownDB();
                    break;
                
                case 2:
                    /* TODO: To add checkpoint if book exists in the database */
                    System.out.print("Enter the book name you want to delete: ");
                    input.nextLine();
                    bookName = input.nextLine();
                    DeleteBook(bookName);
                    ShutdownDB();
                    break;
                    
                case 3:
                    System.out.print("\nEnter the book name you want to update: ");
                    input.nextLine(); /* To prevent input skip */
                    bookName = input.nextLine();
                    System.out.print("\nEnter new quantity: ");
                    quantity = input.nextLine();
                    UpdateBookQuantity(bookName, quantity);
                    ShutdownDB();
                    break;
                    
                case 4:
                    SelectBook();
                    ShutdownDB();
                    break;
                    
                case 5:
                    System.out.print("\nNOTE: This will delete the whole table while keeping columns intact."
                            + "\nPress 1 to proceed & 0 to cancel: ");
                    int proceedChoice = input.nextInt();
                    
                    /* BUG: Control goes out of menu after switch..case */
                    switch(proceedChoice) {
                        case 0:
                            System.out.print("\nDeletion aborted!\n");
                            break;
                        case 1:
                            DeleteTable();
                            break;
                        default:
                           System.out.print("\nInvalid choice, process aborted!\n"); 
                           break;
                    }
                case 6:
                    // System.exit(0);
                    return;
            }
        } while(choice > 0 && choice <= 5);

        ShutdownDB();
    }
    
    /* Method to display menu */
    private static int DisplayMenu() {
        System.out.println("\nMulla Book Store\n");
        System.out.print("\n1. Add Book \n2. Delete Book \n3. Update Book Quantity \n4. Display All Books \n5. Delete table \n6. Logout");
        System.out.print("\n\nChoice: ");
        int choice = input.nextInt();
        return(choice);
    }        
    
    /* Method to get & add book details to database */
    private static void GetBookDetails() {
        System.out.print("Enter the book name: ");
        input.nextLine();
        String bookName = input.nextLine();
        
        System.out.print("\nEnter the Author name for " + bookName + ": ");
        String author = input.nextLine();
        
        System.out.print("\nEnter book ID for " + bookName + ": ");
        String bookID = input.next();
        
        System.out.print("\nEnter quantity available for " + bookName + ": ");
        String quantity = input.next();
        
        InsertBook(bookName, author, bookID, quantity);
    }

    /* Method to establish connection with the database */
    private static void CreateConnection() {
        try {
            /* Embedded database */
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        }
        catch(Exception sqle) { 
            sqle.printStackTrace();
        }
    }
    
    /* Method to create a table for book entries */
    private static void CreateTable() {
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()) {
                return;
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "title varchar(200), \n" 
                        + "author varchar(200), \n" 
                        + "id varchar(200) primary key, \n" 
                        + "quantity varchar(100)" 
                        + " )");
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle.getMessage() + " .. setupDatabase");
        }
    }
    
    /* Method to delete book */
    private static void DeleteBook(String bookName) {
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()) {
                stmt.execute("DELETE FROM " + TABLE_NAME + " WHERE title = '" + bookName + "'");
            } else {
                System.out.println("\nThere are no entries in the table.");
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle.getMessage() + " .. setupDatabase");
        }
    }

    /* Method to delete the whole table */
    private static void DeleteTable() {
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()) {
                stmt.execute("DELETE FROM " + TABLE_NAME);
            } else {
                System.out.println("\nThere are no entries in the table.");
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle.getMessage() + " .. setupDatabase");
        }
    }
    
    /* Method to update book quantity */
    private static void UpdateBookQuantity(String bookName, String quantity) {
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if(tables.next()) {
                stmt.execute("UPDATE " + TABLE_NAME + " SET quantity = '" + quantity + "' WHERE title = '" + bookName + "'");
            } else {
                System.out.println("\nThere are no entries in the table.");
            }
        }
        catch(SQLException sqle) {
            System.err.println(sqle.getMessage() + " .. setupDatabase");
        }
    }
    
    /* Method to insert book to database */
    private static void InsertBook(String name, String author, String id, String quantity) {
        try {
            stmt = conn.createStatement();
            stmt.execute("INSERT INTO " + TABLE_NAME + " VALUES ('" + name + "','" + author +  "','" + id + "','" + quantity + "')");
            stmt.close();
        }
        catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /* Method to select (display) books from database */
    private static void SelectBook() {
        try {
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM " + TABLE_NAME);
            ResultSetMetaData resultMeta = result.getMetaData();
            int columnCount = resultMeta.getColumnCount();
            for(int i = 1; i <= columnCount; ++i) {
                System.out.print(resultMeta.getColumnLabel(i) + "\t\t"); 
            }

            System.out.print("\n---------------------------------------------------------\n");

            while(result.next()) {
                String name = result.getString(1);
                String author = result.getString(2);
                int id = result.getInt(3);
                int quantity = result.getInt(4);
                System.out.println(name + "\t\t" + author + "\t\t" + id + "\t\t" + quantity);
            }

            result.close();
        }
        catch(SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    /* Method to shutdown the database */
    private static void ShutdownDB() {
        try {
            if(stmt != null) {
                stmt.close();
            }
            if(conn != null) {
                DriverManager.getConnection(DB_URL + ";shutdown=true");
                conn.close();
            }
        }
        catch(SQLException sqle) { }
    }
}