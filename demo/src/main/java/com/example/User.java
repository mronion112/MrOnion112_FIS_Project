package com.example;

import java.util.Scanner;

public class User {
    String FILE_USERS = "demo\\src\\main\\java\\com\\example\\users.json";
    private String id;
    private String name;
    private String cccd;
    private String birth_date;
    private String address;

    public User(String id, String name, String cccd, String birth_date, String address){
        this.id = id;
        this.name = name;
        this.cccd = cccd;
        this.birth_date = birth_date;
        this.address = address; 
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

    Scanner scanner = new Scanner(System.in);

    public static User new_oop_user(){
        System.out.print("id: ");
        String id = scanner.nextLine();
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("birth_date : ");
        String birth_date = scanner.nextLine();
        System.out.print("address : ");
        String address = scanner.nextLine();

        return new User(id, name, name, birth_date, address);

    }

    



}
