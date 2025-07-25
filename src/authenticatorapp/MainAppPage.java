/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authenticatorapp;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainAppPage {

    // This is what the screen shows after successful login/registration. Will decorate it more soon
    public VBox getUI() {
        VBox layout = new VBox(10); 
        layout.setPadding(new Insets(20)); 

        // Button to close the app cause why not
        Button closeButton = new Button("X");
        //This closes the app
          closeButton.setOnAction(e -> System.exit(0)); 
        
     layout.getChildren().add(closeButton); 

        //Remove later if needed
        layout.getChildren().add(new Label("Welcome to Dream Hotel! Let's Get This Project Done!!!!!!..."));
        return layout;
    }
}