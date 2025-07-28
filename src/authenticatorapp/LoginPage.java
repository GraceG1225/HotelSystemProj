/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import reservation.MainController;

public class LoginPage {

    private AuthenticatorApp app;

    public LoginPage(AuthenticatorApp app) {
        this.app = app;
    }

    // Build and return login screen UI
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10;");

        Label title = new Label("Login");
        title.setFont(new Font("Arial", 24));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label message = new Label();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0033cc; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                message.setText("Please enter email and password.");
                message.setStyle("-fx-text-fill: red;");
            } else if (app.authenticate(email, password)) {
                message.setText("");
                try {
                    // loads reservation page's mainview.fxml (please)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/MainView.fxml"));
                    Parent reservationRoot = loader.load();

                    MainController controller = loader.getController();
                    // controller.setUserEmail(email); // might use later to allow users to log in and edit reservations

                    Scene scene = new Scene(reservationRoot);
                    app.getPrimaryStage().setScene(scene);
                    app.getPrimaryStage().setTitle("Reservation Page");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    message.setText("Failed to load reservation page.");
                    message.setStyle("-fx-text-fill: red;");
                }
            } else {
                message.setText("Invalid email or password.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        Button goToRegister = new Button("Create Account");
        goToRegister.setOnAction(e -> app.showRegisterScene());

        content.getChildren().addAll(title, emailField, passwordField, loginButton, goToRegister, message);

        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> System.exit(0));
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        HBox closeBar = new HBox(closeButton);
        closeBar.setAlignment(Pos.TOP_LEFT);
        closeBar.setPadding(new Insets(10));

        VBox layout = new VBox(closeBar, content);
        layout.setAlignment(Pos.CENTER);

        return new StackPane(layout);
    }
}
