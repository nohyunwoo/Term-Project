package entity;

public class EnrollmentRequest {
    private int requestId; // PK
    private String status; // 대기, 승인, 거절
    private String requestDate;
    private int studentId; // FK
    private Integer clubId; // FK (nullable)
    private Integer studyGroupId; // FK (nullable)

    // 기본 생성자
    public EnrollmentRequest() {}

    // 생성자
    public EnrollmentRequest(int requestId, String status, String requestDate, int studentId, Integer clubId, Integer studyGroupId) {
        this.requestId = requestId;
        this.status = status;
        this.requestDate = requestDate;
        this.studentId = studentId;
        this.clubId = clubId;
        this.studyGroupId = studyGroupId;
    }

    // Getter and Setter
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(Integer studyGroupId) {
        this.studyGroupId = studyGroupId;
    }
}
