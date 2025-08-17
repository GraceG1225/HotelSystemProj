package authenticatorapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import adminpage.AdminLoginPage;
import adminpage.AdminManagementControl;

public class AuthenticatorApp extends Application {

    private Stage primaryStage;
    private String loggedInUserEmail; // store user email

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dream Hotel");

        SQLite.createUsersTable();
        SQLite.createAdminTable();

        showLoginScene();
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setLoggedInUserEmail(String email) {
        this.loggedInUserEmail = email;
    }

    public String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    public void showRegisterScene() {
        RegisterPage registerPage = new RegisterPage(this);
        Scene scene = new Scene(registerPage.getUI(), 400, 400);
        primaryStage.setScene(scene);
    }

    public void showLoginScene() {
        LoginPage loginPage = new LoginPage(this);
        Scene scene = new Scene(loginPage.getUI(), 400, 300);
        primaryStage.setScene(scene);
    }

    public boolean authenticate(String email, String password) {
        return SQLite.checkUser(email, password);
    }

    public boolean registerUser(String email, String password) {
        return SQLite.insertUser(email, password);
    }

    public void showAdminLoginWindow() {
        AdminLoginPage adminLoginPage = new AdminLoginPage(this);
        Scene scene = new Scene(adminLoginPage.getUI(), 400, 300);
        Stage adminStage = new Stage();
        adminStage.setTitle("Admin Login");
        adminStage.setScene(scene);
        adminStage.show();
    }

    public void showAdminManagementControl() {
        AdminManagementControl adminPage = new AdminManagementControl(this);
        Scene scene = new Scene(adminPage.getUI(), 500, 400);
        primaryStage.setScene(scene);
    }

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
