package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.java.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class EditCalendarController {
    @FXML ColorPicker calendarColor;
    @FXML ColorPicker appointmentColor;
    @FXML Rectangle calendarShape;
    @FXML Rectangle appointmentShape;
    @FXML Button applyButton;
    @FXML BorderPane borderPane;

    @FXML
    public void initialize() throws IOException {
        
        String mode = Main.user.getCalendarMode();
        Pane calendar;
        if (mode.equals("Day")) {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Day.fxml"));
        } else if (mode.equals("Week")) {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Weekfxml"));
        } else {
            calendar = FXMLLoader.load(getClass().getResource("/main/resources/view/Month.fxml"));
        }
        borderPane.setCenter(calendar);

        /*calendarShape.fillProperty().bind(Main.user.calendarColorProperty());
        appointmentShape.fillProperty().bind(Main.user.appointmentColorProperty());
        calendarColor.setValue(Main.user.getCalendarColor());
        appointmentColor.setValue(Main.user.getAppointmentColor());*/
    }

    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void beforeClick(ActionEvent actionEvent) {
        calendarShape.fillProperty().unbind();
        appointmentShape.fillProperty().unbind();
        calendarShape.fillProperty().bind(Main.user.calendarColorProperty());
        appointmentShape.fillProperty().bind(Main.user.appointmentColorProperty());
    }

    public void afterClick(ActionEvent actionEvent) {
        calendarShape.fillProperty().unbind();
        appointmentShape.fillProperty().unbind();
        calendarShape.fillProperty().bind(calendarColor.valueProperty());
        appointmentShape.fillProperty().bind(appointmentColor.valueProperty());
    }

    public void applyClick(ActionEvent actionEvent) {
        try {
            Statement stmt = Main.con.createStatement();
            String query = "UPDATE setting "
                    + " SET calendarColor = '" + color(calendarColor.getValue()) + "', "
                    + " appointmentColor = '" + color(appointmentColor.getValue())
                    + " ' WHERE email = '" + Main.user.getEmail()
                    + "'";
            stmt.executeUpdate(query);

            Main.user.setCalendarColor(calendarColor.getValue());
            Main.user.setAppointmentColor(appointmentColor.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editClick(ActionEvent event) {
        calendarColor.setDisable(!calendarColor.isDisabled());
        appointmentColor.setDisable(!appointmentColor.isDisabled());
        applyButton.setDisable(!applyButton.isDisabled());
    }

    public String color(Color color) {
        return String.format("#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
}
