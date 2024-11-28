package termproject;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.201.3:4567/madang", "hyunwoo", "database2024!!");
            Statement stmt = con.createStatement(); // stmt: 데이터베이스에 sql쿼리를 보내고 실행하는 역할

            Scanner scanner = new Scanner(System.in); // 키보드로 입력을 받겠다.
            int choice;

            do {
                System.out.println("---------------------");
                System.out.println("1. connection");
                System.out.println("2. find ****");
                System.out.println("3. insert ****");
                System.out.println("4. ******");
                System.out.println("5. ******");
                System.out.println("6. ******");
                System.out.println("7. ******");
                System.out.println("8. ******");
                System.out.println("99. quit");
                System.out.println("---------------------");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt(); // 입력받은 숫자를 정수로 바꿔서 저장

                switch (choice) { // 어느 번호에 해당하는지 비교
                    case 1:
                        // 구현 로직
                        System.out.println("Connecting to the database...");
                        break;
                    case 2:
                        // 구현 로직
                        System.out.println("Finding ****...");
                        break;
                    case 3:
                    	// 구현 로직
                        System.out.println("Inserting ****...");
                        break;
                    case 4:
                    	// 구현 로직
                        System.out.println("Executing ******...");
                        break;
                    case 5:
                    	// 구현 로직
                        System.out.println("Executing ******...");
                        break;
                    case 6:
                    	// 구현 로직
                        System.out.println("Executing ******...");
                        break;
                    case 7:
                    	// 구현 로직
                        System.out.println("Executing ******...");
                        break;
                    case 8:
                    	// 구현 로직
                        System.out.println("Executing ******...");
                        break;
                    case 99:
                    	// 구현 로직
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 99);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}