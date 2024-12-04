package entity;

public class Administrator {
    private int adminId; // PK
    private String adminName;

    // 기본 생성자
    public Administrator() {}

    // 생성자
    public Administrator(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
    }

    // Getter and Setter
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
