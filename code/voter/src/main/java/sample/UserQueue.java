package sample;

import java.util.LinkedList;
import java.util.Queue;

public class UserQueue {

    private Queue<String> queue = new LinkedList<String>();
    private String string = "User is voting";


    public void addUserToQueue() {

        queue.add(string);
    }

    public void removeUserFromQueue() {

        queue.remove(string);
    }

}
