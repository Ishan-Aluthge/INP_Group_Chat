

import controller.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(4000);
        System.out.println("Server Started!!!");
        Socket socket;

        while (true) {
            socket = serverSocket.accept();
            System.out.println("New Client Connected!!!");

            ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
            clientHandlers.add(clientHandler);
            clientHandler.start();
        }
    }


}
