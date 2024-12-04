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
	        String fetchQuery = "SELECT RequestID, StudentID, ClubID, StudyGroupID ,Status FROM ENROLLMENT_REQUEST WHERE Status = 'pending'";
	        PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
	        ResultSet resultSet = fetchStmt.executeQuery();

	        System.out.println("Pending Enrollment Requests:");
	        while (resultSet.next()) {
	            // 요청 데이터 출력
	            int requestId = resultSet.getInt("RequestID");
	            int studentId = resultSet.getInt("StudentID");
	            int clubId = resultSet.getInt("ClubID");
	            int studyGroupId = resultSet.getInt("StudyGroupID");
	            String status = resultSet.getString("Status");

	            System.out.printf("Request ID: %d, Student ID: %d, Club ID: %d, StudyGroupID: %d, Status: %s%n",
	                    requestId, studentId, clubId, studyGroupId, status);
	        }

	        // 2. 처리할 요청 ID 선택
	        System.out.println("처리할 요청의 ID를 입력하세요: ");
	        int selectedRequestId = scanner.nextInt();

	        // 3. 처리 상태 선택
	        System.out.println("처리 상태를 입력하세요 (accept/reject): ");
	        String action = scanner.next();

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

	private static void handleAcceptRequest(Connection connection, int requestId) throws Exception {
	    // 승인된 요청 정보 가져오기
	    String fetchRequestQuery = "SELECT StudentID, ClubID, StudyGroupID FROM ENROLLMENT_REQUEST WHERE RequestID = ?";
	    PreparedStatement fetchStmt = connection.prepareStatement(fetchRequestQuery);
	    fetchStmt.setInt(1, requestId);
	    ResultSet resultSet = fetchStmt.executeQuery();

	    if (resultSet.next()) {
	        int studentId = resultSet.getInt("StudentID");
	        int clubId = resultSet.getInt("ClubID");
	        int studyGroupId = resultSet.getInt("StudyGroupID");

	        if (studyGroupId != 0) {
	            // 스터디 그룹에 대한 요청 처리
	            handleStudyGroupRequest(connection, studentId, studyGroupId, requestId);
	        } else if (clubId != 0) {
	            // 동아리에 대한 요청 처리
	            handleClubRequest(connection, studentId, clubId, requestId);
	        } else {
	            System.out.println("요청에 유효한 ClubID 또는 StudyGroupID가 없습니다.");
	        }
	    } else {
	        System.out.println("요청 ID를 찾을 수 없습니다.");
	    }
	}

	// 동아리 요청 처리
	private static void handleClubRequest(Connection connection, int studentId, int clubId, int requestId) throws Exception {
	    // CLUB_MEMBER 테이블에 학생 추가
	    String insertMemberQuery = "INSERT INTO CLUB_MEMBER (ClubID, StudentID) VALUES (?, ?)";
	    PreparedStatement insertStmt = connection.prepareStatement(insertMemberQuery);
	    insertStmt.setInt(1, clubId);
	    insertStmt.setInt(2, studentId);

	    int rowsInserted = insertStmt.executeUpdate();
	    if (rowsInserted > 0) {
	        System.out.println("학생이 동아리에 성공적으로 추가되었습니다!");

	        // 요청 상태를 'accept'로 업데이트
	        updateRequestStatus(connection, requestId, "accept");
	    } else {
	        System.out.println("학생 추가에 실패했습니다.");
	    }
	}

	// 스터디 그룹 요청 처리
	private static void handleStudyGroupRequest(Connection connection, int studentId, int studyGroupId, int requestId) throws Exception {
	    // STUDY_GROUP_MEMBER 테이블에 학생 추가
	    String insertMemberQuery = "INSERT INTO STUDY_GROUP_MEMBER (StudyGroupID, StudentID) VALUES (?, ?)";
	    PreparedStatement insertStmt = connection.prepareStatement(insertMemberQuery);
	    insertStmt.setInt(1, studyGroupId);
	    insertStmt.setInt(2, studentId);

	    int rowsInserted = insertStmt.executeUpdate();
	    if (rowsInserted > 0) {
	        System.out.println("학생이 스터디 그룹에 성공적으로 추가되었습니다!");

	        // 요청 상태를 'accept'로 업데이트
	        updateRequestStatus(connection, requestId, "accept");
	    } else {
	        System.out.println("스터디 그룹에 학생 추가에 실패했습니다.");
	    }
	}

	// 요청 상태 업데이트
	private static void updateRequestStatus(Connection connection, int requestId, String status) throws Exception {
	    String updateStatusQuery = "UPDATE ENROLLMENT_REQUEST SET Status = ? WHERE RequestID = ?";
	    PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
	    updateStmt.setString(1, status);
	    updateStmt.setInt(2, requestId);
	    updateStmt.executeUpdate();
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
