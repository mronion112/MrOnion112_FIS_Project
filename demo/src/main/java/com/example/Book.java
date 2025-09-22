package com.example;
import com.google.gson.Gson;

public class Book {

    private String id;
    private String title;
    private String author;
    private String year;
    private String quantity;

    public Book(String id, String title, String author, String year, String quantity){
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

    public String get_year(){
        return this.year;
    }
    public void set_year(String year){
        this.year = year;
    }

    public String get_quantity(){
        return this.quantity;
    }
    public void set_quantity(String quantity){
        this.quantity = quantity;
    }
    
}
