package main.client;

import java.io.IOException;

/**
 * ChatClientThread.java
 * A class that waits for the message from the server and append them to the JTextArea
 * if we have a GUI or simply System.out.println() it in console mode
 * <p>
 * Author: Daniel Melichar
 * Date: 12/05/16
 * Version: ${VERSION}
 */

class ChatClientThread extends Thread {

    private ChatClient cc;

    ChatClientThread(ChatClient cc) {
        this.cc = cc;
    }

    public void run() {
        while(true) {
            try {
                cc.printMessage();
            }
            catch(IOException e) {
                cc.handleMessage(e);
            }
            // can't happen with a String object but need the catch anyhow
            catch(ClassNotFoundException e2) {
            }
        }
    }
}
