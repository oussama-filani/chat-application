package net.oussama.chatapplication.client;


import java.io.*;
        import java.net.*;

public class ClientHandler {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler() {}

    public void connectToServer(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() throws IOException {
        return in.readLine();
    }

    public void closeConnection() throws IOException {
        socket.close();
    }
}

