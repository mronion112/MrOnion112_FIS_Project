package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Book {

    private String id;
    private String title;
    private String author;
    private int year;
    private int quantity;

    public Book(String id, String title, String author, int year, int quantity){
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity; 
    }
    
    public String get_id(){
        return this.id;
    }
    public void set_id(String id){
        this.id = id;
    }

    public String get_title(){
        return this.title;
    }
    public void set_title(String title){
        this.title = title;
    }

    public String get_author(){
        return this.author;
    }
    public void set_author(String author){
        this.author = author;
    }

    public int get_year(){
        return this.year;
    }
    public void set_year(int year){
        this.year = year;
    }

    public int get_quantity(){
        return this.quantity;
    }
    public void set_quantity(int quantity){
        this.quantity = quantity;
    }
    
    private static Scanner scanner = new Scanner(System.in);

    public static Book new_oop_book(ArrayList<String> time_now){
        System.out.println("Tạo sách mới : ");
        System.out.print("id: ");
        String id = scanner.nextLine();

        System.out.print("title : ");
        String title = scanner.nextLine();

        System.out.print("author : ");
        String author = scanner.nextLine();

        System.out.print("year : ");
        int year = scanner.nextInt();

        System.out.print("quantity : ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String now_time = now.format(format_now);
        time_now.add("[ "+now_time+ " ] Đã thêm sách " + title + " (id=" + id +")");

        return new Book(id, title, author, year, quantity); 

    }

    public static String data_book_convert(Book book_list){
        return "id : " + book_list.get_id() + "\ntitle : " + book_list.get_title() + "\nauthor : " + book_list.get_author() + "\nyear : "+book_list.get_year() + "\nquantity : "+book_list.get_quantity();
    }

    public void show_book_information(Book book){
        System.out.println(data_book_convert(book));
        System.out.println("");

    }

    
}
