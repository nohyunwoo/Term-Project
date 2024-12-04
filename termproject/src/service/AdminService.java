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
	        // SQL 실행
	        String query = "DELETE FROM CLUB WHERE ClubID = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);

	        // Club 객체에서 값 가져오기
	        preparedStatement.setInt(1, club.getClubId());

	        int rowsDeleted = preparedStatement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("클럽이 성공적으로 삭제되었습니다!");
	        } else {
	            System.out.println("삭제할 클럽을 찾을 수 없습니다.");
	        }

	    } catch (Exception e) {
	        System.err.println("클럽 삭제 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    
    
    
}
