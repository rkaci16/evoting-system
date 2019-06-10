package model;

public class Citizen {

    private int id;
    private String passportId;
    private String name;
    private String surname;
    private int hasVoted = 0;


    public Citizen(int id, int hasVoted) {

        this.id = id;
        this.hasVoted = hasVoted;
    }
    public int getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(int hasVoted) {
        this.hasVoted = hasVoted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
