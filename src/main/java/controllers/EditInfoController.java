package main.java.controllers;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.java.Main;
import main.java.Validate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class EditInfoController {
    @FXML Text oldEmailText;
    @FXML PasswordField oldPasswordText;
    @FXML Text oldNameText;
    @FXML DatePicker oldBirthDate;

    @FXML TextField newEmailText;
    @FXML PasswordField newPasswordText;
    @FXML TextField newNameText;
    @FXML DatePicker newBirthDate;

    @FXML Button applyButton1;
    @FXML Button applyButton2;
    @FXML Button applyButton3;
    @FXML Button applyButton4;
    @FXML Button editButton1;
    @FXML Button editButton2;
    @FXML Button editButton3;
    @FXML Button editButton4;

    @FXML
    public void initialize() {
        oldEmailText.textProperty().bind(Main.user.emailProperty());
        oldNameText.textProperty().bind(Main.user.nameProperty());
        oldBirthDate.valueProperty().bind(Main.user.birthdateProperty());
    }

    public void homeClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void applyClick1(ActionEvent event) {
        if (Validate.email(newEmailText)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "UPDATE user "
                        + " SET email = '" + newEmailText.getText() + "'"
                        + " WHERE email = '" + oldEmailText.getText()
                        + "'";
                stmt.executeUpdate(query);

                Main.alert(Alert.AlertType.INFORMATION, "Email Confirmation", "Email has changed successfully");

                Main.user.setEmail(newEmailText.getText());
                newEmailText.setText("");
            } catch (SQLIntegrityConstraintViolationException e) {
                Main.alert(Alert.AlertType.WARNING, "User Exists", "This email already has an account");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void applyClick2(ActionEvent event) {
        if (Validate.isMatchingPW(oldPasswordText) && Validate.password(newPasswordText)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "UPDATE user "
                        + " SET password = '" + newPasswordText.getText() + "'"
                        + " WHERE email = '" + oldEmailText.getText()
                        + "'";
                stmt.executeUpdate(query);

                Main.alert(Alert.AlertType.INFORMATION, "Password Confirmation", "Password has changed successfully");

                Main.user.setPassword(newPasswordText.getText());
                oldPasswordText.setText("");
                newPasswordText.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void applyClick3(ActionEvent event) {
        if (Validate.general(newNameText)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "UPDATE user "
                        + " SET name = '" + newNameText.getText() + "'"
                        + " WHERE email = '" + oldEmailText.getText()
                        + "'";
                stmt.executeUpdate(query);

                Main.alert(Alert.AlertType.INFORMATION, "Name Confirmation", "Name has changed successfully");

                Main.user.setName(newNameText.getText());
                newNameText.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void applyClick4(ActionEvent event) {
        if (Validate.date(newBirthDate)) {
            try {
                Statement stmt = Main.con.createStatement();
                String query = "UPDATE user "
                        + " SET birthdate = '" + newBirthDate.getValue() + "'"
                        + " WHERE email = '" + oldEmailText.getText()
                        + "'";
                stmt.executeUpdate(query);

                Main.alert(Alert.AlertType.INFORMATION, "Birth Date Confirmation", "Birth date has changed successfully");

                Main.user.setBirthdate(newBirthDate.getValue());
                newBirthDate.setValue(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editClick1(ActionEvent event) {
        newEmailText.setDisable(!newEmailText.isDisabled());
        applyButton1.setDisable(!applyButton1.isDisabled());
    }

    public void editClick2(ActionEvent event) {
        oldPasswordText.setDisable(!oldPasswordText.isDisabled());
        newPasswordText.setDisable(!newPasswordText.isDisabled());
        applyButton2.setDisable(!applyButton2.isDisabled());

    }

    public void editClick3(ActionEvent event) {
        newNameText.setDisable(!newNameText.isDisabled());
        applyButton3.setDisable(!applyButton3.isDisabled());

    }

    public void editClick4(ActionEvent event) {
        newBirthDate.setDisable(!newBirthDate.isDisabled());
        applyButton4.setDisable(!applyButton4.isDisabled());

    }
}
