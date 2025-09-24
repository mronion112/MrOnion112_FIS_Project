package com.example;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {
    
    private String id;
    private String name;
    private String cccd;
    private String birth_date;
    private String address;
    private ArrayList<Borrow> borrow_list;

    public User(String id, String name, String cccd, String birth_date, String address, ArrayList<Borrow> borrow_list){
        this.id = id;
        this.name = name;
        this.cccd = cccd;
        this.birth_date = birth_date;
        this.address = address; 
        this.borrow_list = borrow_list;
    }
    
    public String get_id(){
        return this.id;
    }
    public void set_id(String id){
        this.id = id;
    }

    public String get_name(){
        return this.name;
    }
    public void set_name(String name){
        this.name = name;
    }

    public String get_cccd(){
        return this.cccd;
    }
    public void set_cccd(String cccd){
        this.cccd = cccd;
    }

    public String get_birth_date(){
        return this.birth_date;
    }
    public void set_birth_date(String birth_date){
        this.birth_date = birth_date;
    }

    public String get_address(){
        return this.address;
    }
    public void set_address(String address){
        this.address = address;
    }

   public ArrayList<Borrow> get_borrow_user_list() {
    Borrow[] array = borrow_list.stream()
                                .filter(b -> b.get_userId().equals(this.id))
                                .toArray(Borrow[]::new);
    return new ArrayList<>(Arrays.asList(array));
    }


    

    private static Scanner scanner = new Scanner(System.in);

    public static User new_oop_user(ArrayList<Borrow> borrow_list, ArrayList<String> time_now){
        System.out.println("Tạo người dùng mới ");

        String id = null;
        while(id == null){
            System.out.print("id: ");
            id = scanner.nextLine();
        }

        System.out.print("name: ");
        String name = scanner.nextLine();

        System.out.print("birth_date : ");
        String birth_date = scanner.nextLine();

        System.out.print("cccd : ");
        String cccd = scanner.nextLine();

        System.out.print("address : ");
        String address = scanner.nextLine();
        DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String now_time = now.format(format_now);
        time_now.add("[ "+now_time+" ] Đã thêm người dùng " + name + " (id="+id+")");

        return new User(id, name, cccd, birth_date ,address, borrow_list); 

    }

    public String data_user_convert(User user_list){
        return "id : " + user_list.get_id() + "\nname : " + user_list.get_name() + "\ncccd : " + user_list.get_cccd() + "\nbirthdate : "+user_list.get_birth_date() + "\naddress : "+user_list.get_address();
    }

    public String data_user_borrow_convert(Borrow borrow){
        return "userId : " + borrow.get_userId() + "\nbookId : " +borrow.get_bookId() +"\nnumber book borrow : " + borrow.get_number_borrow() +  "\nborrowDate : " + borrow.get_borrowDate() + "\ndueDate : " +borrow.get_dueDate() + "\nreturnDate : " + borrow.get_returnDate();

    }

    public void show_user_information(User user){
        System.out.println(data_user_convert(user));
        System.out.println("");

        for(Borrow borrow : get_borrow_user_list()){
            System.out.println(data_user_borrow_convert(borrow));
            System.out.println("");
        }

    }

    public static void write_json_user(ArrayList<User> user_list, String FILE_PATH){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(FILE_PATH, false)){
                        gson.toJson(user_list, writer);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
    }
    
    public static void write_json_book(ArrayList<Book> book_list, String FILE_PATH){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(FILE_PATH, false)){
                        gson.toJson(book_list, writer);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
    }

    public static void write_json_borrow(ArrayList<Borrow> borrow_list, String FILE_PATH){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(FILE_PATH, false)){
                        gson.toJson(borrow_list, writer);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
    }

    public static void write_json_time_now(ArrayList<String> time_out_list, String FILE_PATH){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(FILE_PATH, false)){
                        gson.toJson(time_out_list, writer);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
    }

    
    

    
    



}
