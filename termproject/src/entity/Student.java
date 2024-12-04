package entity;

public class Student {
    private int studentId; // PK
    private String name;
    private int age;
    private String sex;
    private String phoneNumber;
    private String universityName; // FK

    // 기본 생성자
    public Student() {}

    // 생성자
    public Student(int studentId, String name, int age, String sex, String phoneNumber, String universityName) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.universityName = universityName;
    }

    // Getter and Setter
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}

