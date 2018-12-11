package main.java.controllers;

import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;

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
import main.java.Appointment;
import main.java.Main;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static main.java.Validate.alert;

public class HomeController {
    @FXML private MenuItem importButton;
    @FXML private MenuItem exportButton;
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
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import Schedule");
            File file = fileChooser.showOpenDialog(importButton.getParentPopup().getScene().getWindow());

            if (FilenameUtils.getExtension(file.getName()).equals("csv")) {
                    SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                    CSVReader reader = new CSVReader(new FileReader(file), ',');
                    String[] record = null;

                    while((record = reader.readNext()) != null) {
                            stmt = Main.con.createStatement();
                            stmt.executeUpdate(
                                    String.format("REPLACE INTO appointment (email, appdate, apptime, category, event, description) VALUES "
                                                + "('%s', '%s', '%s', '%s', '%s', '%s')"
                                                , Main.user.getEmail(), (new Date(date.parse(record[0]).getTime())).toString()
                                                , Time.valueOf(record[1]).toString(), record[2], record[3], record[4]
                                    )
                            );
                    }

                    alert(Alert.AlertType.INFORMATION, "Success", "Appointments succesfully imported.\n NOTE: Conflicting appointments will be overwritten");
            } else {
                alert(Alert.AlertType.WARNING, "Failed", "File type is not .csv");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void exportClick(ActionEvent event) throws IOException {
        try {
            stmt = Main.con.createStatement();
            ResultSet appointments = stmt.executeQuery(
                    String.format("SELECT appdate, apptime, category, event, description FROM appointment "
                                + " WHERE email = '%s' GROUP BY appdate, apptime, category, event, description", Main.user.getEmail())
            );

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Schedule");
            File file = fileChooser.showSaveDialog(exportButton.getParentPopup().getScene().getWindow());

            if (FilenameUtils.getExtension(file.getName()).equals("csv")) {
                CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
                csvWriter.writeAll(appointments, false);
                csvWriter.close();
                alert(Alert.AlertType.INFORMATION, "Success", "Appointments succesfully exported");
            } else {
                alert(Alert.AlertType.WARNING, "Failed", "File type is not .csv");
            }

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