package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Borrow {
    
    private String userId;
    private String bookId;
    private int number_borrow;
    private String borrowDate;
    private String dueDate;
    private String returnDate;

    public Borrow (String userId, String bookId,int number_borrow ,String borrowDate, String dueDate, String returnDate){
        this.userId = userId;
        this.bookId = bookId;
        this.number_borrow = number_borrow;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public String get_userId(){
        return this.userId;
    }
    public void set_userId(String userId){
        this.userId = userId;
    }

    public String get_bookId(){
        return this.bookId;
    }
    public void set_bookId(String bookId){
        this.bookId = bookId;
    }

    public int get_number_borrow(){
        return this.number_borrow;
    }
    public void set_number_borrow(int number_borrow){
        this.number_borrow = number_borrow;
    }

    public String get_borrowDate(){
        return this.borrowDate;
    }
    public void set_borrowDate(String borrowDate){
        this.borrowDate = borrowDate;
    }

    public String get_dueDate(){
        return this.dueDate;
    }
    public void set_dueDate(String dueDate){
        this.dueDate = dueDate;
    }

    public String get_returnDate(){
        return this.returnDate;
    }
    public void set_returnDate(String returnDate){
        this.returnDate= returnDate;
    }
    private static Scanner scanner = new Scanner(System.in);

    public static boolean is_id_user_exist(ArrayList<User> user_list, String userId){
        return user_list.stream().anyMatch(u->u.get_id().equals(userId));
    }

    public static boolean is_id_book_exist(ArrayList<Book> book_list, String bookId){
        return book_list.stream().anyMatch(u->u.get_id().equals(bookId));

        
    }
    
    public static Borrow muon_sach(ArrayList<User> user_list, ArrayList<Book> book_list , ArrayList<String> time_now){
        String userId_1;
        do{
            System.out.print("Id người mượn : ");
            userId_1 = scanner.nextLine();
            if(!is_id_user_exist(user_list, userId_1)){
                System.out.println("Không có id người dùng này, xin nhập lại");
            }
        
        }while(!is_id_user_exist(user_list, userId_1));
        final String userId = userId_1;
        if(number_user_borrowed_all(user_list, userId) >=3){
            System.out.println("Bạn chỉ được mượn tối đa 3 quyển cùng lúc");
            return null;
        }
        else{
            String bookId_1;
            do{
                System.out.println("Id sách muốn mượn : ");
                bookId_1 = scanner.nextLine();
                if(!is_id_book_exist(book_list, bookId_1)){
                    System.out.println("Không có id sách này, xin nhập lại");
                }
            }while(!is_id_book_exist(book_list, bookId_1));
            
            final String bookId = bookId_1;

            int number_borrow;

            
            System.out.print("Số sách muốn mượn: ");
            number_borrow = scanner.nextInt();
            scanner.nextLine();

            int index = java.util.stream.IntStream.range(0, book_list.size())
            .filter(i -> book_list.get(i).get_id().equals(bookId)) 
            .findFirst()
            .orElse(-1);
            
            if(number_borrow > 3){
                System.out.println("Bạn chỉ được mượn tối đa 3 quyển cùng lúc");
                return null;
            }
            if(number_borrow + number_user_borrowed_all(user_list, userId) > 3){
                System.out.println("Bạn chỉ có thể mượn thêm " + (3-number_user_borrowed_all(user_list, userId))+ " quyển sách nữa");
                return null;
            }
            if(number_book_valiable(book_list, bookId) < number_borrow){
                System.out.println("Không đủ sách để mượn còn "+ number_book_valiable(book_list, bookId)+" cái");
                return null;
            }
            if(number_borrow == 0){
                System.out.println("Bạn k mượn quyển nào");
                return null;
            }
            else{
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDateTime borrowDate_time = null;
                LocalDateTime dueDate_time = null;
                String borrowDate = null;
                String dueDate = null;

                while(borrowDate == null){
                    System.out.println("Nhập ngày giờ mượn sách (DD-MM-YYYY) : ");
                    String input = scanner.nextLine();

                    try {
                        borrowDate_time = java.time.LocalDate.parse(input.trim(), format).atStartOfDay();
                        dueDate_time = borrowDate_time.plusDays(7);

                        borrowDate = borrowDate_time.toLocalDate().format(format);
                        dueDate = dueDate_time.toLocalDate().format(format);
                    } catch (DateTimeParseException e) {
                        System.out.println("Nhập sai định dạng (DD-MM-YYYY) nhập lại");
                    }
                }

                book_list.get(index).set_quantity(book_list.get(index).get_quantity()-number_borrow);
                
                String returnDate = null;
                DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String now_time = now.format(format_now);

                time_now.add("[ "+ now_time+" ] User " +userId + " (" + (user_list.stream().filter(b->b.get_id().equals(userId)).findFirst().orElse(null)).get_name() + ") mượn Book " + bookId + " (" + (book_list.stream().filter(b->b.get_id().equals(bookId)).findFirst().orElse(null)).get_title() +")");
                return new Borrow(userId,bookId,number_borrow ,borrowDate,dueDate,returnDate);
            }
        }


    }

    public static  void Tra_sach(ArrayList<User> user_list, ArrayList<Book> book_list, ArrayList<Borrow> borrow_list, ArrayList<String> out_date_list, ArrayList<String> time_now){

        String userId_1;
        do{
            System.out.print("Id người trả : ");
            userId_1 = scanner.nextLine();
            if(!is_id_user_exist(user_list, userId_1)){
                System.out.println("Không có id người dùng này, xin nhập lại");
            }
        
        }while(!is_id_user_exist(user_list, userId_1));
        final String userId = userId_1;

        String bookId_1;
        do{
            System.out.println("Id sách muốn trả : ");
            bookId_1 = scanner.nextLine();
            if(!is_id_book_exist(book_list, bookId_1)){
                System.out.println("Không có id sách này, xin nhập lại");
            }
        }while(!is_id_book_exist(book_list, bookId_1));
        
        final String bookId = bookId_1;

        
        System.out.print("Số sách muốn trả: ");
        int number_borrow_back = scanner.nextInt();
        scanner.nextLine();
        if(number_borrow_back > number_book_user_borrow(borrow_list, bookId)){
            System.out.println("Bạn đang mượn "+ number_book_user_borrow(borrow_list, bookId) + " quyển sách, không thể trả hơn ");
        }
        else{
            int index_book = java.util.stream.IntStream.range(0, book_list.size())
                .filter(b -> book_list.get(b).get_id().equals(bookId)) 
                .findFirst()
                .orElse(-1);
            int index_borrow = java.util.stream.IntStream.range(0, borrow_list.size())
                                                        .filter(b-> borrow_list.get(b).get_bookId().equals(bookId) && borrow_list.get(b).get_userId().equals(userId))
                                                        .findFirst()
                                                        .orElse(-1);
            
            Borrow borrow = borrow_list.stream().filter(b-> b.get_bookId().equals(bookId)&& b.get_userId().equals(userId)).findFirst().orElse(null);
            // String due_Date_time = borrow.get_dueDate(); 
            LocalDateTime due_Date_time= null;
            

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            DateTimeFormatter fomart_due_Date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDateTime borrow_back_Date_time = null;
            String borrow_back_Date = null;
            while(borrow_back_Date == null){
                System.out.println("Nhập ngày giờ trả sách (DD-MM-YYYY HH:mm:ss) : ");
                String input = scanner.nextLine();

                try {
                    borrow_back_Date_time = java.time.LocalDateTime.parse(input.trim(), format);
                    borrow_back_Date = borrow_back_Date_time.format(format);

                    due_Date_time = java.time.LocalDate.parse(borrow.get_dueDate().trim(), fomart_due_Date).atStartOfDay();

                } catch (DateTimeParseException e) {
                    System.out.println("Nhập sai định dạng (DD-MM-YYYY) nhập lại");
                }
            }

            long days = ChronoUnit.DAYS.between(due_Date_time, borrow_back_Date_time);
            

            if(borrow_list.get(index_borrow).get_number_borrow() > number_borrow_back){
                borrow_list.get(index_borrow).set_number_borrow(borrow_list.get(index_borrow).get_number_borrow()- number_borrow_back);
                book_list.get(index_book).set_quantity(book_list.get(index_book).get_quantity()+number_borrow_back);
                if(days > 0){
                    System.out.println("Bạn đã trả muộn "+ days + " ngày" );
                    String out_date_data = "User ID : " + userId + "  |  User Name : " + (user_list.stream().filter(b->b.get_id().equals(userId)).findFirst().orElse(null)).get_name() +
                                            "\nBook ID : " + bookId + "  |  Title : " + (book_list.stream().filter(b->b.get_id().equals(bookId)).findFirst().orElse(null)).get_title()+
                                            "\nBorrow Date : " + borrow.get_borrowDate() +
                                            "\nDue Date : " + borrow.get_dueDate() +
                                            "\nTrễ : " + days + " ngày"; 
                    out_date_list.add(out_date_data);

                }
                if(days <= 0){
                    System.out.println("Bạn trả đúng ngày");
                }
                DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String now_time = now.format(format_now);

                time_now.add("[ "+ now_time+" ] User " +userId + " (" + (user_list.stream().filter(b->b.get_id().equals(userId)).findFirst().orElse(null)).get_name() + ") trả Book " + bookId + " (" + (book_list.stream().filter(b->b.get_id().equals(bookId)).findFirst().orElse(null)).get_title() +")");


            }
            if(borrow_list.get(index_borrow).get_number_borrow() == number_borrow_back){
                borrow_list.remove(index_borrow);
                book_list.get(index_book).set_quantity(book_list.get(index_book).get_quantity()+number_borrow_back);
                if(days > 0){
                    System.out.println("Bạn đã trả muộn "+ days + " ngày" );
                    String out_date_data = "User ID : " + userId + "  |  User Name : " + (user_list.stream().filter(b->b.get_id().equals(userId)).findFirst().orElse(null)).get_name() +
                                            "\nBook ID : " + bookId + "  |  Title : " + (book_list.stream().filter(b->b.get_id().equals(bookId)).findFirst().orElse(null)).get_title()+
                                            "\nBorrow Date : " + borrow.get_borrowDate() +
                                            "\nDue Date : " + borrow.get_dueDate() +
                                            "\nTrễ : " + days + " ngày"; 
                    out_date_list.add(out_date_data);

                }
                if(days <= 0){
                    System.out.println("Bạn trả đúng ngày");
                }
                DateTimeFormatter format_now = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String now_time = now.format(format_now);

                time_now.add("[ "+ now_time+" ] User " +userId + " (" + (user_list.stream().filter(b->b.get_id().equals(userId)).findFirst().orElse(null)).get_name() + ") trả Book " + bookId + " (" + (book_list.stream().filter(b->b.get_id().equals(bookId)).findFirst().orElse(null)).get_title() +")");
                

            }
            else{
                System.out.println("Không thể trả quá số lượng mượn");
            }


            
        }

    }

    public String data_borrow_convert(Borrow borrow){
        return "userId : " + borrow.get_userId() + "\nname : " +borrow.get_bookId()+"\nnumber book borrow : "+ borrow.get_number_borrow() + "\nborrowDate : " + borrow.get_borrowDate() + "\ndueDate : " +borrow.get_dueDate() + "\nreturnDate : " + borrow.get_returnDate();
    }

    public void show_borrow_information(Borrow Borrow){
        
        System.out.println(data_borrow_convert(Borrow));
        System.out.println("");

    }

    public static int number_user_borrowed_all(ArrayList<User> user_list, String userId){
        User user = user_list.stream().filter(b-> b.get_id().equals(userId))
                                        .findFirst()
                                            .orElse(null);
        
        return user.get_borrow_user_list().stream().mapToInt(Borrow :: get_number_borrow)
                                                    .sum();        
        
    }

    public static int number_book_valiable(ArrayList<Book> book_list, String bookId){
        Book book = book_list.stream().filter(b-> b.get_id().equals(bookId))
                                        .findFirst()
                                            .orElse(null);
        
        return book.get_quantity();
        
    }

    public static int number_book_user_borrow(ArrayList<Borrow> borrow_list, String bookId){
        Borrow borrow = borrow_list.stream().filter(b-> b.bookId.equals(bookId))
                                        .findFirst()
                                            .orElse(null);
        
        return borrow.get_number_borrow();
        
    }
        

    


}
