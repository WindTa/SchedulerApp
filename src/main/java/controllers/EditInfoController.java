package main.java.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.java.Main;
import main.java.User;
import main.java.Validate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class EditInfoController extends Validate {
    @FXML private JFXTextField oldEmailText;
    @FXML private JFXPasswordField oldPasswordText;
    @FXML private JFXTextField oldNameText;
    @FXML private DatePicker oldBirthDate;

    @FXML private JFXTextField newEmailText;
    @FXML private JFXPasswordField newPasswordText;
    @FXML private JFXTextField newNameText;
    @FXML private DatePicker newBirthDate;
    private Statement stmt;

    @FXML
    public void initialize() {
        oldEmailText.textProperty().bind(Main.user.emailProperty());
        oldNameText.textProperty().bind(Main.user.nameProperty());
        oldBirthDate.valueProperty().bind(Main.user.birthdateProperty());
    }

    public void homeClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public void applyClick(ActionEvent event) {
        boolean editable = true;
        String query = "UPDATE user SET ";

        if (!newEmailText.getText().isEmpty()) {
            if (email(newEmailText)) {
                query += String.format("email = '%s',", newEmailText.getText());
            }
        }

        if (!oldPasswordText.getText().isEmpty() || !newPasswordText.getText().isEmpty()) {
            if (isMatchingPW(oldPasswordText) && password(newPasswordText)) {
                query += String.format("password = '%s',", newPasswordText.getText());
            } else {
                editable = false;
            }
        }

        if (!newNameText.getText().isEmpty()) {
            query += String.format("name = '%s',", newNameText.getText());
        }

        if (newBirthDate.getValue() != null) {
            query += String.format("birthdate = '%s',", newBirthDate.getValue());
        }

        if (!query.equals("UPDATE user SET ") && editable) {
            query = query.substring(0, query.length() - 1) + String.format(" WHERE email = '%s'", oldEmailText.getText());
            try {
                stmt = Main.con.createStatement();
                stmt.executeUpdate(query);
                alert(Alert.AlertType.INFORMATION, "Confirmation", "User Info has changed successfully");

                if (newEmailText.getText().isEmpty()) {
                    Main.user.update(oldEmailText);
                } else {
                    Main.user.update(newEmailText);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                alert(Alert.AlertType.WARNING, "User Exists", "This email already has an account");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        clear();
    }

    public void clear() {
        newEmailText.clear();
        oldPasswordText.clear();
        newPasswordText.clear();
        newNameText.clear();
        newBirthDate.setValue(null);
    }
}
