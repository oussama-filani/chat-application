package net.oussama.chatapplication.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServerHandler {
    private static final int PORT = 12345;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private ServerSocket serverSocket;

    public ServerHandler() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    // Méthode pour accepter les connexions des clients
    public void acceptConnections() {
        System.out.println("Serveur démarré sur le port " + PORT);
        try {
            while (true) {
                // Attente de la connexion d'un nouveau client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté");

                // Créer un nouveau gestionnaire pour ce client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);

                // Démarrer un thread pour chaque client
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Diffuser un message à tous les clients connectés
    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    // Gérer la déconnexion d'un client
    public void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    // Gestion de chaque client connecté
    private class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String nickname;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Demander le Nom d'utilisateur du client
                out.println("Entrez votre Nom d'utilisateur :");
                nickname = in.readLine();
                System.out.println("Nom d'utilisateur : " + nickname);

                // Diffuser le message de bienvenue
                broadcastMessage(nickname + " a rejoint le chat.");

                // Écouter les messages du client
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    // Diffuser les messages aux autres clients
                    broadcastMessage(nickname + ": " + message);
                }

                // Fermeture de la connexion avec ce client
                removeClient(this);
                socket.close();
                broadcastMessage(nickname + " a quitté le chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Envoi de messages à ce client
        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
