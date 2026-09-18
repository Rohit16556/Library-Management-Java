import java.io.Serializable;

class Book implements Serializable {
    String name;
    String author;
    int quantity;
    int available;

    Book (String name, String author, int quantity) {
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.available = quantity;
    }

public String toString() {
        return "name: " + name + " || author: " + author + " || TotalBooks: " + quantity + "  ||  available: " + available;
    }
}
