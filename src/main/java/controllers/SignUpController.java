package main.java.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

public class SignUpController extends Validate {
    @FXML private JFXTextField emailText;
    @FXML private JFXPasswordField passwordText;
    @FXML private JFXTextField nameText;
    @FXML private DatePicker birthDate;
    private Statement stmt;

    public void signInClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));
        Main.update(root);
    }

    public void signUpClick(ActionEvent event) throws IOException {
        if (email(emailText) && password(passwordText) && general(nameText) && date(birthDate)) {
            try {
                stmt = Main.con.createStatement();

                stmt.executeUpdate(
                        String.format("INSERT INTO user (email, password, name, birthdate) VALUES"
                                    + "('%s', '%s', '%s', '%s')", emailText.getText(), passwordText.getText(), nameText.getText(), birthDate.getValue())
                );

                stmt.executeUpdate(
                        String.format("INSERT INTO setting (email, calendarmode, calendarcolor, appointmentcolor) VALUES"
                                    + "('%s', 'Week', '#FFFFFF', '#0000FF')", emailText.getText())
                );

                Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));
                Main.update(root);
            } catch (SQLIntegrityConstraintViolationException e) {
                alert(Alert.AlertType.WARNING, "User Exists", "This email already has an account");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
