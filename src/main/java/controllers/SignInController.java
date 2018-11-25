package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Main;
import main.java.User;
import main.java.Validate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class SignInController {
    @FXML TextField emailText;
    @FXML PasswordField passwordText;

    public void signUpClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void signInClick(ActionEvent event) throws IOException {
        if (Validate.email(emailText) && Validate.password(passwordText)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "SELECT * FROM user WHERE email ='"
                        + emailText.getText() + "' and BINARY password ='"
                        + passwordText.getText() + "'";
                ResultSet user = stmt.executeQuery(query);

                stmt = Main.con.createStatement();
                query = "SELECT * FROM setting WHERE email = '"
                        + emailText.getText() + "'";
                ResultSet setting = stmt.executeQuery(query);

                if (user.next()) {
                    setting.next();
                    Main.user = new User(user, setting);

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));

                    Main.window.getScene().setRoot(root);
                    Main.window.show();
                } else {
                    Main.alert(Alert.AlertType.WARNING, "Credential Mismatch", "The Email/PW combination does not match any account");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
