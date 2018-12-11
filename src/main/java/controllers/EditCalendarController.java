package main.java.controllers;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import main.java.Main;
import main.java.User;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class EditCalendarController {
    @FXML
    private ColorPicker calendarColor;
    @FXML
    private ColorPicker appointmentColor;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private ToggleGroup stateGroup;
    @FXML
    private JFXRadioButton dayButton;
    @FXML
    private JFXRadioButton weekButton;
    @FXML
    private JFXRadioButton monthButton;
    @FXML
    private JFXRadioButton beforeButton;

    private Statement stmt;

    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue());
        return y >= 128 ? Color.color(0, 0, 0) : Color.color(1, 1, 1);
    }

    @FXML
    public void initialize() {
        String mode = User.getCalendarMode();
        stateGroup.selectToggle(beforeButton);
        if (mode.equals("Day")) {
            modeGroup.selectToggle(dayButton);
        } else if (mode.equals("Week")) {
            modeGroup.selectToggle(weekButton);
        } else {
            modeGroup.selectToggle(monthButton);
        }

        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());

        calendarColor.setValue(User.getCalendarColor());
        appointmentColor.setValue(User.getAppointmentColor());
    }

    public void createDemo(String state, String mode) {
        GridPane gridPane = new GridPane();

        int row = 1, column = 2;

        if (mode.equals("Week")) {
            row = 1;
            column = 7;
        } else if (mode.equals("Month")) {
            row = 6;
            column = 7;
        }

        Color calColor = User.getCalendarColor();
        Color calBorder = getContrastColor(User.getCalendarColor());
        Color appColor = User.getAppointmentColor();
        Color appBorder = getContrastColor(User.getCalendarColor());

        if (state.equals("After")) {
            calColor = calendarColor.getValue();
            calBorder = getContrastColor(calendarColor.getValue());
            appColor = appointmentColor.getValue();
            appBorder = getContrastColor(appointmentColor.getValue());
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(50, 50);
                anchorPane.setPadding(new Insets(10));
                if (i == (row - 1) && j == (column - 1)) {
                    anchorPane.setStyle(
                            "-fx-background-color: #" + appColor.toString().substring(2, 8) + ";"
                                    + "-fx-border-color: #" + appBorder.toString().substring(2, 8)
                    );
                } else {
                    anchorPane.setStyle(
                            "-fx-background-color: #" + calColor.toString().substring(2, 8) + ";"
                                    + "-fx-border-color: #" + calBorder.toString().substring(2, 8)
                    );
                }
                gridPane.add(anchorPane, j, i);
                GridPane.setHgrow(anchorPane, Priority.ALWAYS);
                GridPane.setVgrow(anchorPane, Priority.ALWAYS);
            }
        }
        borderPane.setCenter(gridPane);
    }

    public void homeClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public void beforeClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void afterClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void calendarClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void appointmentClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void dayClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void weekClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void monthClick(ActionEvent actionEvent) {
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());
    }

    public void applyClick(ActionEvent actionEvent) throws IOException {
        try {
            stmt = Main.con.createStatement();
            stmt.executeUpdate(
                    String.format("UPDATE setting "
                                    + "SET calendarMode = '%s', calendarColor = '%s', appointmentColor = '%s' "
                                    + "WHERE email = '%s'",
                            ((RadioButton) modeGroup.getSelectedToggle()).getText(), color(calendarColor.getValue()), color(appointmentColor.getValue()), User.getEmail())
            );

            Main.user.update(User.getEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createDemo(((RadioButton) stateGroup.getSelectedToggle()).getText(), ((RadioButton) modeGroup.getSelectedToggle()).getText());

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/Home.fxml"));
        Main.update(root);
    }

    public String color(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
