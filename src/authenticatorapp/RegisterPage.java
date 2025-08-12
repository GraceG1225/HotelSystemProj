/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    // Background image style
    private String getBackgroundStyle() {
        String imageUrl = getClass().getResource("/authenticatorapp/picture.jpg").toExternalForm();
        return "-fx-background-image: url('" + imageUrl + "'); " +
        "-fx-background-size: cover; " +
        "-fx-background-repeat: no-repeat; " +
        "-fx-background-position: center;";
    }

    // Toggle password visibility
    private void HideOrShowPass(PasswordField hidePass, TextField showPass, Button toggleBtn) {
        if (hidePass.isVisible()) {
            showPass.setText(hidePass.getText());
            hidePass.setVisible(false);
            hidePass.setManaged(false);
            showPass.setVisible(true);
            showPass.setManaged(true);
            toggleBtn.setText("Hide");
        } else {
            hidePass.setText(showPass.getText());
            showPass.setVisible(false);
            showPass.setManaged(false);
            hidePass.setVisible(true);
            hidePass.setManaged(true);
            toggleBtn.setText("Show");
        }
    }

    // Build and return whole registration UI
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Create Account");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField passwordVisible = new TextField();
        passwordVisible.setPromptText("Password");
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        Button togglePasswordButton = new Button("Show");
        togglePasswordButton.setOnAction(e -> HideOrShowPass(passwordField, passwordVisible, togglePasswordButton));

        Label message = new Label();

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.isVisible() ? passwordField.getText() : passwordVisible.getText();

            if (name.isEmpty()) {
                message.setText("Name cannot be empty.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            Validator validator = new Validator();

            if (!validator.isValidEmail(email)) {
                message.setText("Invalid email address. Please enter a valid Gmail address.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (!validator.isValidPassword(password)) {
                message.setText("Password must be at least 5 characters and include uppercase letters, digits, and symbols.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (app.registerUser(email, password)) {
                // Debug output (from original)
                System.out.println("Registered:");
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);

                // Alert popup (from original)
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Your account has been created. Please log in.");
                alert.showAndWait();

                message.setText("Account created successfully!");
                message.setStyle("-fx-text-fill: green;");

                // Redirect to login (from original, NOT main app)
                app.showLoginScene();
            } else {
                message.setText("Email already registered.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        Button goToLogin = new Button("Back to Login");
        goToLogin.setOnAction(e -> app.showLoginScene());

        content.getChildren().addAll(
                title, nameField, emailField, passwordField, passwordVisible,
                togglePasswordButton, registerButton, goToLogin, message
        );

        // Close button
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> System.exit(0));
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        HBox closeBar = new HBox(closeButton);
        closeBar.setAlignment(Pos.TOP_LEFT);
        closeBar.setPadding(new Insets(10));

        VBox layout = new VBox(closeBar, content);
        layout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(layout);
        root.setStyle(getBackgroundStyle());

        return root;
    }
}
