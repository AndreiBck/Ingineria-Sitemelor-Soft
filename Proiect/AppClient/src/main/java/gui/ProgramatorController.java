package gui;

import domain.Bug;
import domain.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import services.AppException;
import services.IObserver;
import services.IServices;

import java.util.Collection;


public class ProgramatorController implements IObserver {
    private IServices server;
    private User crtUser;
    private final ObservableList<EventListItem> modelListaBugs = FXCollections.observableArrayList();

    @FXML
    private ListView<EventListItem> listaBugs;

    @FXML
    public void initialize() {
        listaBugs.setItems(modelListaBugs);
    }

    public ProgramatorController(){}

    public void setUser(User crtUser) {
        this.crtUser = crtUser;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void pressShowAllBtn()
    {
        try {
            Collection<Bug> events = server.getAllBugs();
            modelListaBugs.clear();
            for(Bug bug:events)
            {
                modelListaBugs.add(new EventListItem(bug));
            }
        }catch (AppException e)
        {
            e.printStackTrace();
        }
    }

    public void pressSolveBugButton(ActionEvent actionEvent) throws AppException
    {
        int index = listaBugs.getSelectionModel().getSelectedIndex();
        if (index<0) Util.showWarning("No bug selected!","Please select a bug from the list");
        else {
            Bug bug = listaBugs.getItems().get(index).event;
            this.server.deleteBug(bug);
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
        Platform.runLater(() -> {
            pressShowAllBtn();
        });
    }

    @Override
    public void usersModified() throws AppException {

    }

    private class EventListItem extends HBox {
        private Bug event;
        private AnchorPane leftPane;
        private Text eventName, eventDescription, eventId;

        public EventListItem(Bug event){
            this.event = event;
            initializeLeftPane();
            this.getChildren().addAll(leftPane);
        }

        public Bug getEvent(){
            return event;
        }

        private void initializeLeftPane(){
            leftPane = new AnchorPane();
            leftPane.setPrefWidth(400.0);
            leftPane.setPrefHeight(120.0);
            eventId = new Text(); eventName = new Text(); eventDescription = new Text();
            leftPane.getChildren().addAll(eventId, eventName, eventDescription);
            AnchorPane.setLeftAnchor(eventName, 164.0);
            AnchorPane.setTopAnchor(eventName, 20.0);
            eventName.setText("Nume: " + event.getName());
            AnchorPane.setLeftAnchor(eventDescription, 40.0);
            AnchorPane.setTopAnchor(eventDescription, 40.0);
            eventDescription.setText("Descriere: " + event.getDescription());
            AnchorPane.setLeftAnchor(eventId, 40.0);
            AnchorPane.setTopAnchor(eventId, 60.0);
            eventId.setText("Id: " + event.getID());
        }
    }

}
