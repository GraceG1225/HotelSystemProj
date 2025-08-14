/** Class Name: AuthenticatorApp
 * Date: July 17, 2025
 * Programmer: Fotios Bampouridis
 *
 * This is the main application class for the login and authentication of the Hotel reservation System.
 * It sets up and displays the login and registration screen, handles scene switching, and connects to the SQLite database to authenticate or register users.
 *
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package authenticatorapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AuthenticatorApp extends Application {

    private Stage primaryStage;

    /**
     * Main method that launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args); // Start JavaFX
    }

    /**
     * Starts the application, initializes the primary stage, and displays the login screen.
     * Also creates the users table in the database if it does not exist.
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Dream Hotel");

        // Create DB users table if not exists
        SQLite.createUsersTable();

        showLoginScene();
        primaryStage.show();
    }

    /**
     * Displays the registration screen by setting the scene to the RegisterPage UI.
     */
    public void showRegisterScene() {
        RegisterPage registerPage = new RegisterPage(this);
        Scene scene = new Scene(registerPage.getUI(), 400, 400);
        primaryStage.setScene(scene);
    }

    /**
     * Displays the login screen by setting the scene to the LoginPage UI.
     */
    public void showLoginScene() {
        LoginPage loginPage = new LoginPage(this);
        Scene scene = new Scene(loginPage.getUI(), 400, 300);
        primaryStage.setScene(scene);
    }

    /**
     * Authenticates the user by checking the credentials against the SQLite database.
     *
     * @param email the user's email
     * @param password the user's password
     * @return true if credentials are valid, false otherwise
     */
    public boolean authenticate(String email, String password) {
        return SQLite.checkUser(email, password);
    }

    /**
     * Registers a new user in the SQLite database.
     *
     * @param email the user's email
     * @param password the user's password
     * @return true if registration is successful, false if email already exists
     */
    public boolean registerUser(String email, String password) {
        return SQLite.insertUser(email, password);
    }

    /**
     * Navigates to the main reservation page (MainView.fxml) after successful login.
     */
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

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary Stage object
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
