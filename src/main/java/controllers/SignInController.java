package main.java.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import main.java.Main;
import main.java.User;
import main.java.Validate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignInController extends Validate {
    @FXML
    private JFXTextField emailText;
    @FXML
    private JFXPasswordField passwordText;
    private Statement stmt;

    public void signUpClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));
        Main.update(root);
    }

    public void signInClick(ActionEvent event) throws IOException {
        if (email(emailText) && password(passwordText)) {
            try {
                stmt = Main.con.createStatement();
                ResultSet user = stmt.executeQuery(
                        String.format("SELECT * FROM user "
                                + "WHERE email = '%s' AND BINARY password = '%s'", emailText.getText(), passwordText.getText())
                );

                stmt = Main.con.createStatement();
                ResultSet setting = stmt.executeQuery(
                        String.format("SELECT * FROM setting "
                                + "WHERE email = '%s'", emailText.getText())
                );

                if (user.next()) {
                    setting.next();
                    Main.user = new User(user, setting);

                    Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
                    Main.update(root);
                } else {
                    alert(Alert.AlertType.WARNING, "Credential Mismatch", "The Email/PW combination does not match any account");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
