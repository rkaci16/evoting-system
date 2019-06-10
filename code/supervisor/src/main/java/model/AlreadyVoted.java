package model;

public class AlreadyVoted {
    private int hasVoted = 0;

    public AlreadyVoted(int hasVoted) {
        this.hasVoted = hasVoted;
    }

    public int getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(int hasVoted) {
        this.hasVoted = hasVoted;
    }
}

