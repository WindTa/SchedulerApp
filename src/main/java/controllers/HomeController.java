package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.Main;

import java.io.IOException;

public class HomeController {
    public void logoutClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void exitClick(ActionEvent event) throws IOException {
        Main.closeProgram();
    }
}