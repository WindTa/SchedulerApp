package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import main.java.Main;

import java.io.IOException;

public class EditAppController {
    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }
}
