/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPkg;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class ChatHandler extends Thread {
    DataInputStream dis;
    PrintStream ps;
    static Vector<ChatHandler> clientVector = new Vector<ChatHandler>();
    
    @Override
    public void run(){
        while(true){
            try{
                String str = dis.readLine();
                sendMessageToAll(str);
            } catch (IOException ex) {
//                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendMessageToAll(String str) {
        for(ChatHandler ch : clientVector){
            ch.ps.println(str+" " + getId());
        }
    }
    
    public ChatHandler(Socket s){
        try {
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clientVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
