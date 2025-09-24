import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




public class Library {
    private static final String FILE_PATH = "C:\\Users\\Mr.Onion216\\Desktop\\Library.txt";
    private static final String FILE_PATH_1 = "C:\\Users\\Mr.Onion216\\Desktop\\BorrowBook.txt";


    public void them_sach(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write("Mã số duy nhất: " + book.getId()
                    + "\nTên sách: " + book.getTitle()
                    + "\nTác giả: " + book.getAuthor()
                    + "\nNăm xuất bản: " + book.getYear()
                    + "\nSố lượng còn lại trong thư viện: " + book.getQuantity());
            writer.newLine();
            writer.write("---------------------------");
            writer.newLine();
            System.out.println("Thêm sách mới thành công");
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void show(){
        try(BufferedReader read = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while((line = read.readLine() )!= null){
                System.out.println(line);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void find(){
        String find_word;
        Scanner scanner  = new Scanner(System.in);
        System.out.print("Bạn muốn tìm sách gì (id hoặc tên): ");
        find_word = scanner.nextLine();
        try(BufferedReader read = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            while((line = read.readLine())!= null){
                correctbook.add(line);
                if(line.startsWith("----------------")){
                    check(correctbook, find_word);
                    correctbook.clear();
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void check(ArrayList<String> A, String B){
        if(!A.isEmpty()){
            String Firstline = A.get(0 ); 
            String Secondline = A.get(1);
            if(Firstline.contains(B) || Secondline.contains(B)){
                System.out.println("Sách bạn cần tìm: ");
                for(String l : A){
                    System.out.println(l);
                }
                System.out.println("(----------------");
            }

        }
    }
    
    public static void find_delete(){
        String find_word;
        Scanner scanner  = new Scanner(System.in);
        System.out.print("Bạn muốn xóa sách gì (id hoặc tên): ");
        find_word = scanner.nextLine();
        try(BufferedReader read = new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            ArrayList<String> new_library = new ArrayList<>();
            while((line = read.readLine())!= null){
                correctbook.add(line);
                if(line.startsWith("----------------")){
                    if(check_delete(correctbook, find_word)){
                        correctbook.clear();    
                    }
                    else{
                        for(String l : correctbook){
                            new_library.add(l);
                        }
                        correctbook.clear();
                    }
                    
                }
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for(String l : new_library){
                    writer.write(l);
                    writer.newLine();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }

    public static boolean check_delete(ArrayList<String> A, String B){
        if(!A.isEmpty()){
            String Firstline = A.get(0 ); 
            String Secondline = A.get(1);
            return Firstline.contains(B) || Secondline.contains(B);

        }
        return true;
    }
    

    public static void find_replace() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bạn muốn cập nhật thông tin sách gì (id hoặc tên): ");
        String find_word = scanner.nextLine();

        try (BufferedReader read = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            ArrayList<String> correctbook = new ArrayList<>();
            ArrayList<String> new_library = new ArrayList<>();

            while ((line = read.readLine()) != null) {
                correctbook.add(line);

                if (line.startsWith("----------------")) {
                    
                    if (check_replace(correctbook, find_word)) {
                        int option;
                        do {
                            System.out.print("""
                                    Bạn muốn đổi thông tin gì ? 
                                    1.  Mã số duy nhất 
                                    2.  Tên sách 
                                    3.  Tác giả
                                    4.  Năm xuất bản
                                    5.  Số lượng còn lại trong thư viện
                                    0.  Thoát 

                                    Chọn chức năng :  """);

                            option = scanner.nextInt();
                            scanner.nextLine();

                            switch (option) {
                                case 1 -> {
                                    System.out.print("id sách mới: ");
                                    String id = scanner.nextLine();
                                    correctbook.set(0, "Mã số duy nhất: " + id);
                                }
                                case 2 -> {
                                    System.out.print("Tên sách mới: ");
                                    String title = scanner.nextLine();
                                    correctbook.set(1, "Tên sách: " + title);
                                }
                                case 3 -> {
                                    System.out.print("Tác giả mới: ");
                                    String author = scanner.nextLine();
                                    correctbook.set(2, "Tác giả: " + author);
                                }
                                case 4 -> {
                                    System.out.print("Năm xuất bản mới: ");
                                    int year = scanner.nextInt();
                                    scanner.nextLine();
                                    correctbook.set(3, "Năm xuất bản: " + year);
                                }
                                case 5 -> {
                                    System.out.print("Số lượng mới: ");
                                    int quantity = scanner.nextInt();
                                    scanner.nextLine();
                                    correctbook.set(4, "Số lượng còn lại trong thư viện: " + quantity);
                                }
                                case 0 -> System.out.println("Thoát chỉnh sửa...");
                                default -> System.out.println("Chức năng không hợp lệ!");
                            }

                        } while (option != 0);

                        // ghi lại sách đã chỉnh sửa
                        new_library.addAll(correctbook);
                        correctbook.clear();
                    } 
                    else {
                        // nếu không match thì giữ nguyên
                        for(String l : correctbook){
                            new_library.add(l);
                        }
                        correctbook.clear();
                    }
                }
            }

            // ghi lại toàn bộ file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String l : new_library) {
                    writer.write(l);
                    writer.newLine();
                }
            }
            System.out.println("Đã cập nhật thông tin thành công!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean check_replace(ArrayList<String> A, String B) {
        if (!A.isEmpty()) {
            for (String line : A) {
                if (line.contains(B)) return true;
            }
        }
        return false;
}

    
    
}
