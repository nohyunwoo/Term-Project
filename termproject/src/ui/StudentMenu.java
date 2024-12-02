package ui;

//import services.StudentService;

import java.util.Scanner;
import service.StudentService;

public class StudentMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
//        StudentService studentService = new StudentService();

        while (true) {
            System.out.println("=================================");
            System.out.println("학생 메뉴");
            System.out.println("1. 클럽 가입 신청");
            System.out.println("2. 스터디 가입 신청");
            System.out.println("3. 가입 신청 상태 조회");
            System.out.println("4. 메인 메뉴로 돌아가기");
            System.out.println("=================================");

            System.out.print("선택하세요: ");
            int choice = scanner.nextInt();

//            switch (choice) {
//                case 1:
//                    StudentService.applyForClub(); // 클럽 가입 신청 기능
//                    break;
//                case 2:
//                	StudentService.applyForStudyGroup(); // 스터디 가입 신청 기능
//                    break;
//                case 3:
//                	StudentService.viewEnrollmentStatus(); // 가입 신청 상태 조회
//                    break;
//                case 4:
//                    System.out.println("메인 메뉴로 돌아갑니다.");
//                    return;
//                default:
//                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
//            }
        }
    }
}
