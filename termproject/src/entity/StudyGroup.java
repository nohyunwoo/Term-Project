package entity;

public class StudyGroup {
    private int studyGroupId; // PK
    private String studyGroupName;
    private int clubId; // FK

    // 기본 생성자
    public StudyGroup() {}

    // 생성자
    public StudyGroup(int studyGroupId, String studyGroupName, int clubId) {
        this.studyGroupId = studyGroupId;
        this.studyGroupName = studyGroupName;
        this.clubId = clubId;
    }

    // Getter and Setter
    public int getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(int studyGroupId) {
        this.studyGroupId = studyGroupId;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public void setStudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }
}
