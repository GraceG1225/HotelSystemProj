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
    
    //method designed to allow pictures for the background.
    private String getBackgroundStyle() {
        return "-fx-background-image: url('file:src/authenticatorapp/picture.jpg'); -fx-background-size: cover; -fx-background-repeat: no-repeat; -fx-background-position: center;";
    }

    // Hides or shows password by cllicking button
private void HideOrShowPass(PasswordField hidePass, TextField showPass, Button toggleBtn) {
    if (hidePass.isVisible()) {
        showPass.setText(hidePass.getText());
        hidePass.setVisible(false);
        hidePass.setManaged(false);
        showPass.setVisible(true);
        showPass.setManaged(true);
        toggleBtn.setText("Hide");
    } else {
        if (!hidePass.isVisible()) {  
            hidePass.setText(showPass.getText());
            showPass.setVisible(false);
            showPass.setManaged(false);
            hidePass.setVisible(true);
            hidePass.setManaged(true);
            toggleBtn.setText("Show");
        }
    }
}

    //Build and returns whole login  screen such as title and buttons and textfield
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField passwordVisible = new TextField();
        passwordVisible.setPromptText("Password");
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        // Button to show or hide password
        Button togglePasswordButton = new Button("Show");

        togglePasswordButton.setOnAction(e -> HideOrShowPass(passwordField, passwordVisible, togglePasswordButton));

        // Message that tells invalid login
        Label message = new Label();

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0033cc; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.isVisible() ? passwordField.getText().trim() : passwordVisible.getText().trim();

            // Use Validator to check email and password are validf or not
            if (!Validator.isValidEmail(email)) {
                message.setText("Invalid email");
            } else if (!Validator.isValidPassword(password)) {
                message.setText("Invalid password");
            } else if (app.authenticate(email, password)) {
                message.setText("");
                app.showMainAppScene();
            } else {
                message.setText("Password or Email Invalid");
            }

            message.setStyle("-fx-text-fill: red;");
        });

        Button goToRegister = new Button("Create Account");
        goToRegister.setOnAction(e -> app.showRegisterScene());

        Button adminLoginButton = new Button("Admin Login");
        adminLoginButton.setOnAction(e -> app.showAdminLoginScene());
        //Method that allows to control buttons lables  textfields and many more
        content.getChildren().addAll(title,emailField,passwordField,passwordVisible,togglePasswordButton,loginButton,goToRegister,adminLoginButton,message);
        //Closes program wihtout stop running by pressing the X
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
