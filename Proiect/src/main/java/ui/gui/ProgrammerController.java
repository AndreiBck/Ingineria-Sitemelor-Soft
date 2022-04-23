package ui.gui;

import domain.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.IAppServices;

import java.util.Objects;

public class ProgrammerController {
    private IAppServices server;
    private User crtUser;
    @FXML
    TextField name;
    @FXML
    TextField description;


    public ProgrammerController() {}

    public void setUser(User crtUser) {
        this.crtUser = crtUser;
    }

    public void setServer(IAppServices server) {
        this.server = server;
    }

    public void pressAddBug(ActionEvent actionEvent) {
        //Parent root;
        String namev = name.getText();
        String descriptionv = description.getText();
        try{
                this.server.addBug(namev, descriptionv);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add");
                alert.setContentText("Bug added successfully");
                alert.showAndWait();
            }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error");
            //alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

}
