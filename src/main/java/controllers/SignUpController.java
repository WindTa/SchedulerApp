package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    public void signInClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void signUpClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
