package authenticatorapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import adminpage.AdminLoginPage;
import adminpage.AdminManagementControl;

public class AuthenticatorApp extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args); // Start JavaFX
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dream Hotel");

        // make sure database tables exist
        SQLite.createUsersTable();
        SQLite.createAdminTable();  // makes admins table

        // initial login scene
        showLoginScene();
        primaryStage.show();
    }

    // getter for primaryStage (for loginpage)
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // Shows the registration screen
    public void showRegisterScene() {
        RegisterPage registerPage = new RegisterPage(this);
        Scene scene = new Scene(registerPage.getUI(), 400, 400);
        primaryStage.setScene(scene);
    }

    // Show the login screen
    public void showLoginScene() {
        LoginPage loginPage = new LoginPage(this);
        Scene scene = new Scene(loginPage.getUI(), 400, 300);
        primaryStage.setScene(scene);
    }

    // Authentication by using database
    public boolean authenticate(String email, String password) {
        return SQLite.checkUser(email, password);
    }

    // Registers new user in DB
    public boolean registerUser(String email, String password) {
        return SQLite.insertUser(email, password);
    }

    // Show admin login window
    public void showAdminLoginWindow() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(this);
        Scene scene = new Scene(adminLoginPage.getUI(), 400, 300);
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Login");
        adminStage.setScene(scene);
        adminStage.show();
    }

    // Show admin management page
    public void showAdminManagementControl() {
        // pass to AdminManagementControl
        AdminManagementControl adminPage = new AdminManagementControl(this);
        Scene scene = new Scene(adminPage.getUI(), 500, 400);
        primaryStage.setScene(scene);
    }

    // Show reservation screen
    public void showMainAppScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reservation Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}