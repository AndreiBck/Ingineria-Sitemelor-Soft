import gui.AdministratorController;
import gui.LoginController;
import gui.ProgramatorController;
import gui.VerificatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objectprotocol.ObjectProxy;
import services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ObjectProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginWindow.fxml"));
        Parent root=loader.load();
        LoginController ctrl = loader.<LoginController>getController();
        ctrl.setServer(server);


        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("VerificatorWindow.fxml"));
        Parent croot=cloader.load();
        VerificatorController verificatorController = cloader.<VerificatorController>getController();
        verificatorController.setServer(server);


        FXMLLoader ploader = new FXMLLoader(
                getClass().getClassLoader().getResource("ProgramatorWindow.fxml"));
        Parent proot=ploader.load();
        ProgramatorController programatorController = ploader.<ProgramatorController>getController();
        programatorController.setServer(server);


        FXMLLoader aloader = new FXMLLoader(
                getClass().getClassLoader().getResource("AdministratorWindow.fxml"));
        Parent aroot=aloader.load();
        AdministratorController administratorController = aloader.<AdministratorController>getController();
        administratorController.setServer(server);


        ctrl.setVerificatorParent(croot);
        ctrl.setProgramatorPrent(proot);
        ctrl.setAdministratorParent(aroot);
        ctrl.setVerificatorController(verificatorController);
        ctrl.setProgramatorController(programatorController);
        ctrl.setAdministratorController(administratorController);

        primaryStage.setTitle("ISS chat");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();
    }
}
