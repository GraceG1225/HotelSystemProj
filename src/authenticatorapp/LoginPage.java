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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import adminpage.AdminAuthenticatorApp;
import authenticatorapp.AuthenticatorApp;
import reservation.MainController;

public class LoginPage {

    private AuthenticatorApp app;

    public LoginPage(AuthenticatorApp app) {
        this.app = app;
    }

    // Method for background image styling
    private String getBackgroundStyle() {
        String imageUrl = getClass().getResource("/authenticatorapp/picture.jpg").toExternalForm();
        return "-fx-background-image: url('" + imageUrl + "'); " +
        "-fx-background-size: cover; -fx-background-repeat: no-repeat; " +
        "-fx-background-position: center;";
    }

    // Show/hide password toggle
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

    // Build UI
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

        TextField passwordVisible = new TextField();
        passwordVisible.setPromptText("Password");
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        Button togglePasswordButton = new Button("Show");
        togglePasswordButton.setOnAction(e -> HideOrShowPass(passwordField, passwordVisible, togglePasswordButton));

        Label message = new Label();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0033cc; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.isVisible() ? passwordField.getText().trim() : passwordVisible.getText().trim();

            // Use Validator class to check validity
            if (!Validator.isValidEmail(email)) {
                message.setText("Invalid email");
            } else if (!Validator.isValidPassword(password)) {
                message.setText("Invalid password");
            } else if (app.authenticate(email, password)) {
                message.setText("");

                try {
                    // FXML reservation logic from original
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/MainView.fxml"));
                    Parent reservationRoot = loader.load();

                    MainController controller = loader.getController();
                    // controller.setUserEmail(email); // optional, if needed

                    Scene scene = new Scene(reservationRoot);
                    app.getPrimaryStage().setScene(scene);
                    app.getPrimaryStage().setTitle("Reservation Page");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    message.setText("Failed to load reservation page.");
                    message.setStyle("-fx-text-fill: red;");
                }

            } else {
                message.setText("Password or Email Invalid");
            }

            message.setStyle("-fx-text-fill: red;");
        });

        Button goToRegister = new Button("Create Account");
        goToRegister.setOnAction(e -> app.showRegisterScene());

        Button adminLoginButton = new Button("Admin Login");
        adminLoginButton.setOnAction(e -> {
            app.showAdminLoginWindow(); // calls method added in AuthenticatorApp
            });


        content.getChildren().addAll(title, emailField, passwordField, passwordVisible,
                togglePasswordButton, loginButton, goToRegister, adminLoginButton, message);

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