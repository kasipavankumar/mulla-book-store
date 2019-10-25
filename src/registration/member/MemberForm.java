/**
 * Mulla Book Store
 * 
 * Member Login & Signup Module
 * 
 * @author D. Kasi Pavan Kumar
 * @version 1.2
 * @date 19 October 2019
*/

package registration.member;

import java.util.Scanner;

import database.credentials.member.MemberDBManager;

public class MemberForm {

    /* Variable declarations */
    private static Scanner input = new Scanner(System.in);
    private static String username = null;
    private static String password = null;
    private static String exitMessage = "Exit";
    private static Boolean registrationSuccess = false;
    private static Boolean authenticationSuccess = false;
    private static AuthenticationModule Admin = new AuthenticationModule();
    private static MemberDBManager DB = new MemberDBManager();

    /* Method to display menu for the Member portal */
    public static int DisplayMenu() {
        int choice = 0;
        System.out.print("\n\nMULLA BOOK STORE \nWelcome to Member Portal \n\n1. Register \n2. Login \n3. " + exitMessage + "\nType your choice here: ");
        choice = input.nextInt();
        return(choice);
    }

    /* Method to process the login */
    public static void Register() {
        input.nextLine();
        System.out.print("Enter username: ");
        username = input.nextLine();
        System.out.print("\nEnter password: ");
        password = input.nextLine();
        registrationSuccess = Admin.Register(username, password);
        if(registrationSuccess == true) {
            System.out.print("\nRegistered successfully!\n");
        } else {
            System.out.print("\nRegistration unsuccessful!\n");
        }
    }

    /* Method to process the registration */
    public static void Login() {
        input.nextLine();
        System.out.print("Enter username: ");
        username = input.nextLine();
        System.out.print("\nEnter password: ");
        password = input.nextLine();
        authenticationSuccess = Admin.AuthenticateLogin(username, password);
        if (authenticationSuccess == true) {
            exitMessage = "Logout";
            System.out.print("\nLogged in successfully!\n");
        } else {
            System.out.print("\nInvalid credentials. Try again\n");
        }
    }
    
    /* Driver method to whole member portal */
    public void MemberPortal() {
        int choice = 0;

        do {
            DB.EstablishConnection();
            DB.CreateTable();

            choice = DisplayMenu();
            switch(choice) {

                /* Register */
                case 1:
                    Register();
                    DB.Shutdown();    
                    exitMessage = "Exit";
                    break;

                /* Login */
                case 2:
                    Login();
                    DB.Shutdown();
                    break;

                /* Exit */       
                case 3:
                    exitMessage = "Exit";
                    return;
            }
        } while(choice > 0 && choice <= 3);

        DB.Shutdown();
        input.close();
    }
}

/*  Class which provides methods to authenticate login & process
    registration credentials */
class AuthenticationModule {
    MemberDBManager Admin = new MemberDBManager();
    Boolean registrationSuccess = false; // Registration success flag
    Boolean authenticationSuccess = false; // Login Authentication success flag

    /* Method to register to new member */
    public Boolean Register(String username, String password) {
        
        /* Adding new member details to the database */
        registrationSuccess = Admin.InsertCredentials(username, password);

        /* If registration is successful */
        if(registrationSuccess == true) {
            return(true);
        }

        /*  Failed registration */
        return(false);
    }

    /* Method to authenticate the login details */
    public Boolean AuthenticateLogin(String username, String password) {
        
        /* Getting authentication details from the database */
        authenticationSuccess = Admin.GetCredentialsFromDB(username, password);
        
        // If authentication is successful
        if(authenticationSuccess == true) {
            return(true);
        }

        /*  Failed authentication.
            Control reaches here in the following conditions:
            • Credentials are invalid 
            • No such credentials exist in the database */
        return(false);
    }
} /* END OF FILE */