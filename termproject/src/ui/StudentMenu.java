package ui;

import java.util.Scanner;
import service.StudentService;

public class StudentMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*********************************");
            System.out.println("학생 메뉴");
            System.out.println("1. 학생 정보 기입 | 2. 동아리 가입 신청");
            System.out.println("3. 스터디 가입 신청 | 4. 가입 신청 상태 조회");
            System.out.println("5. 메인 메뉴로");
            System.out.println("**********************************");

            System.out.print("선택하세요: ");
            int choice = scanner.nextInt();

            switch (choice) {
            	case 1:
            		StudentService.addStudentInfo(); // 학생 정보 기입 기능
            		break;
            	case 2:
                    StudentService.applyForClub(); // 동아리 가입 신청 기능
                    break;
                case 3:
                	StudentService.applyForStudyGroup(); // 스터디 가입 신청 기능
                    break;
                case 4:
                	StudentService.viewEnrollmentStatus(); // 가입 신청 상태 조회
                    break;
                case 5:
                    System.out.println("메인 메뉴");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }
}
