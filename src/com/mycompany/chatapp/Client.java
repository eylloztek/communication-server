package com.mycompany.chatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class Client implements Runnable {

    private Socket socket;
    private DataInputStream sInput;
    private DataOutputStream sOutput;

    private String server;
    private int port;
    boolean isListening = false;

    public Client(String server, int port) {

        this.server = server;
        this.port = port;
    }

    public boolean ConnectToServer() {

        try {
            socket = new Socket(this.server, this.port);

            sInput = new DataInputStream(socket.getInputStream());
            sOutput = new DataOutputStream(socket.getOutputStream());
            this.Listen();
            System.out.println("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        } catch (Exception err) {
            System.out.println("Error connecting to server: " + err);
        }
        return true;
    }

    public void Listen() {
        if (isListening) {
            return;
        }
        this.isListening = true;
        Thread t1 = new Thread(this);
        t1.start();

    }

    @Override
    public void run() {
        try {
            while (this.isListening) { 
                String msg = sInput.readUTF();
                addMessage(msg);
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addMessage(String message) {
        StyledDocument doc = GroupChatFrame.tpane_message.getStyledDocument();
        Style style = GroupChatFrame.tpane_message.addStyle("Style", null);
        try {
            doc.insertString(doc.getLength(), message + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void SendMessage(String message) throws IOException {
        sOutput.writeUTF(message);
        sOutput.flush();
    }

    //clientı kapatan fonksiyon
    public void disconnect() {
        try {
            //tüm nesneleri kapatıyoruz
            if (sInput != null) {
                sInput.close();
            }
            if (sOutput != null) {
                sOutput.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

}
