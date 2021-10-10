package network;

import controller.ClientHandler;
import resources.Ports;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager {

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(Ports.MAIN_PORT);
            while (true){
                Socket socket = serverSocket.accept();
                new ClientHandler(new SocketResponseSender(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}