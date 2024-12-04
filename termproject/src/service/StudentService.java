package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import entity.EnrollmentRequest;
import entity.Student;

public class StudentService {

    public static void applyForMembership() {
        Scanner scanner = new Scanner(System.in);

        // 가입 신청 유형 선택
        System.out.println("신청할 유형을 선택하세요:");
        System.out.println("1. 동아리 가입");
        System.out.println("2. 스터디 가입");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // 동아리 가입 신청
            applyForClub();
        } else if (choice == 2) {
            // 스터디 가입 신청
            applyForStudyGroup();
        } else {
            System.out.println("잘못된 선택입니다. 다시 시도하세요.");
        }
    }

    // 동아리 가입 신청
    public static void applyForClub() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("학생 ID를 입력하세요: ");
        int studentId = scanner.nextInt();

        // EnrollmentRequest 객체 생성 및 값 설정
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(studentId);
        request.setClubId(null); // 동아리 가입 신청이므로 NULL
        request.setStudyGroupId(null); // 스터디 가입 아님
        request.setStatus("pending"); // 기본 상태
        request.setRequestDate(java.time.LocalDate.now().toString()); // 현재 날짜 설정

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 실행
            String query = "INSERT INTO ENROLLMENT_REQUEST (Status, RequestDate, StudentID, ClubID, StudyGroupID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, request.getStatus());
            preparedStatement.setString(2, request.getRequestDate());
            preparedStatement.setInt(3, request.getStudentId());
            preparedStatement.setNull(4, java.sql.Types.INTEGER); // ClubID는 NULL
            preparedStatement.setNull(5, java.sql.Types.INTEGER); // StudyGroupID는 NULL

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("동아리 가입 신청이 성공적으로 제출되었습니다!");
            } else {
                System.out.println("동아리 가입 신청에 실패했습니다.");
            }

        } catch (Exception e) {
            System.err.println("동아리 가입 신청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 스터디 가입 신청
    public static void applyForStudyGroup() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("스터디가 속한 동아리 ID를 입력하세요: ");
        int clubId = scanner.nextInt();

        System.out.println("학생 ID를 입력하세요: ");
        int studentId = scanner.nextInt();

        // EnrollmentRequest 객체 생성 및 값 설정
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(studentId);
        request.setClubId(clubId); // 동아리 ID 설정
        request.setStudyGroupId(null); // 스터디 가입 신청이므로 NULL
        request.setStatus("pending"); // 기본 상태
        request.setRequestDate(java.time.LocalDate.now().toString()); // 현재 날짜 설정

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 실행
            String query = "INSERT INTO ENROLLMENT_REQUEST (Status, RequestDate, StudentID, ClubID, StudyGroupID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, request.getStatus());
            preparedStatement.setString(2, request.getRequestDate());
            preparedStatement.setInt(3, request.getStudentId());
            preparedStatement.setInt(4, request.getClubId()); // ClubID는 입력받은 값
            preparedStatement.setNull(5, java.sql.Types.INTEGER); // StudyGroupID는 NULL

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("스터디 가입 신청이 성공적으로 제출되었습니다!");
            } else {
                System.out.println("스터디 가입 신청에 실패했습니다.");
            }

        } catch (Exception e) {
            System.err.println("스터디 가입 신청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
        public static void addStudentInfo() {
            Scanner scanner = new Scanner(System.in);

            System.out.println("학생 학번을 입력하세요: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            // 학생 정보 입력 받기
            System.out.println("학생 이름을 입력하세요: ");
            String name = scanner.nextLine();

            System.out.println("학생 나이를 입력하세요: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // 버퍼 정리

            System.out.println("학생 성별을 입력하세요 (M/F): ");
            String sex = scanner.nextLine();

            System.out.println("학생 전화번호를 입력하세요: ");
            String phoneNumber = scanner.nextLine();

            // UniversityName은 기본값 'Chungbuk University'로 설정됨
            String universityName = "Chungbuk University";

            // Student 객체 생성 및 값 설정
            Student student = new Student();
            student.setStudentId(id);
            student.setName(name);
            student.setAge(age);
            student.setSex(sex);
            student.setPhoneNumber(phoneNumber);
            student.setUniversityName(universityName);

            // 데이터베이스에 삽입
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO STUDENT (StudentID, Name, Age, Sex, PhoneNumber,UniversityName) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                // 객체에서 값 가져오기
                preparedStatement.setInt(1, student.getStudentId());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setInt(3, student.getAge());
                preparedStatement.setString(4, student.getSex());
                preparedStatement.setString(5, student.getPhoneNumber());
                preparedStatement.setString(6, student.getUniversityName()); // 기본값 사용

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("학생 정보가 성공적으로 추가되었습니다!");
                } else {
                    System.out.println("학생 정보 추가에 실패했습니다.");
                }

            } catch (Exception e) {
                System.err.println("학생 정보 추가 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
    
}


