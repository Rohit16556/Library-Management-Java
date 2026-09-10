import java.util.ArrayList;
import java.util.Scanner;
public class LibraryApp{

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();

     static ArrayList<String[]> borrowedBooks = new ArrayList<>();

   static final String admin_name = "admin1";
   static final String admin_pass = "admin@123";

public static class Student{

    String username;
    String password;

    ArrayList<String> borrowedBooks = new ArrayList<>();

    Student(String username, String password){
        this.username = username;
        this.password = password;
    }
}

public static class Book{

    String name;
    String author;
    int quantity;
    int available;

    Book(String name, String author, int quantity) {
        this.name =name;
        this.author =author;
        this.quantity =quantity;
        this.available=quantity;
    }

    public String toString(){
        return "name: " + name + "|| author: " + author + " ||TotalBooks: " + quantity + "  ||  available: " + available;
    }
}

    public static void main(String[] args){

        System.out.println("welcome to the library..");
       
        System.out.println("\n1. Admin Login");
        System.out.println("2. Student Registration");
        System.out.println("3. Student Login");
        System.out.println("4. Exit\n");

        System.out.print("Enter your choice: ");

        int ch = sc.nextInt();
        sc.nextLine();

        if(ch ==1){
            adminLogin();
        }
        else if(ch ==2){
        	studentRegister();
        }
        else if(ch==3){
        	studentLogin();
        }
        else if(ch==4){
            System.out.println("thank you..");
        }
        else{
            System.out.println("Invalid choice.");
        }
    }

    static void adminLogin(){

        System.out.println();
        System.out.println("Admin Login..");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (username.equals(admin_name) && password.equals(admin_pass)){

            System.out.println("Admin login successfully.");
            
            adminMenu();

        }
        else {
            System.out.println("Invalid username and password.");
            adminLogin();
        }
    } 
   
    
static void adminMenu(){

        int ch;
        do {
            System.out.println();
            System.out.println("admin menu..");
            System.out.println();
            System.out.println("1.register a new book");
            System.out.println("2.update book quantity");
            System.out.println("3.view books taken by students");
            System.out.println("4.view all books");
            System.out.println("5.logout");
            System.out.println();

            System.out.print("Enter choice: ");

            ch=sc.nextInt();
            sc.nextLine();

            switch(ch){
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
                    LibraryApp.main(null);
                    break;
                  
                default:
                    System.out.println("invalid choice.");
            }

        } while (ch != 5);
    }

static void registerBook(){
    System.out.println("Register new book..");

    System.out.print("Book name: ");
    String name =sc.nextLine();

    System.out.print("Author: ");
    String author =sc.nextLine();

    System.out.print("Quantity: ");
    int quantity =sc.nextInt();
    sc.nextLine();

    Book book =new Book(name, author, quantity);

    books.add(book);

    System.out.println("Book registered successfully.");
}

static void viewAllBooks(){

    System.out.println();
    System.out.println("available books in library..");

    if(books.isEmpty()) {
        System.out.println("No books available.");
    }

    for(int i=0; i<books.size(); i++) {
        Book book =books.get(i);
        System.out.println((i + 1) + "= " + book);
    }
}


static Book findBook(String name) {
    for(Book book:books) {
        if(book.name.equals(name)) {
            return book;
        }
    }
    return null;
}


static void updateQuantity() {

    System.out.println();
    System.out.println("update books quantity...");

    System.out.print("Enter book name: ");
    String name = sc.nextLine();

    Book book = findBook(name);

    if (book == null) {
        System.out.println("Book not found.");
        return;
    }

    System.out.println("current quantity: " + book.quantity);
    System.out.println("available books: " + book.available);

    System.out.print("Enter new total quantity: ");
    int newquantity = sc.nextInt();
    sc.nextLine();

    int addbooks = newquantity + book.quantity;

    book.quantity = addbooks;
    book.available = book.quantity;

    if (book.available < 0) {
        book.available = 0;
    }

    System.out.println("Quantity updated successfully.");
}


static Student findStudent(String username){

    for(Student student:students){
        if (student.username.equals(username)) {
            return student;
        }
    }

    return null;
}

static void studentRegister(){
    System.out.println();
    System.out.println("register new student");

    System.out.print("enter username: ");
    String username = sc.nextLine();

    if(findStudent(username) != null) {
        System.out.println("Username already exits.");
        studentLogin();
    }

    System.out.print("enter password: ");
    String password = sc.nextLine();

    Student student = new Student(username, password);

    students.add(student);

    System.out.println("Registration successful.");
    studentLogin();
   
}

static void studentLogin(){
    System.out.println();
    System.out.println("candidate login: ");

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

static void studentMenu(Student student){
    int choice;
    do {
        System.out.println();
        System.out.println("welcome to e-lab.. ");
        System.out.println("\n1.view book");
        System.out.println("2.borrow a book");
        System.out.println("3.return a book");
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
                LibraryApp.main(null);
                break;

            default:
                System.out.println("Invalid choice.");
        }

    } while (choice != 4);
}
	
static void viewBooks(){

    System.out.println();
    System.out.println("available books");

    if(books.isEmpty()){
        System.out.println("No books available.");
	return;
    }

    int pageSize = 10;
    int start = 0;

    while(start < books.size()){
        int end = Math.min(start + pageSize, books.size());

        System.out.println();

        for(int i = start; i < end; i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book);
        }

        if(end >=books.size()){
            System.out.println();
            System.out.println("end of page");
            break;
        }

	System.out.print("press yes to see more, or type x to stop: ");
	String input=sc.nextLine();

	if(input.equals("x")){
	break;
	}

	start=end;
    }
}


static void borrowBook(Student student){

    System.out.println();
    System.out.println("borrow book: ");

    System.out.print("Enter book name: ");
    String name = sc.nextLine();

    Book book=findBook(name);

    if(book==null){
        System.out.println("Book not found.");
        return;
    }

    if(book.available <=0){
        System.out.println("Book is not available.");
        return;
    }

    if(student.borrowedBooks.contains(book.name)){
        System.out.println("You already borrowed this book.");
        return;
    }

    
    book.available--;

    student.borrowedBooks.add(book.name);
    borrowedBooks.add(new String[]{student.username, book.name});
    System.out.println("book borrowed sucessfully.");
}

static void returnBook(Student student){

    System.out.println();
    System.out.println("return book");

    System.out.print("Enter book name: ");
    String name =sc.nextLine();

    Book book =findBook(name);

    if(book ==null){
        System.out.println("Book not found.");
        return;
    }

    if(!(student.borrowedBooks.contains(book.name))){
        System.out.println("You have not borrowed this book.");
        return;
    }

    book.available++;

    student.borrowedBooks.remove(book.name);

    for(int i=0; i< borrowedBooks.size(); i++) {
	String [] record = borrowedBooks.get(i);
	
	if(record[0].equals(student.username) && record[1].equals(book.name)){
		borrowedBooks.remove(i);
		break;
	}
}
	

    System.out.println("Book  submit back to lab successfully...");
}

static void borrowedBooks(){

	System.out.println("student name: " + "  " + "borrowed dooks: ");
	for(int i=0; i<borrowedBooks.size(); i++) {
		String[] records = borrowedBooks.get(i);
		System.out.println(records[0] + "       " + records[1]);
}
}
}
