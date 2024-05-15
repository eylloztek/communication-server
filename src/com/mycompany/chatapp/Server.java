package com.mycompany.chatapp;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.util.*;

public class Server extends Thread {

    private ServerSocket serverSocket;
    public boolean isListening = false;
    public int clientId = 0;

    private int port;

    private ArrayList<SClient> clientList;

    public Server() {
        clientList = new ArrayList<>();
    }

    public boolean Create(int port) {
        try {
            this.port = port;
            serverSocket = new ServerSocket(this.port);
        } catch (Exception err) {
            System.out.println("Error connecting to server: " + err);
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            while (this.isListening) {
                System.out.println("Server waiting client...");
                Socket clientSocket = this.serverSocket.accept();
                SClient nsclient = new SClient(clientSocket, this);
                nsclient.Listen();
                clientList.add(nsclient);
                UpdateActiveUsersList();
                this.clientId++;

            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void UpdateActiveUsersList() {
        GroupChatFrame.activeUsersListModel.clear();
        for (SClient sClient : clientList) {
            String cinfo = sClient.socket.getInetAddress().toString() + ":" + sClient.socket.getPort();
            GroupChatFrame.activeUsersListModel.addElement(cinfo);
        }
        
    }

    public void DisconnectClient(SClient client) {
        this.clientList.remove(client);
        GroupChatFrame.activeUsersListModel.removeAllElements();
        for (SClient sClient : clientList) {
            String cinfo = sClient.socket.getInetAddress().toString() + ":" + sClient.socket.getPort();
            GroupChatFrame.activeUsersListModel.addElement(cinfo);
        }
    }

    public void Listen() {
        this.isListening = true;
        this.start();
    }

    public void Stop() {
        try {
            this.isListening = false;
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendMessage(String message, int clientId) throws IOException {
        for (SClient sClient : this.clientList) {
            if (clientId == sClient.id) {
                sClient.SendMessage(message);
                break;
            }
        }
    }

    public void SendBroadCastMessage(String message) throws IOException {
        for (SClient sClient : this.clientList) {
            sClient.SendMessage(message);
        }
    }

}

