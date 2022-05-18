package gui;

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
import services.IServices;


import java.util.Objects;


public class LoginController{

    private IServices server;
    private VerificatorController verificatorController;
    private ProgramatorController programatorController;
    private AdministratorController administratorController;
    private User crtUser;
    @FXML
    TextField user;
    @FXML
    TextField password;
    @FXML
    TextField type;

    Parent VerificatorParent;
    Parent ProgramatorPrent;
    Parent AdministratorParent;

    public void setProgramatorController(ProgramatorController programatorController)
    {
        this.programatorController = programatorController;
    }

    public void setVerificatorController(VerificatorController verificatorController) {
        this.verificatorController = verificatorController;
    }

    public void setAdministratorController(AdministratorController administratorController)
    {
        this.administratorController = administratorController;
    }

    public void setServer(IServices s){
        server=s;
    }

    public void setVerificatorParent(Parent p){
        VerificatorParent =p;
    }
    public void setProgramatorPrent(Parent p ){ProgramatorPrent=p;}
    public void setAdministratorParent(Parent p){AdministratorParent=p;}

    public void pressLogin(ActionEvent actionEvent) {
        //Parent root;
        Integer id = Integer.parseInt(user.getText());
        String passwd = password.getText();
        String typev = type.getText();
        crtUser = new User();
        crtUser.setID(id);
        crtUser.setPassword(passwd);
        crtUser.setType(typev);


            if(Objects.equals(crtUser.getType(), "verificator"))
            {
                try{
                    server.login(crtUser, verificatorController);
                    Stage stage=new Stage();
                    stage.setTitle("Chat Window for " +crtUser.getID());
                    stage.setScene(new Scene(VerificatorParent));

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            verificatorController.logout();
                            System.exit(0);
                        }
                    });

                    stage.show();
                    verificatorController.setUser(crtUser);
                    verificatorController.setServer(this.server);
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ISS chat");
                alert.setHeaderText("Authentication failure");
                alert.setContentText("Wrong data!");
                //alert.setContentText(e.getMessage());
                alert.showAndWait();
                }
            }
            else if(Objects.equals(crtUser.getType(), "programator"))
            {
                try{
                    server.login(crtUser, programatorController);
                    Stage stage=new Stage();
                    stage.setTitle("Chat Window for " +crtUser.getID());
                    stage.setScene(new Scene(ProgramatorPrent));

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            programatorController.logout();
                            System.exit(0);
                        }
                    });

                    stage.show();
                    programatorController.setUser(crtUser);
                    programatorController.setServer(this.server);
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ISS chat");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("Wrong username or password");
                    //alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
            else if(Objects.equals(crtUser.getType(), "administrator"))
            {
                try{
                    server.login(crtUser, administratorController);
                    Stage stage=new Stage();
                    stage.setTitle("Chat Window for " +crtUser.getID());
                    stage.setScene(new Scene(AdministratorParent));

                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            administratorController.logout();
                            System.exit(0);
                        }
                    });

                    stage.show();
                    administratorController.setUser(crtUser);
                    administratorController.setServer(this.server);
                    ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ISS chat");
                    alert.setHeaderText("Authentication failure");
                    alert.setContentText("Wrong username or password");
                    //alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }

    }

    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setUser(User user) {
        this.crtUser = user;
    }



}

