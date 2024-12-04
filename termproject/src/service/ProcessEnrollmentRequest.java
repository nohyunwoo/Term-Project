package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import entity.EnrollmentRequest;
import entity.Student;
import entity.Club;


public class ProcessEnrollmentRequest {

    public static void processEnrollmentRequest() {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // 1. `pending` 상태의 요청 조회
            String fetchQuery = "SELECT RequestID, StudentID, ClubID, Status FROM ENROLLMENT_REQUEST WHERE Status = 'pending'";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = fetchStmt.executeQuery();

            System.out.println("Pending Enrollment Requests:");
            while (resultSet.next()) {
                // 요청 데이터 출력
                int requestId = resultSet.getInt("RequestID");
                int studentId = resultSet.getInt("StudentID");
                int clubId = resultSet.getInt("ClubID");
                String status = resultSet.getString("Status");

                System.out.printf("Request ID: %d, Student ID: %d, Club ID: %d, Status: %s%n",
                        requestId, studentId, clubId, status);
            }

            // 2. 처리할 요청 ID 선택
            System.out.println("처리할 요청의 ID를 입력하세요: ");
            int selectedRequestId = scanner.nextInt();
            scanner.nextLine(); // 버퍼 정리

            // 3. 처리 상태 선택
            System.out.println("처리 상태를 입력하세요 (accept/reject): ");
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("accept")) {
                handleAcceptRequest(connection, selectedRequestId);
            } else if (action.equalsIgnoreCase("reject")) {
                handleRejectRequest(connection, selectedRequestId);
            } else {
                System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }

        } catch (Exception e) {
            System.err.println("Enrollment request processing error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 요청 승인 처리
    private static void handleAcceptRequest(Connection connection, int requestId) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 승인된 요청 정보 가져오기
        String fetchRequestQuery = "SELECT StudentID, ClubID FROM ENROLLMENT_REQUEST WHERE RequestID = ?";
        PreparedStatement fetchStmt = connection.prepareStatement(fetchRequestQuery);
        fetchStmt.setInt(1, requestId);
        ResultSet resultSet = fetchStmt.executeQuery();

        if (resultSet.next()) {
            int studentId = resultSet.getInt("StudentID");
            int clubId = resultSet.getInt("ClubID");

            // 동아리 이름 입력
            System.out.println("추가할 동아리 이름을 입력하세요: ");
            String clubName = scanner.nextLine();

            // Club 객체 생성 및 데이터 설정
            Club club = new Club();
            club.setClubId(clubId);
            club.setClubName(clubName);

            // Club 테이블에 데이터 삽입
            String insertClubQuery = "INSERT INTO CLUB (ClubName) VALUES (?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertClubQuery);
            insertStmt.setString(1, club.getClubName());

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("동아리가 성공적으로 추가되었습니다!");

                // 요청 상태를 'accept'로 업데이트
                String updateStatusQuery = "UPDATE ENROLLMENT_REQUEST SET Status = 'accept' WHERE RequestID = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
                updateStmt.setInt(1, requestId);
                updateStmt.executeUpdate();
            } else {
                System.out.println("동아리 추가에 실패했습니다.");
            }
        } else {
            System.out.println("요청 ID를 찾을 수 없습니다.");
        }
    }

    // 요청 거절 처리
    private static void handleRejectRequest(Connection connection, int requestId) throws Exception {
        // 요청 상태를 'reject'로 업데이트
        String updateStatusQuery = "UPDATE ENROLLMENT_REQUEST SET Status = 'reject' WHERE RequestID = ?";
        PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
        updateStmt.setInt(1, requestId);

        int rowsUpdated = updateStmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("요청이 거절되었습니다.");
        } else {
            System.out.println("요청 ID를 찾을 수 없습니다.");
        }
    }
}
