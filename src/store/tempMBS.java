package store;

import java.util.Scanner;

class LOGIN {
	public static void main(String[]agrs){
		int noOfBooks = 0;
		Scanner s = new Scanner(System.in);
		System.out.println("---WELCOME TO THE BOOK STORE---\n1.\tLogin as ADMIN.\n2.\tLogin as MEMBER.");
		int choice = s.nextInt();
		switch (choice) {

		case 1:
			System.out.println("USERNAME:");
			String Username;

			Username = s.nextLine();
			if (Username == "Aman") {
				System.out.println(
						"Enter one of the choices\n1.\tAdd Book.\n2.\tUpdate book details.\n3.\tDelete Book.\n4.\tDisplay Book.\n5.\tLogout.");
				int pick = s.nextInt();
				switch (pick) {
				case 1:
					System.out.println("How many books do you want to add?\n");
					noOfBooks = s.nextInt();
					System.out.println(
							"To Add a new book following information is required:\n1.Name of the book\n2.Author\n3.Date of Publishing.\n4.\tBook ID. \n5.\tQuantity.");
					for (int i = 0; i < noOfBooks; i++) {
						// Content To data base

					}
				case 2:
					System.out.println("Enter th eID of the book of which you want to update the details:\n");
					System.out.println(
							"Which of the details you want to update:\nEnter your choice:\n1.Name of the book\n2.Author\n3.Date of Publishing.\n4. \tBook ID.\n5.Quantity.");
							//Choice and cases
						case 3:
							System.out.println("Enter the Book ID of the boook you want to delete:");
							// bookID = s.nextInt();
							//Deletion process
						case 4:
							System.out.println("Enter the ID of the book whose details are to be displayed:\n");
							// bookID = s.nextInt();
							//Displaying the details.
						case 5:
							return;
					}
				}else{
					System.out.println("Invalid Username.");
				}
			case 2:
				System.out.println("USERNAME:");
				String member;
				int choose = 0;
				member = s.nextLine();
				if(member == "MEMBER"){
					System.out.println("Enter a choice to checkout one of the things:\n1.\tBooks Issued\n2.\tDue dates of books.\n3.\tLogout.");
					choose = s.nextInt();
					switch(choose){
						case 1:
							System.out.println("Following are the issued books in your account:\n");
							//Displaying of the issued books
						case 2:
							System.out.println("Following are the name of the books with their due dates:\n");
							//Displaying of the books with their due dates
						case 3:
  							return;
					}
				}else{
					System.out.println("Invalid Username.");
				}
				s.close();
		}
	}
}