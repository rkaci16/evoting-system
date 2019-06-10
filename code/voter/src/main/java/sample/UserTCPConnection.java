package sample;

import db.UserDB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.*;
import java.io.*;

public class UserTCPConnection {

    private static Socket socket;
    private DataInputStream dataInput = null;
    private DataOutputStream dataOutput = null;
    private UserDB userDB = new UserDB();

    public void sendMessageThroughTCP() {

        try {
            String host = "localhost";
            int port = 5555;

            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);

            //Send the message to the server
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            //The message to be sent
            String msgToSend = "The citizien is ready to vote";

            bufferedWriter.write(msgToSend);
            bufferedWriter.flush();
            System.out.println("Message sent to the server: ");

        } catch (UnknownHostException unknown) {
            System.out.println(unknown);

        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String getMessageThroughTCP() {

        String myString = null;

        try {
            //Get the return message from the server
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //The returned message
            String returnedMsg = bufferedReader.readLine();
            System.out.println("Message received from the server : " + returnedMsg);

            myString = returnedMsg;

        } catch (UnknownHostException unknown) {
            System.out.println(unknown);

        } catch (IOException i) {
            System.out.println(i);
        }
        return myString;
    }

}
