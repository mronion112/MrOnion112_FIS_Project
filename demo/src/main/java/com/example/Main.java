package com.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private transient final static String FILE_TIME_NOW = "src\\main\\java\\com\\example\\time_now.json";
    private transient final static String FILE_BOOKS = "src\\main\\java\\com\\example\\books.json";
    private transient final static String FILE_USERS = "src\\main\\java\\com\\example\\users.json";
    private transient final static String FILE_BORROWS = "src\\main\\java\\com\\example\\borrows.json";
    public static void main(String[] args) {
        DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String now_time = now.format(format_now);
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> user_list  = JsonUtils.read_json(FILE_USERS, User.class);
        ArrayList<Book> book_list  = JsonUtils.read_json(FILE_BOOKS, Book.class);
        ArrayList<Borrow> borrow_list= JsonUtils.read_json(FILE_BORROWS, Borrow.class);
        ArrayList<String> time_now_list = new ArrayList<>();
        ArrayList<String> out_date_list = new ArrayList<>();

        
        int x = 0;

        do { 
            System.out.print("""
                Thư viện v2.
                1, Tạo người dùng mới 
                2, Tạo sách mới
                3, Mượn sách
                4, Trả sách
                5, Hiển thị danh sách quá hạn
                6, Hiển thị danh sách hoạt động (log)   
                7, Hiển thị danh sách người dùng
                8, Hiển thị danh sách sách 
                9, Hiển thị danh sách mượn
                0, Thoát
                Lựa chọn của bạn là : """);
              x = scanner.nextInt();
             

             switch (x) {
                 case 1 :
                    user_list.add(User.new_oop_user(borrow_list, time_now_list));

                    
                     break;
                 case 2:
                    book_list.add(Book.new_oop_book(time_now_list));

                     break;
                
                 case 3:
                    borrow_list.add(Borrow.muon_sach(user_list, book_list, time_now_list));

                     break;

                 case 4:
                    Borrow.Tra_sach(user_list, book_list, borrow_list, out_date_list, time_now_list);

                     break;

                 case 5: 
                    for(String l : out_date_list){
                        System.out.println(l);
                    }
                    System.out.println("\n Tổng số sách quá hạn : " + out_date_list.size());
                    time_now_list.add("[ "+now_time+ " ] Hiển thị danh sách quá hạn");

                     break;

                 case 6:
                    for(String l : time_now_list){
                        System.out.println(l);
                    }
                    
                     break;

                 case 7:
                    for(User user : user_list){
                        user.show_user_information(user);
                    }
                
                    time_now_list.add("[ "+now_time+ " ] Hiển thị danh sách người dùng");

                     break;

                 case 8:
                    for(Book book : book_list){
                        book.show_book_information(book);
                    }
                    time_now_list.add("[ "+now_time+ " ] Hiển thị danh sách sách");

                     break;

                 case 9:
                    for(Borrow borrow : borrow_list){
                        borrow.show_borrow_information(borrow);

                        time_now_list.add("[ "+now_time+ " ] Hiển thị danh sách sách mượn");
                    }
                     break;

                 case 0:
                    System.out.println("Tạm biệt....");
                    time_now_list.add("[ "+now_time+ " ] Đóng thư viện");
                    User.write_json_user(user_list, FILE_USERS);
                    User.write_json_book(book_list, FILE_BOOKS);
                    User.write_json_borrow(borrow_list, FILE_BORROWS);
                    User.write_json_time_now(time_now_list, FILE_TIME_NOW);

                     break;

                 default:
                    
                }
        } while (x != 0);

        


    }
   
    
}


