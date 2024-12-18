package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;
import entity.Club;
import entity.StudyGroup;

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
	            System.out.println("동아리 등록 완료!");
	        } else {
	            System.out.println("동아리 등록 실패");
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while adding club: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    
	public static void deleteClub() {
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
	
	public static void viewStudyGroups() {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // SQL 실행: STUDY_GROUP 테이블에서 데이터 조회
	        String query = "SELECT StudyGroupID, StudyGroupName, ClubID FROM STUDY_GROUP";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        System.out.println("Study Groups List:");
	        System.out.println("=========================================");
	        while (resultSet.next()) {
	            // StudyGroup 객체 생성 및 데이터 설정
	            StudyGroup studyGroup = new StudyGroup();
	            studyGroup.setStudyGroupId(resultSet.getInt("StudyGroupID"));
	            studyGroup.setStudyGroupName(resultSet.getString("StudyGroupName"));
	            studyGroup.setClubId(resultSet.getInt("ClubID"));

	            // StudyGroup 객체 데이터 출력
	            System.out.printf("Study Group ID: %d, Name: %s, Club ID: %d%n",
	                    studyGroup.getStudyGroupId(),
	                    studyGroup.getStudyGroupName(),
	                    studyGroup.getClubId());
	        }

	    } catch (Exception e) {
	        System.err.println("Error occurred while fetching study groups: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void addStudyGroup() {
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
        
        // 사용자로부터 ClubID와 StudyGroupName 입력받기
        System.out.println("스터디 그룹을 추가할 ClubID를 입력하세요: ");
        int clubId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 정리

        System.out.println("추가할 스터디 그룹의 이름을 입력하세요: ");
        String studyGroupName = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            // ClubID가 존재하는지 확인
            String checkClubQuery = "SELECT COUNT(*) FROM CLUB WHERE ClubID = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkClubQuery);
            checkStmt.setInt(1, clubId);
            ResultSet resultSet = checkStmt.executeQuery();

            boolean clubExists = false;
            if (resultSet.next()) {
                clubExists = resultSet.getInt(1) > 0;
            }

            if (!clubExists) {
                System.out.println("해당 ClubID는 존재하지 않습니다.");
                return;
            }

            // 스터디 그룹 추가
            String insertQuery = "INSERT INTO STUDY_GROUP (StudyGroupName, ClubID) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, studyGroupName);
            insertStmt.setInt(2, clubId);

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("스터디 그룹 추가 완료");
            } else {
                System.out.println("스터디 그룹 추가 실패.");
            }

        } catch (Exception e) {
            System.err.println("스터디 그룹 추가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
	public static void viewStudyGroupMembersByClub() {
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
	    
	    // 사용자로부터 ClubID 입력받기
	    System.out.println("조회할 동아리의 ClubID를 입력하세요: ");
	    int clubId = scanner.nextInt();

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        
	        String query = "SELECT sgm.StudyGroupID, sg.StudyGroupName, s.StudentID, s.Name AS StudentName, c.ClubName " +
	                       "FROM STUDY_GROUP_MEMBER sgm " +
	                       "JOIN STUDY_GROUP sg ON sgm.StudyGroupID = sg.StudyGroupID " +
	                       "JOIN STUDENT s ON sgm.StudentID = s.StudentID " +
	                       "JOIN CLUB c ON sg.ClubID = c.ClubID " +
	                       "WHERE c.ClubID = ?";

	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, clubId);

	        ResultSet resultSet = preparedStatement.executeQuery();

	     
	        System.out.println("Study Group Members for Club ID: " + clubId);
	        System.out.println("===============================================");
	        boolean hasResults = false;

	        while (resultSet.next()) {
	            hasResults = true;
	            int studyGroupId = resultSet.getInt("StudyGroupID");
	            String studyGroupName = resultSet.getString("StudyGroupName");
	            int studentId = resultSet.getInt("StudentID");
	            String studentName = resultSet.getString("StudentName");
	            String clubName = resultSet.getString("ClubName");

	            System.out.printf("Club: %s | Study Group: %s (ID: %d) | Student: %s (ID: %d)%n",
	                    clubName, studyGroupName, studyGroupId, studentName, studentId);
	        }

	        if (!hasResults) {
	            System.out.println("해당 동아리에 등록된 스터디 그룹 멤버가 없습니다.");
	        }

	    } catch (Exception e) {
	        System.err.println("스터디 그룹 멤버 조회 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void viewAllStudents() {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        
	        String query = "SELECT StudentID, Name, Age, Sex, PhoneNumber, UniversityName FROM STUDENT";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        System.out.println("전체 학생 정보:");
	        System.out.println("========================================");
	        System.out.printf("%-10s %-20s %-5s %-5s %-15s %-20s%n", "StudentID", "Name", "Age", "Sex", "PhoneNumber", "UniversityName");
	        System.out.println("========================================");

	        while (resultSet.next()) {
	            int studentId = resultSet.getInt("StudentID");
	            String name = resultSet.getString("Name");
	            int age = resultSet.getInt("Age");
	            String sex = resultSet.getString("Sex");
	            String phoneNumber = resultSet.getString("PhoneNumber");
	            String universityName = resultSet.getString("UniversityName");

	            System.out.printf("%-10d %-20s %-5d %-5s %-15s %-20s%n", studentId, name, age, sex, phoneNumber, universityName);
	        }
	    } catch (Exception e) {
	        System.err.println("학생 정보 조회 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static void viewEnrollmentRequestAdminRecords() {
	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // SQL 쿼리 실행
	        String query = "SELECT RequestID, AdminID, ProcessDate FROM ENROLLMENT_REQUEST_ADMIN";
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        System.out.println("요청 처리 기록:");
	        System.out.println("*********************************************************");
	        System.out.printf("%-10s %-10s %-20s%n", "RequestID", "AdminID", "ProcessDate");
	        System.out.println("*********************************************************");

	        while (resultSet.next()) {
	            int requestId = resultSet.getInt("RequestID");
	            int adminId = resultSet.getInt("AdminID");
	            String processDate = resultSet.getString("ProcessDate");

	            System.out.printf("%-10d %-10d %-20s%n", requestId, adminId, processDate);
	        }
	    } catch (Exception e) {
	        System.err.println("요청 처리 기록 조회 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void deleteStudyGroup() {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("삭제할 스터디 그룹 ID를 입력하세요: ");
	    int studyGroupId = scanner.nextInt();

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        // 먼저 STUDY_GROUP_MEMBER에서 관련 데이터 삭제
	        String deleteMembersQuery = "DELETE FROM STUDY_GROUP_MEMBER WHERE StudyGroupID = ?";
	        PreparedStatement deleteMembersStmt = connection.prepareStatement(deleteMembersQuery);
	        deleteMembersStmt.setInt(1, studyGroupId);

	        int memberRowsDeleted = deleteMembersStmt.executeUpdate();
	        if (memberRowsDeleted > 0) {
	            System.out.println("스터디 그룹의 멤버 데이터가 삭제되었습니다.");
	        } else {
	            System.out.println("스터디 그룹의 멤버 데이터가 없습니다.");
	        }

	        // STUDY_GROUP 테이블에서 스터디 그룹 삭제
	        String deleteGroupQuery = "DELETE FROM STUDY_GROUP WHERE StudyGroupID = ?";
	        PreparedStatement deleteGroupStmt = connection.prepareStatement(deleteGroupQuery);
	        deleteGroupStmt.setInt(1, studyGroupId);

	        int groupRowsDeleted = deleteGroupStmt.executeUpdate();
	        if (groupRowsDeleted > 0) {
	            System.out.println("스터디 그룹이 성공적으로 삭제되었습니다!");
	        } else {
	            System.out.println("삭제할 스터디 그룹을 찾을 수 없습니다.");
	        }

	    } catch (Exception e) {
	        System.err.println("스터디 그룹 삭제 중 오류 발생: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    
    
}
