package net.oussama.chatapplication.server;


import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        try {
            // Créer une instance du modèle du serveur
            ServerHandler serverHandler = new ServerHandler();

            // Démarrer l'acceptation des connexions
            serverHandler.acceptConnections();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
