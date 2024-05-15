package com.mycompany.chatapp;

public class ServerManager {

    private static Server serverInstance = new Server();

    public static Server getServerInstance() {
        if (serverInstance == null) {
            serverInstance = new Server();
        }
        return serverInstance;
    }

    public static void startServer(int port) {

        serverInstance.Create(port);
        serverInstance.Listen();
    }

    public static void stopServer() {
        if (serverInstance != null) {
            serverInstance.Stop();
            serverInstance = null;
            System.out.println("Server stopped.");
        } else {
            System.out.println("Server is not running.");
        }
    }

}
