package main.java.controllers;

import com.opencsv.CSVWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static main.java.Validate.alert;

public class HomeController {
    @FXML private MenuItem importButton;
    @FXML private BorderPane borderPane;

    private Statement stmt;

    @FXML
    public void initialize() throws IOException {
        String mode = Main.user.getCalendarMode();
        Pane calendar;
        if (mode.equals("Day")) {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Day.fxml"));
        } else if (mode.equals("Week")) {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Week.fxml"));
        } else {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Month.fxml"));
        }
        borderPane.setCenter(calendar);
    }

    public void importClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Schedule");
        fileChooser.showOpenDialog(importButton.getParentPopup().getScene().getWindow());
    }

    public void exportClick(ActionEvent event) throws IOException {
        try {
            stmt = Main.con.createStatement();
            ResultSet appointments = stmt.executeQuery(
                    String.format("SELECT * FROM appointment "
                                + " WHERE email = '%s' GROUP BY appdate, apptime, category, event, description", Main.user.getEmail())
            );

            CSVWriter csvWriter = new CSVWriter(new FileWriter(Main.user.getEmail() + ".csv"));
            csvWriter.writeAll(appointments, true);
            csvWriter.close();
            alert(Alert.AlertType.INFORMATION, "Success", "Appointments succesfully exported");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void logoutClick(ActionEvent event) throws IOException {
        Main.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));
        Main.update(root);
    }

    public void exitClick(ActionEvent event) throws IOException {
        Main.closeProgram();
    }

    public void editInfoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditInfo.fxml"));
        Main.update(root);
    }

    public void makeAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/MakeApp.fxml"));
        Main.update(root);
    }

    public void cancelAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/CancelApp.fxml"));
        Main.update(root);
    }

    public void editAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditApp.fxml"));
        Main.update(root);
    }

    public void editCalendarClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditCalendar.fxml"));
        Main.update(root);
    }
}