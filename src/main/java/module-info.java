module net.oussama.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;

    exports net.oussama.chatapplication.client;  // Permet l'accès à la classe ClientApp
    opens net.oussama.chatapplication.client to javafx.graphics; // Ouvre le package aux modules JavaFX
}
