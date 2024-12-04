package entity;

public class University {
    private String universityName; // PK

    // 기본 생성자
    public University() {}

    // 생성자
    public University(String universityName) {
        this.universityName = universityName;
    }

    // Getter and Setter
    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
