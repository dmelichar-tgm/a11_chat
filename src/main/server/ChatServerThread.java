package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Logger;

/**
 * ChatServerThread.java
 * This is the thread that is being created for every User (the naming may have been done badly).
 * <p>
 * Author: Daniel Melichar
 * Date: 12/05/16
 * Version: 1.0
 */
class ChatServerThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger( ChatServerThread.class.getName() );

    // the socket where to listen/talk
    Socket socket;
    ObjectInputStream sInput;
    ObjectOutputStream sOutput;
    // my unique id (easier for deconnection)
    int id;
    // the Username of the Client
    String username;
    // the only type of message a will receive
    ChatMessage cm;
    // the date I connect
    String date;
    // where the data is
    ChatServer cs;

    // Constructore
    ChatServerThread(ChatServer cs, Socket socket) {
        // a unique id
        id = ++cs.uniqueId;
        this.socket = socket;
        this.cs = cs;

        /* Creating both Data Stream */
        LOGGER.info("Thread trying to create Object Input/Output Streams");
        try {
            // create output first
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sInput  = new ObjectInputStream(socket.getInputStream());
            // read the username
            username = (String) sInput.readObject();
            cs.display(username + " just connected.");
        }
        catch (IOException e) {
            cs.display("Exception creating new Input/output Streams: " + e);
            return;
        }
        // have to catch ClassNotFoundException
        // but I read a String, I am sure it will work
        catch (ClassNotFoundException e) {
        }
        date = new Date().toString() + "\n";
    }

    // what will run forever
    public void run() {
        // to loop until LOGOUT
        boolean keepGoing = true;
        while(keepGoing) {
            // read a String (which is an object)
            try {
                cm = (ChatMessage) sInput.readObject();
            }
            catch (IOException e) {
                cs.display(username + " Exception reading Streams: " + e);
                break;
            }
            catch(ClassNotFoundException e2) {
                break;
            }
            // the messaage part of the ChatMessage
            String message = cm.getMessage();

            // Switch on the type of message receive
            switch(cm.getType()) {

                case ChatMessage.MESSAGE:
                    cs.broadcast(username + ": " + message);
                    break;
                case ChatMessage.LOGOUT:
                    cs.display(username + " disconnected with a LOGOUT message.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("List of the users connected at " + cs.sdf.format(new Date()) + "\n");
                    // scan al the users connected
                    for(int i = 0; i < cs.al.size(); ++i) {
                        ChatServerThread ct = cs.al.get(i);
                        writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
                    }
                    break;
            }
        }
        // remove myself from the arrayList containing the list of the
        // connected Clients
        cs.remove(id);
        close();
    }

    // try to close everything
    private void close() {
        // try to close the connection
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {}
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {};
        try {
            if(socket != null) socket.close();
        }
        catch (Exception e) {}
    }

    /*
     * Write a String to the Client output stream
     */
    protected boolean writeMsg(String msg) {
        // if Client is still connected send the message to it
        if(!socket.isConnected()) {
            close();
            return false;
        }
        // write the message to the stream
        try {
            sOutput.writeObject(msg);
        }
        // if an error occurs, do not abort just inform the user
        catch(IOException e) {
            cs.display("Error sending message to " + username);
            cs.display(e.toString());
        }
        return true;
    }
}
