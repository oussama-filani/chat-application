package net.oussama.chatapplication.client;

import javafx.application.Application;
import javafx.stage.*;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClientHandler handler = new ClientHandler();
        ClientView view = new ClientView();
        ClientController controller = new ClientController(handler, view);

        primaryStage.setTitle("Application de Chat");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
