package entity;

public class Club {
    private int clubId;
    private String clubName;

    // 생성자
    public Club() {}
    public Club(int clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    // get(), set()
    public int getClubId() {
        return clubId;
    }
    // 굳이 필요없지만 혹시 모르니까
    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}

