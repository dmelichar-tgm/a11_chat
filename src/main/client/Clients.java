package main.client;

/**
 * Clients.java (Interface)
 * To start a ChatClientThread, a ChatClient needs to inherit from this Clients Interface
 * <p>
 * Author: Daniel Melichar
 * Date: 22/04/16
 * Version: 1.0
 */
public interface Clients {

    // These are the basic functions a Client requires.
    void stop();
    void handle(String msg);
}
