package ui;

import java.util.Scanner;
import service.AdminService;
import service.ProcessEnrollmentRequest;

public class AdminMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("**************************************************************************");
            System.out.println("1. 동아리 조회 		    2. 동아리 추가 		    3. 동아리 삭제");
            System.out.println("4. 동아리 멤버 조회	    	    5. 스터디 조회		    6. 스터디 추가");
            System.out.println("7. 스터디 삭제		    8. 스터디 멤버 조회 	    9. 전체 학생 조회");
            System.out.println("10. 요청 처리 기록 조회   	    11. 가입 요청 승인/거절      12. 메인 메뉴로");
            System.out.println("**************************************************************************");

            System.out.print("업무 번호를 입력하세요: ");
            int choice = scanner.nextInt();
            
            
            switch (choice) {
                case 1:
                    AdminService.viewClubs(); // 동아리 조회
                    break;
                case 2:
                    AdminService.addClub(); // 동아리 추가 
                    break;
                case 3:
                    AdminService.deleteClub(); // 동아리 삭제 
                    break;
                case 4:
                    AdminService.viewClubMember(); // 동아리 멤버 조회
                    break;
                case 5:
                    AdminService.viewStudyGroups(); // 스터디 조회 
                    break;
                case 6:
                    AdminService.addStudyGroup(); // 스터디 추가 
                    break;
                case 7:
                    AdminService.deleteStudyGroup(); // 스터디 삭제
                    break;   
                case 8:
                    AdminService.viewStudyGroupMembersByClub(); // 스터디 멤버 조회 
                    break;
                case 9:
                    AdminService. viewAllStudents(); // 전체 학생 조회
                    break;
                case 10:
                    AdminService. viewEnrollmentRequestAdminRecords(); // 요청 처리 기록 조회
                    break;
                case 11:
                	ProcessEnrollmentRequest.processEnrollmentRequest(); // 가입 요청 승인/거절
                    break;
                case 12:
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }
}
