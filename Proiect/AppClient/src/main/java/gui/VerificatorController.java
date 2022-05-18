package gui;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.AppException;
import services.IObserver;
import services.IServices;


public class VerificatorController implements IObserver {
    private IServices server;
    private User crtUser;
    @FXML
    TextField name;
    @FXML
    TextField description;


    public VerificatorController() {}

    public void setUser(User crtUser) {
        this.crtUser = crtUser;
    }

    public void setServer(IServices server) {
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

    public void logout() {
        try {
            this.server.logout(crtUser, this);
        } catch (AppException e) {
            System.out.println("Logout error " + e);
        }
    }

    @Override
    public void bugsModified() throws AppException {

    }

    @Override
    public void usersModified() throws AppException {

    }
}
