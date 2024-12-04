package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import service.AdminService;


public class ProcessEnrollmentRequest {
	public static void processEnrollmentRequest() {
	    Scanner scanner = new Scanner(System.in);

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // 1. `pending` 상태의 요청 조회
	        String fetchQuery = "SELECT RequestID, StudentID, ClubID, StudyGroupID, Status FROM ENROLLMENT_REQUEST WHERE Status = 'pending'";
	        PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
	        ResultSet resultSet = fetchStmt.executeQuery();

	        System.out.println("Pending Enrollment Requests:");
	        while (resultSet.next()) {
	            int requestId = resultSet.getInt("RequestID");
	            int studentId = resultSet.getInt("StudentID");
	            Integer clubId = resultSet.getInt("ClubID");
	            Integer studyGroupId = resultSet.getInt("StudyGroupID");
	            String status = resultSet.getString("Status");

	            System.out.printf("Request ID: %d, Student ID: %d, Club ID: %s, Study Group ID: %s, Status: %s%n",
	                    requestId, studentId,
	                    clubId == 0 ? "N/A" : clubId,
	                    studyGroupId == 0 ? "N/A" : studyGroupId,
	                    status);
	        }

	        // 2. 요청 ID 선택
	        System.out.println("처리할 요청의 ID를 입력하세요: ");
	        int selectedRequestId = scanner.nextInt();

	        System.out.println("처리 상태를 선택하세요 (accept/reject): ");
	        String action = scanner.next();

	        if (action.equalsIgnoreCase("accept")) {
	            // 요청 승인 처리
	            handleAcceptRequest(connection, selectedRequestId);
	        } else if (action.equalsIgnoreCase("reject")) {
	            // 요청 거절 처리
	            rejectRequest(connection, selectedRequestId);
	        } else {
	            System.out.println("잘못된 입력입니다. 다시 시도하세요.");
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while processing enrollment request: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	private static void handleAcceptRequest(Connection connection, int requestId) throws Exception {
	    // 승인된 요청 정보 가져오기
	    String fetchRequestQuery = "SELECT StudentID, ClubID, StudyGroupID FROM ENROLLMENT_REQUEST WHERE RequestID = ?";
	    PreparedStatement fetchStmt = connection.prepareStatement(fetchRequestQuery);
	    fetchStmt.setInt(1, requestId);

	    ResultSet resultSet = fetchStmt.executeQuery();
	    if (resultSet.next()) {
	        Integer clubId = resultSet.getInt("ClubID");
	        Integer studyGroupId = resultSet.getInt("StudyGroupID");

	        if (clubId != 0) {
	            // 동아리 승인 -> addClub() 호출
	            AdminService.addClub();  // 기존 메서드 호출
	        } else if (studyGroupId != 0) {
	            // 스터디 승인 -> 스터디 추가 메서드 호출
	            addStudyGroup();  // 추가 메서드 작성 필요
	        }

	        // 요청 상태를 'accept'로 업데이트
	        String updateStatusQuery = "UPDATE ENROLLMENT_REQUEST SET Status = 'accept' WHERE RequestID = ?";
	        PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
	        updateStmt.setInt(1, requestId);
	        updateStmt.executeUpdate();

	        System.out.println("요청이 승인되었습니다.");
	    } else {
	        System.out.println("요청 ID를 찾을 수 없습니다.");
	    }
	}
	
	private static void rejectRequest(Connection connection, int requestId) throws Exception {
        // 요청 상태를 'reject'로 업데이트
        String updateStatusQuery = "UPDATE ENROLLMENT_REQUEST SET Status = 'reject' WHERE RequestID = ?";
        PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
        updateStmt.setInt(1, requestId);

        int rowsUpdated = updateStmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("status reject 수정 완료.");
        } else {
            System.out.println("요청 ID를 찾을 수 없습니다.");
        }
    }

}
