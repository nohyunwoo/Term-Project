package entity;

public class StudyGroupMember {

    private int studyGroupId; // FK
    private int studentId; // FK

    // 기본 생성자
    public StudyGroupMember() {}

    // 매개변수를 포함한 생성자
    public StudyGroupMember(int studyGroupId, int studentId) {
        this.studyGroupId = studyGroupId;
        this.studentId = studentId;
    }

    // Getter and Setter
    public int getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(int studyGroupId) {
        this.studyGroupId = studyGroupId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

}
