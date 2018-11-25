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
        Main.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void exitClick(ActionEvent event) throws IOException {
        Main.closeProgram();
    }

    public void editInfoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditInfo.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void makeAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/MakeApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void cancelAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/CancelApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void editAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void editColorClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditColor.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }
}