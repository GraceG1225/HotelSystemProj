/**
* Class Name: Main
* Date: July 14, 2025
* Programmer: Maliya Cockrell
*
* This is the entry point of the Hotel Reservation System application.
* It loads the main user interface defined in MainView.fxml and initializes the primary stage for the JavaFX application
*/

package reservation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * Initializes and displays the primary stage using the main booking form layout.
     *
     * @param stage the primary JavaFX window
     * @throws Exception if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/reservation/MainView.fxml"));
        stage.setTitle("Hotel Reservation System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
