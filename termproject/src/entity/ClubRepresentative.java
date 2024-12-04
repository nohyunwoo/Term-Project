package entity;

public class ClubRepresentative {
    private int clubId; // PK, FK
    private int studentId; // FK

    // 기본 생성자
    public ClubRepresentative() {}

    // 생성자
    public ClubRepresentative(int clubId, int studentId) {
        this.clubId = clubId;
        this.studentId = studentId;
    }

    // Getter and Setter
    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
