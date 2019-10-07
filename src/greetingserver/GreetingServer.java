/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package greetingserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author Abdelhalim
 */
public class GreetingServer extends Thread{

    private ServerSocket serverSocket;
    
    public GreetingServer(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(6000);
    }
    
    public void run(){
        while(true){
            try{
                System.out.println("Waiting for client on port "+
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just Connected to "+
                        server.getRemoteSocketAddress());
                
                DataInputStream dataIn = new DataInputStream(server.getInputStream());
                System.out.println(dataIn.readUTF());
                DataOutputStream dataOut = new DataOutputStream(server.getOutputStream());
                dataOut.writeUTF("Thank you for connecting to "+
                        server.getLocalSocketAddress()+"\n GoodBye");
                server.close();
            }catch(SocketTimeoutException socketTimeOutExp){
                socketTimeOutExp.printStackTrace();
                break;
            }catch(IOException ioExp){
                ioExp.printStackTrace();
                break;
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int port = 6066;
        try{
            Thread thread = new GreetingServer(port);
            thread.start();
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
    }
}
