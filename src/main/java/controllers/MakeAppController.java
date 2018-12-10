package main.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import main.java.Main;
import main.java.Validate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MakeAppController extends Validate {

    @FXML DatePicker datePicker;
    @FXML JFXTextField startTime;
    @FXML JFXComboBox categoryBox;
    @FXML JFXTextField titleText;
    @FXML TextArea descriptionText;

    private Statement stmt;

    @FXML
    public void initialize() {
        try {
            stmt = Main.con.createStatement();
            ResultSet categories = stmt.executeQuery(
                    String.format("SELECT category FROM appointment "
                                + "WHERE email = '%s' GROUP BY category", Main.user.getEmail())
            );

            while (categories.next()) {
                categoryBox.getItems().add(categories.getString("category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public void addClick(ActionEvent actionEvent) throws IOException {
        if (date(datePicker) && time(startTime) && category(categoryBox) && title(titleText) && description(descriptionText)) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                stmt = Main.con.createStatement();

                stmt.executeUpdate(
                        String.format("INSERT INTO appointment (email, appdate, apptime, category, event, description) VALUES"
                                + "('%s', '%s', '%s', '%s', '%s', '%s')"
                                , Main.user.getEmail(), datePicker.getValue(), LocalTime.parse(startTime.getText(), inputFormatter)
                                , categoryBox.getValue(), titleText.getText(), descriptionText.getText())
                );

                alert(Alert.AlertType.INFORMATION, "Success", "Appointment was successfully created");

                Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
                Main.update(root);
            } catch (SQLIntegrityConstraintViolationException e) {
                alert(Alert.AlertType.WARNING, "Failure", "Appointment has time conflict");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
