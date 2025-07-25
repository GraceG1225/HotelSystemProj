/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginPage {

    private AuthenticatorApp app;

    public LoginPage(AuthenticatorApp app) {
        this.app = app;
    }
//Build and returens whole sign up screen such as title and textfield
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10;");

        Label title = new Label("Login");

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
                app.showMainAppScene();
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