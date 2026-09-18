import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.File;

public class LibraryApp {

	Scanner sc = new Scanner(System.in);
	
    final String admin_name = "admin1";
   	final String admin_pass = "admin@123";

  	ArrayList<Book> books = new ArrayList<>();
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<String[]> borrowedBooks = new ArrayList<>();

	String dataFile = "data/library.data";

	public static void main(String[] args) {
		LibraryApp lab = new LibraryApp();

		lab.createDataFolder();

		lab.loadData();
		
		lab.start();
	}

private void clearScreen() {
	try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void createDataFolder() {
	
	File folder = new File("data");

	if(!folder.exits()) {
		folder.mkdir();
	}
}
	
public void start() {
        System.out.println("  Welcome to the library.. ");

        System.out.println("\n1. Admin Login");
        System.out.println("2. Student Registration");
        System.out.println("3. Student Login");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                adminLogin();
                break;

            case 2:
                studentRegister();
                break;

            case 3:
                studentLogin();
                break;

            case 4:
                System.out.println("Thank you..");
                break;

            default:
                System.out.println("Invalid choice.");
        }
}

private void adminLogin() {
		clearScreen();
	
        System.out.println("Admin Login..");
	
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (username.equals(admin_name) && password.equals(admin_pass)){

            System.out.println("Admin login successfully."); 
			
            adminMenu();
			
	    	return;

    	} else {
			
			System.out.println("Invalid Username or Password.");
			
			start();
		}
	}

private void adminMenu() {
	
        clearScreen();
	
		int ch;
        do {
			
            System.out.println("\n ** Admin menu **");
            
			System.out.println("\n1.Register a new book");
            System.out.println("2.Update book quantity");
            System.out.println("3.View books taken by students");
            System.out.println("4.View all books");
            System.out.println("5.Logout");

            System.out.print("\nEnter choice: ");

            ch = sc.nextInt();
            sc.nextLine();

            switch(ch) {
					
                case 1:
                	registerBook();
                    	break;
					
                case 2:
                	updateQuantity();
                    	break;
					
                case 3:
                   	borrowedBooks();
                    	break;
					
                case 4:
                	viewAllBooks();
               	     	break;
					
                case 5:
                        System.out.println("Log out.");
                        start();
					
						return;
                  
                default:
                    System.out.println("Invalid choice.");
            }
			
        } while (ch != 5);
	}

private void registerBook() {
		clearScreen();
    	
		System.out.println("Register new book..");
    	
		System.out.print("Book name: ");
    	String name =sc.nextLine();

    	System.out.print("Author: ");
    	String author =sc.nextLine();

    	System.out.print("Quantity: ");
    	int quantity =sc.nextInt();
    	sc.nextLine();

    	Book book = new Book(name, author, quantity);
    
		books.add(book);

	  	saveData();
    	
		System.out.println("Book registered successfully.");
    }

private void viewAllBooks() {

    if(books.isEmpty()) { 	 
        System.out.println("No books available.");
   	}
	
    System.out.println("\nAvailable books in library..");

    for(int i=0; i<books.size(); i++) {
       
		Book book =books.get(i);
        
		System.out.println((i + 1) + ".  " + book);
    }
}

private Student findStudent(String name) {
	
	for(Student student : students) {
	
		if(student.username.equals(name)) {
		
			return student;
		}
	}
	return null;
}

private void updateQuantity() {
	System.out.println("Update Books Quantity.");
        
	System.out.println("Enter book name: ");
    String name = sc.nextLine();

    Book book = findBook(name);

	if(book == null) {
		System.out.println("book not found.");
		return;
	}

	System.out.println("Current quantity: " + book.quantity);
	System.out.println("Available quantity: " + book.available);

	System.out.println("Enter new total quantity: ");
	int newquantity = sc.nextInt();
	sc.nextLine();

	book.quantity = newquantity + book.quantity;
	book.available = book.available + newquantity;

	if(book.available < 0 ) {
		book.available = 0;
	}	

	saveData();
  
	System.out.println("Books updated succefully.");
}

private Book findBook(String name) {
 
	for(Book book:books) {
    
		if(book.name.equalsIgnoreCase(name)) {
			return book;
        }
    }
    return null;
}

private void studentRegister() {
    System.out.println();
    System.out.println("Register new student: ");

    System.out.print("Enter username: ");
    String username = sc.nextLine();

    if(findStudent(username) != null) {
        System.out.println("Username already exits.");
        return;
    }

    System.out.print("Enter password: ");
    String password = sc.nextLine();

    Student student = new Student(username, password);
   
	students.add(student);

	saveData();
	
	System.out.println("Registration successful.");
    
	studentLogin();
}

private void studentLogin() {
    clearScreen();
   
	System.out.println("\nCandidate login: ");

    System.out.print("Username: ");
    String username = sc.nextLine();

    System.out.print("Password: ");
    String password = sc.nextLine();

    Student student = findStudent(username);

    if(student == null) {
        
		System.out.println("Username not found.");
        
		studentRegister();
        
		return;
    }

    if(!student.password.equals(password)) {
		
    	System.out.println("password does not match.");
    	
		studentRegister();
		
        return;
    }

    System.out.println("Login successfully.");

    studentMenu(student);
}

private void studentMenu(Student student){
   
	int choice;
    do {
       
        System.out.println("\nWelcome to A Library ");
        
		System.out.println("\n1.view books");
        System.out.println("2.Borrow a book");
        System.out.println("3.Return a book");
        System.out.println("4.Logout");

        System.out.print("Enter choice: ");

        choice = sc.nextInt();
        sc.nextLine();

        switch(choice) {
            case 1:
            	viewBooks();
                break;

            case 2:
            	borrowBook(student);
                break;

            case 3:
            	returnBook(student);
                break;

            case 4:
                System.out.println("Logged out.");
                start();
                return;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 4);
}
	
private void viewBooks() {

    if(books.isEmpty()) {
        
		System.out.println("There are no books available.");
		return;
    }

	System.out.println("\nAvailable books");

    int pageSize = 10;
    int start = 0;

    while(start < books.size()) {
        int end = Math.min(start + pageSize, books.size());

        System.out.println();

        for(int i = start; i < end; i++) {
            
			Book book = books.get(i);
            
			System.out.println((i + 1) + ". " + book);
        }

        if(end >= books.size()) {
           
			System.out.println("\nEnd of page");
            break;
        }

	System.out.print("press yes to see more, or type x to stop: ");
		
	String input=sc.nextLine();
	
	if(input.equals("x")) {
		break;
	}

	start=end;
    }
}


private void borrowBook(Student student) {

    System.out.println("\nborrow book: ");

    System.out.print("Enter book name: ");
    String name = sc.nextLine();

    Book book=findBook(name);

    if(student.borrowedBooks.contains(book.name)) {
       
		System.out.println("You already borrowed this book.");
        
		return;
    }

    if(book==null){
        
		System.out.println("Book not found.");
        
		return;
    }

    if(book.available <=0) {
        
		System.out.println("Book is not available.");
        
		return;
    }
   
    book.available--;

    student.borrowedBooks.add(book.name);
   
	borrowedBooks.add(new String[]{student.username, book.name});

	saveData();
    
	System.out.println("book borrowed sucessfully.");
}

private void returnBook(Student student) {

    System.out.println();
    System.out.println("Return book");

    System.out.print("Enter book name: ");
    String name = sc.nextLine();

    Book book = findBook(name);

    if(book == null){
        
		System.out.println("Book not found.");
        
		return;
    }

    if(!(student.borrowedBooks.contains(book.name))) {
       
		System.out.println("You have not borrowed this book.");
        
		return;
    }

    book.available++;

    student.borrowedBooks.remove(book.name);

    for(int i=0; i< borrowedBooks.size(); i++) {
	
		String [] record = borrowedBooks.get(i);
	
		if(record[0].equals(student.username) && record[1].equals(book.name)) {
			
			borrowedBooks.remove(i);
			break;
		}
	}

	saveData();
	
    System.out.println("Book submit back to the lab successfully...");
}

private void borrowedBooks() {
	
	if(borrowedBooks.isEmpty()){
		
		System.out.println("No one has borrowed any book. ");
		return;
	}
	
	System.out.println("Student Name:         Borrowed Books: ");
	
	for(int i=0; i<borrowedBooks.size(); i++) {
		
		String[] records = borrowedBooks.get(i);
	
		System.out.println(records[0] + "                  " + records[1]);
		}
	}

private void saveDate() {

	try {
		
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dataFile));

		output.writeObject(books);

		output.writeObject(students);

		output.writeObject(borrowedBooks);

		output.close();

		} catch(IOException e) {

		e.printStackTrace();
	}
}

@SuppressWarnings("unchecked")
private void loadData() {

	try {

		ObjectInputStream input = new ObjectInputStream(new FileInputStream(dataFile));
	
		books = ((ArrayList<Book>) input.readObject());

		students = (ArrayList<Student>) input.readObject();

		borrowedBooks = (ArrayList<String[]>) input.readObject();

		input.close();

		} catch(IOException e) {
	
	        System.out.println("No saved data found.");

		} catch(ClassNotFoundException  e) {
		
		e.printStackTrace();
		}
	}
}
