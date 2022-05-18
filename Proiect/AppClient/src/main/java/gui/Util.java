package gui;

import javafx.scene.control.Alert;

public class Util {


    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ISS chat");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

