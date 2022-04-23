package ui.gui;

import domain.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.IAppServices;

import java.util.Objects;


public class LoginController{

    private IAppServices server;
    private ProgrammerController appCtrl;
    private User crtUser;
    @FXML
    TextField user;
    @FXML
    TextField password;

    Parent mainChatParent;

    public void setServer(IAppServices s){
        server=s;
    }

    public void setParent(Parent p){
        mainChatParent=p;
    }

    public void pressLogin(ActionEvent actionEvent) {
        //Parent root;
        Integer id = Integer.parseInt(user.getText());
        String passwd = password.getText();
        crtUser = new User();
        crtUser.setID(id);
        crtUser.setPassword(passwd);

        try{
            User us2 = server.findUserbyId(crtUser.getID());

            if(Objects.equals(us2.getPassword(), crtUser.getPassword()))
            {
                crtUser.setUsername(us2.getUsername());
                crtUser.setType(us2.getType());
            }
            else
            {
                throw new Exception("Date incorecte!");
            }

            if(Objects.equals(crtUser.getType(), "programmer"))
            {
                Stage stage=new Stage();
                stage.setTitle("Chat Window for " +crtUser.getID());
                stage.setScene(new Scene(mainChatParent));

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.exit(0);
                    }
                });

                stage.show();
                appCtrl.setUser(crtUser);
                appCtrl.setServer(this.server);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

        }   catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ISS chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            //alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setUser(User user) {
        this.crtUser = user;
    }

    public void setAppController(ProgrammerController appController) {
        this.appCtrl = appController;
    }

}

