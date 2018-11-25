package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.Main;
import main.java.Validate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    @FXML TextField emailText;
    @FXML PasswordField passwordText;
    @FXML TextField nameText;
    @FXML DatePicker birthDate;

    public void signInClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void signUpClick(ActionEvent event) throws IOException {
        if (Validate.email(emailText) && Validate.password(passwordText) && Validate.general(nameText) && Validate.date(birthDate)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "INSERT INTO user (email, password, name, birthdate) VALUES"
                        + "('"
                        + emailText.getText() + "', '"
                        + passwordText.getText() + "', '"
                        + nameText.getText() + "', '"
                        + birthDate.getValue() + "')";
                stmt.executeUpdate(query);

                query = "INSERT INTO setting (email, calendarmode, calendarcolor, appointmentcolor) VALUES"
                        + "('"
                        + emailText.getText() + "', '"
                        + "Week', '"
                        + "#FFFFFF', '"
                        + "#0000FF')";
                stmt.executeUpdate(query);

                Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));

                Main.window.getScene().setRoot(root);
                Main.window.show();
            } catch (SQLIntegrityConstraintViolationException e) {
                Main.alert(Alert.AlertType.WARNING, "User Exists", "This email already has an account");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
