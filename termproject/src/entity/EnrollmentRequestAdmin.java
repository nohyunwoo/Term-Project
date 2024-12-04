package entity;

public class EnrollmentRequestAdmin {
    private int requestId; // PK, FK
    private int adminId; // FK

    // 기본 생성자
    public EnrollmentRequestAdmin() {}

    // 생성자
    public EnrollmentRequestAdmin(int requestId, int adminId) {
        this.requestId = requestId;
        this.adminId = adminId;
    }

    // Getter and Setter
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}

