package net.oussama.chatapplication.client;

import java.io.IOException;

public class ClientController {
    private ClientHandler handler;
    private ClientView view;

    public ClientController(ClientHandler handler, ClientView view) {
        this.handler = handler;
        this.view = view;

        // Gérer l'action de définir le Nom d'utilisateur
        view.setOnSetNicknameButtonAction(e -> setNickname());

        // Gérer l'action d'envoyer un message
        view.setOnSendButtonAction(e -> sendMessage());
    }

    private void setNickname() {
        String nickname = view.getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            try {
                handler.connectToServer("localhost", 12345);
                handler.sendMessage(nickname); // Envoi du Nom d'utilisateur
                listenForMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            view.appendMessage("","Le Nom d'utilisateur est requis !");
        }
    }

    private void sendMessage() {
        String message = view.getMessage();
        String username = view.getNickname(); // Récupère le nom d'utilisateur
        if (!message.isEmpty() && username != null && !username.isEmpty()) {
            handler.sendMessage(message);
            view.appendMessage(username, message); // Affiche le message avec le nom d'utilisateur et l'horodatage
            view.clearMessageField();
        }
    }

    private void listenForMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = handler.receiveMessage()) != null) {
                    // Lorsque le message est reçu, ajoute l'horodatage et affiche
                    view.appendMessage("Server", message); // "Server" est utilisé ici comme exemple de nom d'utilisateur
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
