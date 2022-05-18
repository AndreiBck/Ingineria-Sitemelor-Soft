package gui;

import domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.AppException;
import services.IObserver;
import services.IServices;

import java.util.Collection;


public class AdministratorController implements IObserver {
    private IServices server;
    private User crtUser;
    private final ObservableList<EventListItem> modelListaUsers = FXCollections.observableArrayList();

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField eMail;
    @FXML
    TextField password;
    @FXML
    TextField type;

    @FXML
    private ListView<EventListItem> listaUsers;

    @FXML
    public void initialize() {
        listaUsers.setItems(modelListaUsers);
    }

    public AdministratorController(){}

    public void setUser(User crtUser) {
        this.crtUser = crtUser;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void pressShowAllBtn()
    {
        try {
            Collection<User> events = server.getAllUsers();
            modelListaUsers.clear();
            for(User user:events)
            {
                modelListaUsers.add(new EventListItem(user));
            }
        }catch (AppException e)
        {
            e.printStackTrace();
        }
    }

    public void pressAddButton(ActionEvent actionEvent) throws AppException
    {
        String firstNamev = firstName.getText();
        String lastNamev = lastName.getText();
        String eMailv = eMail.getText();
        String passwordv = password.getText();
        String typev = type.getText();

        this.server.addUser(firstNamev, lastNamev, eMailv, passwordv, typev);

    }

    public void pressUpdateButton(ActionEvent actionEvent) throws AppException
    {
        int index = listaUsers.getSelectionModel().getSelectedIndex();
        if (index<0) Util.showWarning("No user selected!","Please select a user from the list");
        else {
            String firstNamev = firstName.getText();
            String lastNamev = lastName.getText();
            String eMailv = eMail.getText();
            String passwordv = password.getText();
            String typev = type.getText();
            User user = listaUsers.getItems().get(index).event;
            this.server.updateUser(firstNamev, lastNamev, eMailv, passwordv, typev, user.getID());
        }
    }

    public void pressDeleteButton(ActionEvent actionEvent) throws AppException
    {
        int index = listaUsers.getSelectionModel().getSelectedIndex();
        if (index<0) Util.showWarning("No user selected!","Please select a user from the list");
        else {
            User user = listaUsers.getItems().get(index).event;
            this.server.deleteUser(user);
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
        Platform.runLater(() -> {
           pressShowAllBtn();
        });
    }

    private class EventListItem extends HBox {
        private User event;
        private AnchorPane leftPane;
        private Text eventFirstName, eventLastName, eventEMail, eventPassword, eventType, eventId;

        public EventListItem(User event){
            this.event = event;
            initializeLeftPane();
            this.getChildren().addAll(leftPane);
        }

        public User getEvent(){
            return event;
        }

        private void initializeLeftPane(){
            leftPane = new AnchorPane();
            leftPane.setPrefWidth(400.0);
            leftPane.setPrefHeight(120.0);
            eventFirstName = new Text(); eventLastName = new Text(); eventEMail = new Text();
            eventPassword = new Text(); eventType = new Text(); eventId = new Text();
            leftPane.getChildren().addAll(eventFirstName, eventLastName, eventEMail, eventPassword, eventType, eventId);
            AnchorPane.setLeftAnchor(eventLastName, 164.0);
            AnchorPane.setTopAnchor(eventLastName, 20.0);
            eventLastName.setText(event.getLastName());
            AnchorPane.setLeftAnchor(eventFirstName, 40.0);
            AnchorPane.setTopAnchor(eventFirstName, 40.0);
            eventFirstName.setText("First Name: " + event.getFirstName());
            AnchorPane.setLeftAnchor(eventEMail, 40.0);
            AnchorPane.setTopAnchor(eventEMail, 60.0);
            eventEMail.setText("e-mail: " + event.geteMail());
            AnchorPane.setLeftAnchor(eventPassword, 40.0);
            AnchorPane.setTopAnchor(eventPassword, 80.0);
            eventPassword.setText("Password: " + (event.getPassword()));
            AnchorPane.setLeftAnchor(eventType, 40.0);
            AnchorPane.setTopAnchor(eventType, 100.0);
            eventType.setText("Type: " + (event.getType()));
            AnchorPane.setLeftAnchor(eventId, 40.0);
            AnchorPane.setTopAnchor(eventId, 120.0);
            eventId.setText("Id: " + String.valueOf(event.getID()));
        }
    }
}
