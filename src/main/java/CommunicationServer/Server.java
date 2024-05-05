package CommunicationServer;

import java.net.ServerSocket;
import java.net.Socket;

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
