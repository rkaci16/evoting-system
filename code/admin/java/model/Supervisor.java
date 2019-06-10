package model;

public class Supervisor {
    private int id;
    private String fullName;
    private int votingCenter;

    public Supervisor(int id, String fullName, int votingCenter) {
        this.id = id;
        this.fullName = fullName;
        this.votingCenter = votingCenter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Supervisor(int votingCenter) {
        this.votingCenter = votingCenter;
    }

    public int getVotingCenter() {
        return votingCenter;
    }

    public void setVotingCenter(int votingCenter) {
        this.votingCenter = votingCenter;
    }

    //TODO
    public boolean submitInfo(){
        return true;
    }
}
