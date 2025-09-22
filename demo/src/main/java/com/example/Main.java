import com.google.gson.Gson;
public class main {
    String FILE_OUT_DATE = "demo\\src\\main\\java\\com\\example\\out_date_log.json";
    String FILE_BOOKS = "demo\\src\\main\\java\\com\\example\\books.json";
    String FILE_USERS = "demo\\src\\main\\java\\com\\example\\users.json";
    String FILE_BORROWS = "demo\\src\\main\\java\\com\\example\\borrows.json";

    public static void main(String[] args) {
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
                Lựa chọn của b là : 
             """);

            switch (x) {
                case 1 :
                    User.new_oop_user();
                    
                    break;
                case 2:

                    break;
                
                case 3:

                    break;

                case 4:

                    break;

                case 5: 

                    break;

                case 6:

                    break;

                case 7:

                    break;

                case 8:

                    break;

                case 9:

                    break;

                case 0:

                    break;

                default:
                    throw new AssertionError();
            }
        } while (x != 0);
    }

}
