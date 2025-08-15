
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adminpage;

import authenticatorapp.SQLite;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import authenticatorapp.AuthenticatorApp;

public class AdminLoginPage {

    private AuthenticatorApp app;

    public AdminLoginPage(AuthenticatorApp app) {
        this.app = app;
    }
    
    //This method was designed to allow pictures for the background. Note: edited for integration
    private String getBackgroundStyle() {
        String imageUrl = getClass().getResource("/authenticatorapp/picture.jpg").toExternalForm();
        return "-fx-background-image: url('" + imageUrl + "'); " +
               "-fx-background-size: cover; -fx-background-repeat: no-repeat; -fx-background-position: center;";
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
        if (!hidePass.isVisible()) {  // nested if (redundant but as requested)
            hidePass.setText(showPass.getText());
            showPass.setVisible(false);
            showPass.setManaged(false);
            hidePass.setVisible(true);
            hidePass.setManaged(true);
            toggleBtn.setText("Show");
        }
    }
}

    // Builds the JavaFX layout buttons, textfields, etc.
    public Pane getUI() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);

        Label title = new Label("Admin Login");

        TextField idField = new TextField();
        idField.setPromptText("Admin ID");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField passwordVisible = new TextField();
        passwordVisible.setPromptText("Password");
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false);

        // Shows or hides the password
        Button togglePasswordButton = new Button("Show");
        togglePasswordButton.setOnAction(e -> HideOrShowPass(passwordField, passwordVisible, togglePasswordButton));

        Label message = new Label();

        Button loginButton = new Button("Login as Admin");
        loginButton.setStyle("-fx-background-color: #cc0000; -fx-text-fill: white;");
        
       
        loginButton.setOnAction(e -> {
            String id = idField.getText().trim();
            String password = "";

            if (passwordField.isVisible()) {
                password = passwordField.getText().trim();
            } else {
                password = passwordVisible.getText().trim();
            }

            if (id.equals("")) {
                message.setText("Admin ID cannot be empty.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (password.length() == 0) {
                message.setText("Enter your Password.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (SQLite.checkAdmin(id, password)) {
                message.setText("");
                app.showAdminManagementControl();
            } else {
                message.setText("Invalid admin credentials.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        Button backToLogin = new Button("Back to User Login");
        backToLogin.setOnAction(e -> app.showLoginScene());

        content.getChildren().addAll(title, idField, passwordField, passwordVisible, togglePasswordButton, loginButton, backToLogin, message);
        
        // Simple way to exit without closing program
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