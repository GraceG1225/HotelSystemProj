/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;  // or your package name

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BackgroundScene {

    public Scene createScene() {
     
        Pane content = new Pane();
        StackPane root = new StackPane(content);

        // Load the image 
        String imageUrl = getClass().getResource("/picturesAuthenticatorApp/picturehotel1.jpg").toExternalForm();

        // Set background image style on the root pane
        root.setStyle("-fx-background-image: url('" + imageUrl + "');" + "-fx-background-size: cover;" + "-fx-background-position: center center;");

        // Create and return the Scene and  set width and height as needed will change later
        Scene scene = new Scene(root, 800, 600);  
        return scene;
    }
}
