package main.java.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import main.java.Main;
import main.java.Validate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class SignUpController extends Validate {
    @FXML
    private JFXTextField emailText;
    @FXML
    private JFXPasswordField passwordText;
    @FXML
    private JFXTextField nameText;
    @FXML
    private DatePicker birthDate;
    @FXML
    private JFXTextField phoneText;
    private Statement stmt;

    public void signInClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignIn.fxml"));
        Main.update(root);
    }

    public void signUpClick(ActionEvent event) throws IOException {
        if (email(emailText) && password(passwordText) && name(nameText) && phone(phoneText) && date(birthDate)) {
            try {
                stmt = Main.con.createStatement();

                stmt.executeUpdate(
                        String.format("INSERT INTO user (email, password, name, phone, birthdate) VALUES"
                                + "('%s', '%s', '%s', '%s', '%s')", emailText.getText(), passwordText.getText(), nameText.getText(), phoneText.getText(), birthDate.getValue())
                );

                stmt.executeUpdate(
                        String.format("INSERT INTO setting (email, calendarmode, calendarcolor, appointmentcolor) VALUES"
                                + "('%s', 'Week', '#FFFFFF', '#0000FF')", emailText.getText())
                );

                stmt.executeUpdate(
                        String.format("INSERT INTO reminder(email, media, schedule) VALUES"
                                + "('%s', 'Text', '00:30:00')", emailText.getText())
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
