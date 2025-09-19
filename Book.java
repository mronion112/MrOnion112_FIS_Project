import java.util.Scanner;

public class Book{
    
    String id;
    String title;
    String author;
    int year;
    int quantity;

    public static Book add_information(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("id sách: ");
        String id = scanner.nextLine();
        System.out.print("\nTên sách: ");
        String title = scanner.nextLine();
        //scanner.next();
        System.out.print("\nTác giả: ");
        String author = scanner.nextLine();
        //scanner.next();
        System.out.print("\nNăm xuất bản: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nSố lượng: ");
        int quantity = scanner.nextInt(); 

        return new Book(id, title, author, year, quantity);
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public int getQuantity() { return quantity; }

    public Book( String id, String title, String author, int year, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
        
    }

    
    
}
