/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterPage {

    private AuthenticatorApp app;

    public RegisterPage(AuthenticatorApp app) {
        this.app = app;
    }
//Build and returens whole sign up screen such as title and textfield
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10;");

        Label title = new Label("Create Account");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label message = new Label();

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (name.isEmpty()) {
                message.setText("Name cannot be empty.");
                message.setStyle("-fx-text-fill: red;");
            } else if (!Validator.isValidEmail(email)) {
                message.setText("Invalid email.");
                message.setStyle("-fx-text-fill: red;");
            } else if (!Validator.isValidPassword(password)) {
                message.setText("Weak password. Must have uppercase, digit, and symbol.");
                message.setStyle("-fx-text-fill: red;");
            } else {
                //So we can see password email created tired of writting down
            if (app.registerUser(email, password)) {
                 System.out.println("Registered:");
                  System.out.println("Email: " + email);
                 System.out.println("Password: " + password);

                 message.setText("Account created successfully!");
                 message.setStyle("-fx-text-fill: green;");
                  app.showMainAppScene();


                } else {
                    message.setText("Email already registered.");
                    message.setStyle("-fx-text-fill: red;");
                }
            }
        });

        Button goToLogin = new Button("Back to Login");
        goToLogin.setOnAction(e -> app.showLoginScene());

        content.getChildren().addAll(title, nameField, emailField, passwordField, registerButton, goToLogin, message);

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