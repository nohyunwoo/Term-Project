package entity;

public class ClubAdmin {
    private int clubId; // PK, FK
    private int adminId; // FK

    // 기본 생성자
    public ClubAdmin() {}

    // 생성자
    public ClubAdmin(int clubId, int adminId) {
        this.clubId = clubId;
        this.adminId = adminId;
    }

    // Getter and Setter
    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}

