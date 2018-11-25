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
    public void importClick(ActionEvent event) throws IOException {

    }

    public void exportClick(ActionEvent event) throws IOException {

    }

    public void logoutClick(ActionEvent event) throws IOException {
        Main.user = "";
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void exitClick(ActionEvent event) throws IOException {
        Main.closeProgram();
    }

    public void editInfoClick(ActionEvent event) throws IOException {

    }

    public void makeAppClick(ActionEvent event) throws IOException {

    }

    public void cancelAppClick(ActionEvent event) throws IOException {

    }

    public void editAppClick(ActionEvent event) throws IOException {

    }

    public void editColorClick(ActionEvent event) throws IOException {

    }
}