package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;


public class AdminService {

    public static void viewClubs() {
        // 데이터베이스 연결 생성
        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 실행
            String query = "SELECT ClubID, ClubName, UniversityName FROM CLUB";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // 결과 출력
            System.out.println("Clubs List:");
            while (resultSet.next()) {
                int clubId = resultSet.getInt("ClubID");
                String clubName = resultSet.getString("ClubName");
                String universityName = resultSet.getString("UniversityName");

                System.out.printf("Club ID: %d, Name: %s, University: %s%n", clubId, clubName, universityName);
            }

        } catch (Exception e) {
            System.err.println("Error occurred while fetching clubs: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void addClub() {
        Scanner scanner = new Scanner(System.in);

        // 사용자 입력 받기
        System.out.println("동아리 이름 입력: ");
        String clubName = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 실행
            String query = "INSERT INTO CLUB (ClubName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // 입력값 설정
            preparedStatement.setString(1, clubName);


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

        try (Connection connection = DatabaseConnection.getConnection()) {
            // SQL 실행
            String query = "DELETE FROM CLUB WHERE ClubID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // 입력값 설정
            preparedStatement.setInt(1, clubId);

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
