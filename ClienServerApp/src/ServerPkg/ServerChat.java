/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPkg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ServerChat {
    ServerSocket myServerSocket;
    public ServerChat(){
        try {
            myServerSocket = new ServerSocket(5005);
            while(true){
                Socket s = myServerSocket.accept();
                new ChatHandler(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
       new ServerChat();
    }
    
}
