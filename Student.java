import java.util.ArrayList;
import java.io.Serializable;

class Student {
    String username;
    String password;

    ArrayList<String> borrowedBooks = new ArrayList<>();

    Student(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
