package ui;

import domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.DataBases.BugDbRepository;
import repository.DataBases.UserDbRepository;
import repository.Interfaces.IRepositoryBug;
import repository.Interfaces.IRepositoryUser;
import service.IAppServices;
import service.Service;
import ui.gui.LoginController;
import ui.gui.ProgrammerController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Properties props=new Properties();
        try {
            System.out.println((new File(".")).getAbsolutePath());
            props.load(new FileReader("bd.config"));
            System.out.println((new File(".")).getAbsolutePath());
            System.out.println("FOUND IT!!!!!!!!!!!!!!!");
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }


        IRepositoryBug repositoryBug = new BugDbRepository(props);
        IRepositoryUser repositoryUser = new UserDbRepository(props);

        IAppServices server = new Service(repositoryBug, repositoryUser);


        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginW.fxml"));
        Parent root=loader.load();


        LoginController ctrl = loader.<LoginController>getController();
        ctrl.setServer(server);

        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("ChatW.fxml"));
        Parent croot=cloader.load();

        ProgrammerController appCtrl = cloader.<ProgrammerController>getController();
        appCtrl.setServer(server);

        ctrl.setAppController(appCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("MPP chat");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    };


}
