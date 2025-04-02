package net.oussama.chatapplication.client;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientView {
    private TextArea chatArea;
    private TextField messageField;
    private TextField nicknameField;
    private Button sendButton;
    private Button setNicknameButton;
    private VBox root;

    public ClientView() {
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        messageField = new TextField();
        nicknameField = new TextField();
        setNicknameButton = new Button("Définir le surnom");
        sendButton = new Button("Envoyer");

        root = new VBox(10);
        root.getChildren().addAll(nicknameField, setNicknameButton, chatArea, messageField, sendButton);

        // Appliquer le CSS
        root.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
    }

    public Scene getScene() {
        return new Scene(root, 400, 400);
    }

    public void appendMessage(String username, String message) {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        chatArea.appendText("[" + timeStamp + "] " + username + ": " + message + "\n");
    }

    public String getMessage() {
        return messageField.getText();
    }

    public String getNickname() {
        return nicknameField.getText();
    }

    public void clearMessageField() {
        messageField.clear();
    }

    public void setOnSendButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        sendButton.setOnAction(handler);
    }

    public void setOnSetNicknameButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        setNicknameButton.setOnAction(handler);
    }
}
