package model;

import java.util.List;

public class Admin {
    private int ID;
    private String fullName;
    private String username;
    private String password;

    public Admin(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
