package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;

import entity.Club;
import entity.EnrollmentRequest;
import entity.Student;
import entity.StudyGroup;

public class StudentService {
    // 동아리 가입 신청
    public static void createForClub() {
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
        
        System.out.println("학생이 소속된 동아리 ID를 입력하세요: ");
        int clubId = scanner.nextInt();

        System.out.println("학생 ID를 입력하세요: ");
        int studentId = scanner.nextInt();
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            // ClubID에 속한 스터디 그룹 정보 가져오기
            String query = "SELECT StudyGroupID, StudyGroupName, ClubID FROM STUDY_GROUP WHERE ClubID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clubId);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Study Groups List for Club ID: " + clubId);
            System.out.println("=====================================");

            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;

                // StudyGroup 객체 생성 및 데이터 설정
                StudyGroup studyGroup = new StudyGroup();
                studyGroup.setStudyGroupId(resultSet.getInt("StudyGroupID"));
                studyGroup.setStudyGroupName(resultSet.getString("StudyGroupName"));
                studyGroup.setClubId(resultSet.getInt("ClubID"));

                // StudyGroup 객체 데이터 출력
                System.out.printf("Study Group ID: %d, Name: %s%n", 
                                  studyGroup.getStudyGroupId(), 
                                  studyGroup.getStudyGroupName());
            }

            if (!hasResults) {
                System.out.println("해당 클럽에 등록된 스터디 그룹이 없습니다.");
            }

        } catch (Exception e) {
            System.err.println("스터디 그룹 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        
        System.out.println("가입하고 싶은 스터디ID를 입력하세요: ");
        int studentGroupId = scanner.nextInt();

        // EnrollmentRequest 객체 생성 및 값 설정
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(studentId);
        request.setClubId(clubId); // 동아리 ID 설정
        request.setStudyGroupId(studentGroupId); // 스터디 ID 설정
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
            preparedStatement.setInt(5, request.getStudyGroupId()); // StudyGroupID는 입력받은 값

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
        
        public static void applyForClub() {
        	try (Connection connection = DatabaseConnection.getConnection()) {
    
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
        	
            Scanner scanner = new Scanner(System.in);

            System.out.println("가입하려는 동아리 ID를 입력하세요: ");
            int clubId = scanner.nextInt();

            System.out.println("학생 학번을 입력하세요: ");
            int studentId = scanner.nextInt();

            // EnrollmentRequest 객체 생성 및 값 설정
            EnrollmentRequest request = new EnrollmentRequest();
            request.setStudentId(studentId);
            request.setClubId(clubId); // 동아리 ID 설정
            request.setStudyGroupId(null); // 스터디 가입 아님
            request.setStatus("pending"); // 기본 상태 설정
            request.setRequestDate(java.time.LocalDate.now().toString()); // 현재 날짜 설정

            try (Connection connection = DatabaseConnection.getConnection()) {
               
                String query = "INSERT INTO ENROLLMENT_REQUEST (Status, RequestDate, StudentID, ClubID, StudyGroupID) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, request.getStatus());
                preparedStatement.setString(2, request.getRequestDate());
                preparedStatement.setInt(3, request.getStudentId());
                preparedStatement.setInt(4, request.getClubId()); // ClubID는 입력받은 값
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
        
        public static void viewEnrollmentStatus() {
            Scanner scanner = new Scanner(System.in);

            // 사용자로부터 StudentID 입력 받기
            System.out.println("조회할 학생 학번을 입력하세요: ");
            int studentId = scanner.nextInt();

            try (Connection connection = DatabaseConnection.getConnection()) {
                // SQL 쿼리: ENROLLMENT_REQUEST 테이블에서 해당 학생의 요청 상태 조회
                String query = "SELECT RequestID, ClubID, StudyGroupID, Status, RequestDate " +
                               "FROM ENROLLMENT_REQUEST " +
                               "WHERE StudentID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, studentId);

                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("Enrollment Requests for Student ID: " + studentId);
                System.out.println("=================================================");
                boolean hasResults = false; // 결과가 있는지 확인

                while (resultSet.next()) {
                    hasResults = true;
                    int requestId = resultSet.getInt("RequestID");
                    Integer clubId = resultSet.getInt("ClubID");
                    if (resultSet.wasNull()) clubId = null; // NULL 처리
                    Integer studyGroupId = resultSet.getInt("StudyGroupID");
                    if (resultSet.wasNull()) studyGroupId = null; // NULL 처리
                    String status = resultSet.getString("Status");
                    String requestDate = resultSet.getString("RequestDate");

                    // 결과 출력
                    System.out.printf("Request ID: %d, Club ID: %s, Study Group ID: %s, Status: %s, Date: %s%n",
                            requestId,
                            clubId == null ? "N/A" : clubId.toString(),
                            studyGroupId == null ? "N/A" : studyGroupId.toString(),
                            status,
                            requestDate);
                }

                if (!hasResults) {
                    System.out.println("해당 학생의 가입 신청 상태가 없습니다.");
                }

            } catch (Exception e) {
                System.err.println("가입 신청 상태 조회 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        
}


