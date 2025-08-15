package adminpage;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
import authenticatorapp.AuthenticatorApp;

public class AdminManagementControl {

    private Pane ui;
    private AuthenticatorApp app;

    public AdminManagementControl(AuthenticatorApp app) {
        this.app = app;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminpage/ManageReservations.fxml"));
            ui = loader.load();

            ManageReservationsController controller = loader.getController();
            controller.setApp(app);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pane getUI() {
        return ui;
    }
}
