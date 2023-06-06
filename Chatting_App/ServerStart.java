package Chatting_App;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ebrar
 */
public class ServerStart {
    public static void main(String[] args) {
        try {
            int port = 5000;
            new Server(port).StartServer();
            System.out.println("Server Started...");
        } catch (IOException ex) {
            Logger.getLogger(Frm_Server.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}
