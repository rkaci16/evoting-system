package model;

public class VotingStatus {
    private int hasStarted = 0;

    public VotingStatus(int hasStarted) {
        this.hasStarted = hasStarted;
    }

    public int getHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(int hasStarted) {
        this.hasStarted = hasStarted;
    }
}
