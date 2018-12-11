package main.java.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import main.java.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ReminderController extends Validate {
    public static final String ACCOUNT_SID = "AC93fd036cf7ff68436dd27f0247276a9a";
    public static final String AUTH_TOKEN = "0c857f758f8023959ee3dce38e1791be";
    @FXML
    JFXListView<Appointment> searchResult;
    @FXML
    ToggleGroup mediaGroup;
    @FXML
    JFXRadioButton textButton;
    @FXML
    JFXRadioButton emailButton;
    @FXML
    JFXTextField scheduleText;
    @FXML
    Button sendButton;
    private ObservableList<Appointment> list = FXCollections.observableArrayList();
    private Statement stmt;

    public static String convertTime(String time) {
        DateTimeFormatter fmt_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fmt_12 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime t = LocalTime.parse(time, fmt_24);
        return fmt_12.format(t);
    }

    @FXML
    public void initialize() {
        list.clear();
        try {
            stmt = Main.con.createStatement();
            ResultSet reminder = stmt.executeQuery(
                    String.format(
                            "SELECT * FROM reminder WHERE email = '%s'",
                            User.getEmail()
                    )
            );

            reminder.next();
            if (reminder.getString("media").equals("Text")) {
                mediaGroup.selectToggle(textButton);
            } else {
                mediaGroup.selectToggle(emailButton);
            }

            scheduleText.setText(convertTime(reminder.getString("schedule")));

            stmt = Main.con.createStatement();

            ResultSet appointments = stmt.executeQuery(
                    String.format(
                            "SELECT * FROM appointment WHERE email = '%s'",
                            User.getEmail()
                    )
            );

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

    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public void defaultClick(ActionEvent actioNEvent) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("H:mm", Locale.ENGLISH);
        if (reminder(scheduleText)) {
            try {
                stmt = Main.con.createStatement();
                stmt.executeUpdate(
                        String.format("UPDATE reminder SET "
                                        + "media = '%s', "
                                        + "schedule = '%s' "
                                        + "WHERE email = '%s'"
                                , ((RadioButton) mediaGroup.getSelectedToggle()).getText(), LocalTime.parse(scheduleText.getText(), inputFormatter), User.getEmail()
                        )
                );
                alert(Alert.AlertType.INFORMATION, " Success", "Reminder setttings succesfully changed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void mouseClick(MouseEvent mouseEvent) {
        Appointment a = searchResult.getSelectionModel().getSelectedItem();
        if (a != null) {
            sendButton.setDisable(false);
        } else {
            sendButton.setDisable(true);
        }
    }

    public void sendClick(ActionEvent actionEvent) throws IOException {
        Appointment a = searchResult.getSelectionModel().getSelectedItem();
        String msg = String.format("Hello %s! This is a reminder for your appointment!\n"
                        + "Date: %s\n"
                        + "Time: %s\n"
                        + "Category: %s\n"
                        + "Event: %s\n"
                        + "Description: %s"
                , User.getName()
                , a.getDate().toString()
                , a.getTime().toString()
                , a.getCategory()
                , a.getEvent()
                , a.getDescription()
        );
        if (((RadioButton) mediaGroup.getSelectedToggle()).getText().equals("Text")) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber("+" + User.getPhone()),
                    new PhoneNumber("+12138058943"),
                    msg
            ).create();
        } else {
            SendEmail SE = new SendEmail(User.getEmail(), "Reminder", msg);
        }
    }
}
