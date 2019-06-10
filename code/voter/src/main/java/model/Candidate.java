package model;

public class Candidate {
    private String name;
    private int ID;
    private String leader;

    public Candidate(int id, String name, String leader) {
        this.ID = id;
        this.name = name;
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}
