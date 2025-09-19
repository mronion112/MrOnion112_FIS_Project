// 3.2. Quản lý người dùng

// Mỗi User có thông tin:
// id
// cccd
// name
// birthdate
// address
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User 
{
    String id;
    String cccd;
    String name;
    String birthdate;
    String address;

    public static User add_information(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("id : ");
        String id = scanner.nextLine();
        //scanner.next();
        System.out.print("\nSố cccd: ");
        String cccd = scanner.nextLine();
        //scanner.next();
        System.out.print("\nHọ và Tên: ");
        String name = scanner.nextLine();
        //scanner.next();
        System.out.print("\nNgày/tháng/năm sinh: ");
        String birthdate = scanner.nextLine();
        System.out.print("\nĐịa chỉ nhà: ");
        String address = scanner.nextLine();
        //scanner.next(); 

        return new User(id, cccd, name, birthdate, address);
    }


    public User( String id, String cccd, String name, String birthdate, String address) {
        this.id =id;
        this.cccd = cccd;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
        
    }

    private static final String FILE_PATH_USER = "C:\\Users\\Mr.Onion216\\Desktop\\Userproperty.txt";
    private static final String FILE_PATH_BORROW_BOOK = "C:\\Users\\Mr.Onion216\\Desktop\\BorrowBook.txt";
    private static final String FILE_PATH_BOOK = "C:\\Users\\Mr.Onion216\\Desktop\\Library.txt";



    public void them_user() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_USER, true))) {
            writer.write("ID: " + this.id
                    + "\nSố cccd: " + this.cccd
                    + "\nHọ và tên: " + this.name
                    + "\nNgày/ tháng/ năm sinh: " + this.birthdate
                    + "\nĐịa chỉ nhà: " + this.address
                    + "\nSách đã mượn: ");
                    
            writer.newLine();
            writer.write("---------------------------");
            writer.newLine();
            System.out.println("Thêm người dùng mới thành công");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void show(){
        try(BufferedReader read = new BufferedReader(new FileReader(FILE_PATH_USER))){
            String line;
            while((line = read.readLine() )!= null){
                System.out.println(line);

            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void muon_sach(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập ID người mượn ");
        String user_id = scanner.nextLine();

        System.out.println("Nhập ID sách: ");
        String book_id = scanner.nextLine();

        System.out.println("Bạn đang có " + book_numer(book_id) + " quyển sách ");

        System.out.println("Nhập số lượng muốn mượn: ");
        int number_book_borrow = scanner.nextInt();

        change_book_number_minus(number_book_borrow, book_id);

        if(is_idbook_valiable_in_borrow_book(user_id, book_id )){
            change_borrow_book_information_add(user_id, book_id, number_book_borrow);
            change_user_borrow_book_information();
        }
        else{
            add_borrow_book_information(user_id, book_id, number_book_borrow);
            add_user_borrow_book_information(user_id, book_id, number_book_borrow);
        }
    }

    public static void tra_sach(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập ID người mượn ");
        String user_id = scanner.nextLine();

        System.out.println("Nhập ID sách: ");
        String book_id = scanner.nextLine();

        int number_book_user_borrow = borrow_book_number_in_file(user_id, book_id);
        if(number_book_user_borrow == 0){
            System.out.println("Bạn k mượn cái nào cả");
        }
        else{
            System.out.println("Bạn đang mượn " + number_book_user_borrow   + " quyển sách ");

            System.out.println("Nhập số lượng muốn trả:  ");
            int number_book_return = scanner.nextInt();

            
            if(number_book_return > number_book_user_borrow ){
                System.out.println("Bạn k có nhiều sách đến v ");
            }

            if(number_book_return == number_book_user_borrow){
                change_book_number_add(number_book_return, book_id);
                delete_borrow_book_information(user_id, book_id);
                change_user_borrow_book_information();

            }

            else{
                change_book_number_add(number_book_return, book_id);
                change_borrow_book_information_minus(user_id, book_id, number_book_return);
                change_user_borrow_book_information();

            }
        }
    }

    
    public static void change_book_number_minus(int number_minus, String id){

        if(book_numer(id) < number_minus){
            System.out.println("Số lượng không cho phép");
        }
        else{
            int reamain_book = book_numer(id) - number_minus; 

            try(BufferedReader read_book = new BufferedReader(new FileReader(FILE_PATH_BOOK))){
                String l;
                ArrayList<String> current_book = new ArrayList<>();
                ArrayList<String> new_book = new ArrayList<>();
                while((l = read_book.readLine())!= null){
                    current_book.add(l);
                    if(l.startsWith("----------------")){
                        if(is_exist_id_book(current_book, id)){
                            current_book.set(4, "Số lượng còn lại trong thư viện: "+ reamain_book);

                        }
                        for(String a : current_book){
                            new_book.add(a);
                        }
                        current_book.clear();
                    }
                }
                try(BufferedWriter write_book = new BufferedWriter(new FileWriter(FILE_PATH_BOOK))){
                    for(String b : new_book){
                        write_book.write(b);
                        write_book.newLine();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
                


            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void change_book_number_add(int number_plus, String id){

        
        int reamain_book = book_numer(id) + number_plus; 

        try(BufferedReader read_book = new BufferedReader(new FileReader(FILE_PATH_BOOK))){
            String l;
            ArrayList<String> current_book = new ArrayList<>();
            ArrayList<String> new_book = new ArrayList<>();
            while((l = read_book.readLine())!= null){
                current_book.add(l);
                if(l.startsWith("----------------")){
                    if(is_exist_id_book(current_book, id)){
                        current_book.set(4, "Số lượng còn lại trong thư viện: "+ reamain_book);

                    }
                    for(String a : current_book){
                        new_book.add(a);
                    }
                    current_book.clear();
                }
            }
            try(BufferedWriter write_book = new BufferedWriter(new FileWriter(FILE_PATH_BOOK))){
                for(String b : new_book){
                    write_book.write(b);
                    write_book.newLine();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            


        }catch(IOException e){
            e.printStackTrace();
        }
        

    }

    public static void delete_borrow_book_information(String user_id, String book_id){
        try(BufferedReader borrow_book_reader = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))){
            String l;
            ArrayList<String> current_borrow_book = new ArrayList<>();
            ArrayList<String> new_borrow_book = new ArrayList<>();

            while((l = borrow_book_reader.readLine())!= null){
                current_borrow_book.add(l);
                if(l.startsWith("----------------")){

                    if(is_exist_id_user(current_borrow_book, user_id) == true && is_exist_id_book(current_borrow_book, book_id) == true){
                        current_borrow_book.clear();
                    }
                    new_borrow_book.addAll(current_borrow_book);
                    current_borrow_book.clear();
                }

            }
            try(BufferedWriter borrow_book_writter = new BufferedWriter(new FileWriter(FILE_PATH_BORROW_BOOK))){
                for(String A: new_borrow_book){
                    borrow_book_writter.write(A);
                    borrow_book_writter.newLine();
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    
    
    public static boolean is_idbook_valiable_in_borrow_book(String user_id, String book_id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))) {
            String line;
            ArrayList<String> block = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                block.add(line);

                if (line.startsWith("----------------")) {
                    if(block.get(0).contains(user_id) && block.get(1).contains(book_id)){
                        return true;
                    }
                    block.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }



    public static void change_borrow_book_information_add(String id_user, String id_book, int number_plus) {
        try (BufferedReader borrow_book_reader = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))) {
            String l;
            ArrayList<String> current_borrow_book = new ArrayList<>();
            ArrayList<String> new_borrow_book = new ArrayList<>();

            while ((l = borrow_book_reader.readLine()) != null) {
                current_borrow_book.add(l);

                if (l.startsWith("----------------")) {
                    String userLine = current_borrow_book.get(0); 
                    String bookLine = current_borrow_book.get(1);
                    String soLuongLine = current_borrow_book.get(2);


                    if (userLine.contains(id_user) && bookLine.contains(id_book) ) {
                        int old_number = Integer.parseInt(soLuongLine.substring(soLuongLine.lastIndexOf(":") + 1).trim());
                        current_borrow_book.set(2, "Số lượng mượn: " + (old_number + number_plus));
                    }

                    new_borrow_book.addAll(current_borrow_book);
                    current_borrow_book.clear();
                }
            }




            try (BufferedWriter borrow_book_writer = new BufferedWriter(new FileWriter(FILE_PATH_BORROW_BOOK))) {
                for (String b : new_borrow_book) {
                    borrow_book_writer.write(b);
                    borrow_book_writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void change_borrow_book_information_minus(String id_user, String id_book, int number_minus) {
        try (BufferedReader borrow_book_reader = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))) {
            String l;
            ArrayList<String> current_borrow_book = new ArrayList<>();
            ArrayList<String> new_borrow_book = new ArrayList<>();

            while ((l = borrow_book_reader.readLine()) != null) {
                current_borrow_book.add(l);

                if (l.startsWith("----------------")) {
                    String userLine = current_borrow_book.get(0); 
                    String bookLine = current_borrow_book.get(1);
                    String soLuongLine = current_borrow_book.get(2);


                    if (userLine.contains(id_user) && bookLine.contains(id_book) ) {
                        int old_number = Integer.parseInt(soLuongLine.substring(soLuongLine.lastIndexOf(":") + 1).trim());
                        current_borrow_book.set(2, "Số lượng mượn: " + (old_number - number_minus));
                    }

                    new_borrow_book.addAll(current_borrow_book);
                    current_borrow_book.clear();
                }
            }




            try (BufferedWriter borrow_book_writer = new BufferedWriter(new FileWriter(FILE_PATH_BORROW_BOOK))) {
                for (String b : new_borrow_book) {
                    borrow_book_writer.write(b);
                    borrow_book_writer.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void add_borrow_book_information(String id_user, String id_book, int number_book_borrow){
        try(BufferedWriter borrow_book_writter = new BufferedWriter(new FileWriter(FILE_PATH_BORROW_BOOK, true))){
            borrow_book_writter.write("ID người mượn: "+id_user
                                    +"\nID sách: "+ id_book
                                    +"\nSố lượng mượn: "+number_book_borrow); 
            borrow_book_writter.newLine();
            borrow_book_writter.write("----------------");
            borrow_book_writter.newLine();
                                


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void add_user_borrow_book_information(String id_user, String id_book, int number_book_borrow){
        try(BufferedReader user_reader = new BufferedReader(new FileReader(FILE_PATH_USER))){
            String l;
            ArrayList<String> current_user = new ArrayList<>();
            ArrayList<String> new_user = new ArrayList<>();

            while((l = user_reader.readLine())!= null){
                current_user.add(l);
                if(l.startsWith("----------------")){
                    if(is_exist_id_user(current_user, id_user) == true){
                        current_user.remove(current_user.size()-1);
                        current_user.add("ID sách: "+ id_book+" - Số lượng mượn: "+number_book_borrow);
                        current_user.add("----------------");
                    }
                    
                    for(String a : current_user){
                        new_user.add(a);
                    }
                    current_user.clear();
                }
            }
            
            try(BufferedWriter user_writer = new BufferedWriter(new FileWriter(FILE_PATH_USER))){
                for(String b : new_user){
                    user_writer.write(b);
                    user_writer.newLine();
                }
            }catch(IOException e){
                e.printStackTrace();
            }


        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void change_user_borrow_book_information() {
        try (BufferedReader user_reader = new BufferedReader(new FileReader(FILE_PATH_USER))) {
            String l;
            ArrayList<String> current_user = new ArrayList<>();
            ArrayList<String> new_user = new ArrayList<>();

            while ((l = user_reader.readLine()) != null) {
                current_user.add(l);

                if (l.startsWith("----------------")) {
                    String id_user = current_user.get(0).substring(current_user.get(0).lastIndexOf(":") + 1).trim();

                    ArrayList<String> cleaned_user = new ArrayList<>();
                    for (String line : current_user) {
                        if (!line.startsWith("ID sách:")) cleaned_user.add(line);
                    }
                    cleaned_user.remove(cleaned_user.size() - 1); 

                    if (is_exist_id_user(current_user, id_user)) {
                        ArrayList<String> borrowed = collect_all_borrow_book_follow_id_user(id_user );
                        cleaned_user.addAll(borrowed);
                    }

                    cleaned_user.add("---------------------------");

                    new_user.addAll(cleaned_user);
                    current_user.clear();
                }
            }

            try (BufferedWriter user_writer = new BufferedWriter(new FileWriter(FILE_PATH_USER))) {
                for (String b : new_user) {
                    user_writer.write(b);
                    user_writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}



    public static String find_user_id_in_array(ArrayList<String> block){
        String A = block.get(0);
        return A.substring(A.indexOf(":")+1).trim();
    }


    


    public static ArrayList<String> collect_all_borrow_book_follow_id_user(String id_user){
        ArrayList<String> borrow_book_information_follow_id_user = new ArrayList<>();
        try(BufferedReader borrow_book_reader = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))){
            String l;
            ArrayList<String> current_borrow_book = new ArrayList<>();

            while((l = borrow_book_reader.readLine()) != null){
                current_borrow_book.add(l);
                if(l.startsWith("----------------")){
                    if(is_exist_id_user_borrow(current_borrow_book, id_user) == true ){
                        borrow_book_information_follow_id_user.add(current_borrow_book.get(1) + " - " +current_borrow_book.get(2));

                    }
                    current_borrow_book.clear();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return borrow_book_information_follow_id_user;
    }



    public static boolean is_exist_id_book(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            String Firstline = A.get(0);
            if (Firstline.equals("Mã số duy nhất: "+ B)) {
                return true;
            }
            return false;
        }
        return false; 
    }
    public static boolean is_exist_id_user(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            String Firstline = A.get(0);
            if (Firstline.equals("ID: "+ B)) {
                return true;
            }
            return false;
        }
        return false; 
    }
    public static boolean is_exist_id_user_borrow(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            String Firstline = A.get(0);
            if (Firstline.equals("ID người mượn: "+ B)) {
                return true;
            }
            return false;
        }
        return false; 
    }
    public static boolean is_exist_id_book_borrow(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            String Firstline = A.get(0);
            if (Firstline.equals("ID sách: "+ B)) {
                return true;
            }
            return false;
        }
        return false; 
    }

    public static int book_numer(String id ){
        Scanner scanner  = new Scanner(System.in);
        int number_book;
        try (BufferedReader read = new BufferedReader(new FileReader(FILE_PATH_BOOK))) {
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            while ((line = read.readLine()) != null) {
                correctbook.add(line);
                if (line.startsWith("----------------")) {
                    String result = book_number_line(correctbook, id);
                    if (result != null) {
                        number_book = Integer.parseInt(result.substring(result.lastIndexOf(":") + 1).trim());
                        return number_book;
                    }
                    correctbook.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String book_number_line(ArrayList<String> A, String B){
        if(!A.isEmpty()){
            String Firstline = A.get(0 ); 
            String Secondline = A.get(1);
            if(Firstline.contains(B) || Secondline.contains(B)){
                return A.get(4).trim();            
            }

        }
        return null;
    }
    public static String book_name_line(ArrayList<String> A, String B){
        if(!A.isEmpty()){
            String Firstline = A.get(0 ); 
            String Secondline = A.get(1);
            if(Firstline.contains(B) || Secondline.contains(B)){
                return A.get(1).trim();            
            }

        }
        return null;
    }

    public static String book_name(String id ){
        Scanner scanner  = new Scanner(System.in);
        String number_book;
        try (BufferedReader read = new BufferedReader(new FileReader(FILE_PATH_BOOK))) {
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            while ((line = read.readLine()) != null) {
                correctbook.add(line);
                if (line.startsWith("----------------")) {
                    String result = book_name_line(correctbook, id);
                    if (result != null) {
                        number_book = result.substring(result.lastIndexOf(":") + 1).trim();
                        return number_book;
                    }
                    correctbook.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String find_user_id(String id ){
        Scanner scanner  = new Scanner(System.in);
        String number_book;
        try (BufferedReader read = new BufferedReader(new FileReader(FILE_PATH_USER))) {
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            while ((line = read.readLine()) != null) {
                correctbook.add(line);
                if (line.startsWith("----------------")) {
                    String result = user_id_line(correctbook, id);
                    if (result != null) {
                        number_book = result.substring(result.lastIndexOf(":") + 1).trim();
                        return number_book;
                    }
                    correctbook.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static String user_id_line(ArrayList<String> A, String B){
        if(!A.isEmpty()){
            String Firstline = A.get(0 ); 
            String Secondline = A.get(1);
            if(Firstline.contains(B) || Secondline.contains(B)){
                return A.get(0).trim();            
            }

        }
        return null;
    }

    public static int book_borrow_numer(String id) {
        int number_book;
        try (BufferedReader read = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))) {
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            while ((line = read.readLine()) != null) {
                correctbook.add(line);
                if (line.startsWith("----------------")) {
                    String result = borrow_book_number_line(correctbook, id);
                    if (result != null) {
                        number_book = Integer.parseInt(result.substring(result.lastIndexOf(":") + 1).trim());
                        return number_book;
                    }
                    correctbook.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String borrow_book_number_line(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            String Firstline = A.get(0);
            String Secondline = A.get(1);
            if (Firstline.contains(B) || Secondline.contains(B)) {
                return A.get(2).trim();
            }
        }
        return null;
    }

    public static boolean  check_exist(ArrayList<String> A, String B){
        for(String l : A){
            if(l.contains(B)){
                return true;
            }
        }
        return false;

    }

    public static int borrow_book_number_in_file(String id_user, String id_book){
        try(BufferedReader borrow_book_read = new BufferedReader(new FileReader(FILE_PATH_BORROW_BOOK))){
            String l;
            ArrayList<String> current_borrow_book = new ArrayList<>();
            while((l = borrow_book_read.readLine())!= null){
                current_borrow_book.add(l);
                if(l.startsWith("----------------")){

                    if(check_exist(current_borrow_book,id_user) == true && check_exist(current_borrow_book,id_book) == true){
                        String A = current_borrow_book.get(2);
                        return Integer.parseInt(A.substring(A.lastIndexOf(":")+1).trim());

                    }
                    current_borrow_book.clear();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
    