/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommunicationServer;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Eylül Öztek
 */
public class Server {
    private ServerSocket serverSocket;
    public Socket socket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void startServer(){
        
        try {
            while (!serverSocket.isClosed()) {                
                socket = serverSocket.accept();
                System.out.println("A new client has connected.");
            }
        } catch (Exception e) {
            
        }
    }
    
    
    public void closeSocket(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
