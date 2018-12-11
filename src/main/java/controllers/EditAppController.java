package main.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.java.Appointment;
import main.java.Main;
import main.java.User;
import main.java.Validate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EditAppController extends Validate {

    @FXML
    DatePicker searchStartDate;
    @FXML
    DatePicker searchEndDate;
    @FXML
    JFXTextField searchStartTime;
    @FXML
    JFXTextField searchEndTime;
    @FXML
    JFXComboBox searchBox;
    @FXML
    JFXTextField searchText;
    @FXML
    JFXListView<Appointment> searchResult;

    @FXML
    VBox vbox;
    @FXML
    DatePicker newDate;
    @FXML
    JFXTextField newTime;
    @FXML
    JFXComboBox newCategory;
    @FXML
    JFXTextField newTitle;
    @FXML
    TextArea newDescription;

    private ObservableList<Appointment> list = FXCollections.observableArrayList();
    private Statement stmt;

    public static String convertTime(String time) {
        DateTimeFormatter fmt_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fmt_12 = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime t = LocalTime.parse(time, fmt_24);
        return fmt_12.format(t);
    }

    public void initialize() {
        try {
            searchBox.getItems().clear();
            newCategory.getItems().clear();
            stmt = Main.con.createStatement();
            ResultSet categories = stmt.executeQuery(
                    String.format("SELECT category FROM appointment "
                            + "WHERE email = '%s' GROUP BY category", User.getEmail())
            );

            while (categories.next()) {
                searchBox.getItems().add(categories.getString("category"));
                newCategory.getItems().add(categories.getString("category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public void searchClick(ActionEvent actionEvent) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

        boolean editable = true;
        String query = String.format(
                "SELECT * FROM appointment WHERE (email = '%s')",
                User.getEmail()
        );

        if (searchStartDate.getValue() != null && searchEndDate.getValue() != null) {
            if (searchDateValid(searchStartDate, searchEndDate)) {
                query += String.format(
                        " AND (appdate BETWEEN '%s' AND '%s')",
                        searchStartDate.getValue(), searchEndDate.getValue()
                );
            } else {
                editable = false;
            }
        } else if (searchStartDate.getValue() != null && searchEndDate.getValue() == null) {
            query += String.format(
                    " AND (appdate >= '%s')",
                    searchStartDate.getValue()
            );
        } else if (searchStartDate.getValue() == null && searchEndDate.getValue() != null) {
            query += String.format(
                    " AND (appdate <= '%s')",
                    searchEndDate.getValue()
            );
        }

        if (!searchStartTime.getText().isEmpty() && !searchEndTime.getText().isEmpty()) {
            if (time(searchStartTime) && time(searchEndTime) && searchTimeValid(searchStartTime, searchEndTime)) {
                query += String.format(
                        " AND (apptime BETWEEN '%s' AND '%s')",
                        LocalTime.parse(searchStartTime.getText(), inputFormatter), LocalTime.parse(searchEndTime.getText(), inputFormatter)
                );
            } else {
                editable = false;
            }
        } else if (!searchStartTime.getText().isEmpty() && searchEndTime.getText().isEmpty()) {
            if (time(searchStartTime)) {
                query += String.format(
                        " AND (apptime >= '%s')",
                        LocalTime.parse(searchStartTime.getText(), inputFormatter)
                );
            }
        } else if (searchStartTime.getText().isEmpty() && !searchEndTime.getText().isEmpty()) {
            if (time(searchEndTime)) {
                query += String.format(
                        " AND (apptime <= '%s')",
                        LocalTime.parse(searchEndTime.getText(), inputFormatter)
                );
            }
        }

        if (searchBox.getValue() != null) {
            if (!searchBox.getValue().equals("")) {
                query += String.format(
                        " AND (category = '%s')",
                        searchBox.getValue()
                );
            }
        }

        if (!searchText.getText().isEmpty()) {
            query += " AND (event LIKE '%" + searchText.getText() + "%' OR description LIKE '%" + searchText.getText() + "%')";

            //query += " AND ((event LIKE '\"%" + searchText.getText() + "%\"') OR (description LIKE '\"%" + searchText.getText() + "%\"'))";
        }

        if (editable) {
            list.clear();
            try {
                System.out.println(query);
                stmt = Main.con.createStatement();
                ResultSet appointments = stmt.executeQuery(query);
                while (appointments.next()) {

                    list.add(new Appointment(
                            appointments.getDate("appdate"),
                            appointments.getTime("apptime"),
                            appointments.getString("category"),
                            appointments.getString("event"),
                            appointments.getString("description")
                    ));
                }
                searchResult.setItems(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void mouseClick(MouseEvent mouseEvent) {
        Appointment a = searchResult.getSelectionModel().getSelectedItem();
        if (a != null) {
            vbox.setDisable(false);
            DateTimeFormatter date = DateTimeFormatter.ofPattern("m-d-yyyy");
            DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm a");
            newDate.setValue(LocalDate.parse(a.getDate().toString()));
            newTime.setText(convertTime(a.getTime().toString()));
            newCategory.getSelectionModel().select(a.getCategory());
            newTitle.setText(a.getEvent());
            newDescription.setText(a.getDescription());
        } else {
            vbox.setDisable(true);
        }
    }

    public void applyClick(ActionEvent actionEvent) throws IOException {
        Appointment a = searchResult.getSelectionModel().getSelectedItem();
        if (date(newDate) && time(newTime) && category(newCategory) && title(newTitle) && description(newDescription)) {
            try {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

                stmt = Main.con.createStatement();
                stmt.executeUpdate(
                        String.format("UPDATE appointment SET"
                                        + " appdate = '%s',"
                                        + " apptime = '%s',"
                                        + " category = '%s',"
                                        + " event = '%s',"
                                        + " description = '%s'"
                                        + " WHERE email = '%s'"
                                        + " AND appdate = '%s'"
                                        + " AND apptime = '%s'"
                                        + " AND category = '%s'"
                                        + " AND event = '%s'"
                                        + " AND description = '%s'"
                                , newDate.getValue(), LocalTime.parse(newTime.getText(), inputFormatter)
                                , newCategory.getValue(), newTitle.getText(), newDescription.getText()
                                , User.getEmail(), a.getDate(), a.getTime(), a.getCategory(), a.getEvent(), a.getDescription()
                        )
                );

                alert(Alert.AlertType.INFORMATION, "Success", "Appointment was successfully updated");
                vbox.setDisable(true);
                searchClick(actionEvent);
                initialize();
            } catch (SQLIntegrityConstraintViolationException e) {
                alert(Alert.AlertType.WARNING, "Failure", "Appointment has time conflict");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
