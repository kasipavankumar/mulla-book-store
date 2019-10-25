/**
 * Mulla Book Store
 * 
 * Store module.
 * 
 * @author Makarand More
 * @version 1.0
 * @date 20 October 2019
 */

package store;

import java.util.Scanner;
import database.books.DatabaseHandler;
import registration.admin.AdminForm;
import registration.member.MemberForm;

public class MullaBookStore {
    private static Scanner input = new Scanner(System.in);
    private static DatabaseHandler database = new DatabaseHandler();
    private static AdminForm admin = new AdminForm();
    private static MemberForm member = new MemberForm();
    private static String adminUsername = null;
    private static String adminPassword = null;

    public static void main(String[] args) {
        int pickPerson = 0;
        do {
            pickPerson = ShowStartMenu();

            switch(pickPerson) {
                case 1:
                    AuthAdmin();
                    break;

                case 2:
                    member.MemberPortal();
                    break;
                case 3:
                    System.exit(0);
            }
        } while(pickPerson > 0 && pickPerson <= 3);
    }

    /*  Method to display the login menu with the following
    options:
        1) Admin
        2) Member 
        3) Cancel */
    public static int ShowStartMenu() {
        int pickPerson = 0;
        System.out.println("\nWelcome to Mulla Book Store");
        System.out.print("\n1) Admin \n2) Member \n3) Exit \nType choice here: ");
        pickPerson = input.nextInt();
        return(pickPerson);
    }

    /**
     * Method to authenticate the admin's credentials
     * from the database
     */
    public static void AuthAdmin() {
        System.out.print("\nWelcome back, Admin!");
        input.nextLine();
        System.out.print("\nEnter username: ");
        adminUsername = input.nextLine();
        System.out.print("Enter password: ");
        adminPassword = input.nextLine();
        if(admin.LoginAuth(adminUsername, adminPassword) == true) {
            database.AdminPortal();
        } else {
            System.out.print("\nInvalid credentials. \nTry again.");
            return;
        }
    }

}