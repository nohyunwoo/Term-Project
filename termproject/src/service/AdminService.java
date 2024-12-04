package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import entity.Club;


public class AdminService {

	public static void viewClubs() {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // SQL 실행
	        String query = "SELECT ClubID, ClubName, UniversityName FROM CLUB";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        System.out.println("Clubs List:");
	        while (resultSet.next()) {
	            // Club 객체 생성 및 데이터 설정
	            Club club = new Club();
	            club.setClubId(resultSet.getInt("ClubID"));
	            club.setClubName(resultSet.getString("ClubName"));

	            // Club 객체 데이터 출력
	            System.out.printf("Club ID: %d, Name: %s%n", club.getClubId(), club.getClubName());
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while fetching clubs: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    
	public static void addClub() {
	    Scanner scanner = new Scanner(System.in);

	    // 사용자 입력 받기
	    System.out.println("동아리 이름을 입력하세요: ");
	    String clubName = scanner.nextLine();

	    // Club 객체 생성 및 값 설정
	    Club club = new Club();
	    club.setClubName(clubName);

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // SQL 실행
	        String query = "INSERT INTO CLUB (ClubName) VALUES (?)";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);

	        // Club 객체에서 값 가져오기
	        preparedStatement.setString(1, club.getClubName());

	        int rowsInserted = preparedStatement.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("New club added successfully!");
	        } else {
	            System.out.println("Failed to add club.");
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while adding club: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    
	public static void deleteClub() {
	    Scanner scanner = new Scanner(System.in);
	    
	    // 사용자 입력 받기
	    System.out.println("삭제할 클럽 ID를 입력하세요: ");
	    int clubId = scanner.nextInt();

	    // Club 객체 생성 및 값 설정
	    Club club = new Club();
	    club.setClubId(clubId); // 삭제할 ClubID 설정

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // 1. ENROLLMENT_REQUEST 테이블에서 관련 데이터 삭제
	        String deleteEnrollmentRequestQuery = "DELETE FROM ENROLLMENT_REQUEST WHERE ClubID = ?";
	        PreparedStatement deleteEnrollmentStmt = connection.prepareStatement(deleteEnrollmentRequestQuery);
	        deleteEnrollmentStmt.setInt(1, club.getClubId());
	        int enrollmentRowsDeleted = deleteEnrollmentStmt.executeUpdate();
	        if (enrollmentRowsDeleted > 0) {
	            System.out.println("ENROLLMENT_REQUEST 테이블에서 관련 데이터가 성공적으로 삭제되었습니다!");
	        } else {
	            System.out.println("ENROLLMENT_REQUEST 테이블에 관련 데이터가 없습니다.");
	        }

	        // 2. CLUB_MEMBER 테이블에서 관련 데이터 삭제
	        String deleteMembersQuery = "DELETE FROM CLUB_MEMBER WHERE ClubID = ?";
	        PreparedStatement deleteMembersStmt = connection.prepareStatement(deleteMembersQuery);
	        deleteMembersStmt.setInt(1, club.getClubId());
	        int memberRowsDeleted = deleteMembersStmt.executeUpdate();
	        if (memberRowsDeleted > 0) {
	            System.out.println("CLUB_MEMBER 테이블에서 관련 멤버 데이터가 성공적으로 삭제되었습니다!");
	        } else {
	            System.out.println("CLUB_MEMBER 테이블에 관련 데이터가 없습니다.");
	        }

	        // 3. CLUB 테이블에서 클럽 삭제
	        String deleteClubQuery = "DELETE FROM CLUB WHERE ClubID = ?";
	        PreparedStatement deleteClubStmt = connection.prepareStatement(deleteClubQuery);
	        deleteClubStmt.setInt(1, club.getClubId());
	        int clubRowsDeleted = deleteClubStmt.executeUpdate();
	        if (clubRowsDeleted > 0) {
	            System.out.println("클럽이 성공적으로 삭제되었습니다!");
	        } else {
	            System.out.println("삭제할 클럽을 찾을 수 없습니다.");
	        }

	    } catch (Exception e) {
	        System.err.println("클럽 삭제 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void viewClubMember() {
        Scanner scanner = new Scanner(System.in);
        try (Connection connection = DatabaseConnection.getConnection()) {
	        // SQL 실행
	        String query = "SELECT ClubID, ClubName, UniversityName FROM CLUB";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        System.out.println("Clubs List:");
	        while (resultSet.next()) {
	            // Club 객체 생성 및 데이터 설정
	            Club club = new Club();
	            club.setClubId(resultSet.getInt("ClubID"));
	            club.setClubName(resultSet.getString("ClubName"));

	            // Club 객체 데이터 출력
	            System.out.printf("Club ID: %d, Name: %s%n", club.getClubId(), club.getClubName());
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while fetching clubs: " + e.getMessage());
	        e.printStackTrace();
	    }
        // 사용자로부터 ClubID 입력 받기
        System.out.println("조회할 동아리 ID를 입력하세요: ");
        int clubId = scanner.nextInt();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 쿼리: CLUB_MEMBER와 STUDENT 테이블 조인하여 멤버 정보 조회
            String query = "SELECT cm.StudentID, s.Name, s.Age, s.Sex, s.PhoneNumber " +
                           "FROM CLUB_MEMBER cm " +
                           "JOIN STUDENT s ON cm.StudentID = s.StudentID " +
                           "WHERE cm.ClubID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);

            ResultSet resultSet = preparedStatement.executeQuery();

            // 결과 출력
            System.out.println("Club Members List:");
            System.out.println("=========================================");
            boolean hasResults = false; // 결과가 있는지 확인하기 위한 플래그

            while (resultSet.next()) {
                hasResults = true;
                int studentId = resultSet.getInt("StudentID");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String sex = resultSet.getString("Sex");
                String phoneNumber = resultSet.getString("PhoneNumber");

                System.out.printf("Student ID: %d, Name: %s, Age: %d, Sex: %s, Phone: %s%n",
                        studentId, name, age, sex, phoneNumber);
            }

            if (!hasResults) {
                System.out.println("해당 동아리에 멤버가 없습니다.");
            }

        } catch (Exception e) {
            System.err.println("동아리 멤버 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    
    
}
