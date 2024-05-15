package com.mycompany.chatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient extends Thread {

    public int id;
    public Socket socket;
    private Server server;
    private DataInputStream sInput;
    private DataOutputStream sOutput;

    public boolean isListening = false;

    public void setUserId(int userId) {
        this.id = userId;
    }

    public SClient(Socket socket, Server server) {

        try {
            this.server = server;
            this.socket = socket;
            this.sInput = new DataInputStream(this.socket.getInputStream());
            this.sOutput = new DataOutputStream(this.socket.getOutputStream());
            //this.id = server.clientId;
            

        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Listen() {
        this.isListening = true;
        this.start();

    }

    @Override
    public void run() {
        try {
            while (this.isListening) {

                String msg = sInput.readUTF();
                this.server.SendBroadCastMessage(msg);
            }

        } catch (IOException ex) {
            this.Disconnect();
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void SendMessage(String message) throws IOException {
        sOutput.writeUTF(message);
        sOutput.flush();
    }

    //clientı kapatan fonksiyon
    public void Disconnect() {
        try {
            this.isListening = false;
            this.socket.close();
            this.sInput.close();
            this.sOutput.close();
            this.server.DisconnectClient(this); // Correct method name
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
