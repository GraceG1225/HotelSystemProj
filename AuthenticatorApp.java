 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package authenticatorapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuthenticatorApp extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args); // Starts the JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dream Hotel");

        // Create Database users table if not exists
        SQLite.createUsersTable();
        SQLite.createAdminTable();
        showLoginScene();
        primaryStage.show();
     
}
    // Shows the registration screen
    public void showRegisterScene() {
        RegisterPage registerPage = new RegisterPage(this);
        Scene scene = new Scene(registerPage.getUI(), 400, 400);
        primaryStage.setScene(scene);
    }

    // Showsthe login screen
    public void showLoginScene() {
        LoginPage loginPage = new LoginPage(this);
        Scene scene = new Scene(loginPage.getUI(), 400, 300);
        primaryStage.setScene(scene);
    }

    // Authentication by using database
    public boolean authenticate(String email, String password) {
        return SQLite.checkUser(email, password);
    }

    // Registers new user in Database
    public boolean registerUser(String email, String password) {
        return SQLite.insertUser(email, password);
    }

    // Show main page after login/registering
    public void showMainAppScene() {
        MainAppPage mainAppPage = new MainAppPage();
        Scene scene = new Scene(mainAppPage.getUI(), 500, 400);
        primaryStage.setScene(scene);
    }
    //Shows admin login no register for security reasons
    public void showAdminLoginScene() {
    AdminLoginPage adminLoginPage = new AdminLoginPage(this);
    Scene scene = new Scene(adminLoginPage.getUI(), 400, 300);
    primaryStage.setScene(scene);
}
//Admin controls page
public void showAdminManagementControl() {
    AdminManagementControl adminPage = new AdminManagementControl();
    Scene scene = new Scene(adminPage.getUI(), 500, 400);
    primaryStage.setScene(scene);
}
}
