package model;

public class VotingCenter {
    private int ID;
    private String city;
    private int unitCenterNo;

    public VotingCenter(int id, String city, int unitCenterNo) {
        this.city = city;
        this.ID = id;
        this.unitCenterNo = unitCenterNo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUnitCenterNo() {
        return unitCenterNo;
    }

    public void setUnitCenterNo(int unitCenterNo) {
        this.unitCenterNo = unitCenterNo;
    }
}
