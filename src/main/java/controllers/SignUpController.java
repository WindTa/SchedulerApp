package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.Main;

import java.io.IOException;

public class SignUpController {
    public void signInClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void signUpClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }
}
