package ui;

import java.util.Scanner;

public class SelectMenu {
    public static void displayMenu() {
        System.out.println("1. 관리자");
        System.out.println("2. 학생");
        System.out.println("0. 종료");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            displayMenu();
            System.out.print("선택하세요: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    AdminMenu.showMenu();
                    break;
                case 2:
                    StudentMenu.showMenu();
                    break;
                case 0:
                    System.out.println("프로그램 종료.");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }
}
