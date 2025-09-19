import java.util.Scanner;

public class test1{
    public static void main (String[] args){
        int option;
        Scanner scanner = new Scanner(System.in);


        do{
            
            System.out.print("""
            ================= LIBRARY MENU =================
            1. Thêm sách
            2. Xoá sách
            3. Cập nhật thông tin sách
            4. Tìm kiếm sách
            5. Hiển thị danh sách
            6. Thêm người dùng
            7. Hiển thị danh sách người dùng + sách đã mượn
            8. Mượn sách
            9. Trả sách
            0. Thoát (lưu dữ liệu ra file)
            ===============================================
            Chọn chức năng: """);
            option = scanner.nextInt();
            

            switch (option) {
                case 1: {                    
                    Book book = Book.add_information();
                    Library library = new Library();
                    library.them_sach(book);
                    break;
                }
                    
                case 2:
                    Library.find_delete();
                    break;
                case 3:
                    Library.find_replace();
                    break;
                case 4:
                    Library.find();
                    break;
                case 5:
                    Library.show();

                    break;
                    
                case 6:
                    User user = User.add_information();

                    user.them_user();
                    break;
                    
                case 7:
                    User.show();
                    break;
                case 8:
                    User.muon_sach();
                    break;
                case 9:
                    User.tra_sach();
                    break;
                case 0: 
                    
                default: 
            }
            
        }while(option != 0);
        System.out.println("Tạm biệt ...");
        
        scanner.close();

    }
}