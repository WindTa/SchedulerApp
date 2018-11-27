package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.java.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeController {
    @FXML ToggleGroup calendarMode;
    @FXML RadioMenuItem monthItem;
    @FXML RadioMenuItem weekItem;
    @FXML RadioMenuItem dayItem;
    @FXML MenuItem importButton;

    @FXML
    public void initialize() {
        String mode = Main.user.getCalendarMode();
        if (mode.equals("Month")) {
            calendarMode.selectToggle(monthItem);
        } else if (mode.equals("Week")) {
            calendarMode.selectToggle(weekItem);
        } else if (mode.equals("Day")){
            calendarMode.selectToggle(dayItem);
        }
    }

    public void importClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Schedule");
        fileChooser.showOpenDialog(importButton.getParentPopup().getScene().getWindow());
    }

    public void exportClick(ActionEvent event) throws IOException {

    }

    public void logoutClick(ActionEvent event) throws IOException {
        Main.user = null;
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void exitClick(ActionEvent event) throws IOException {
        Main.closeProgram();
    }

    public void editInfoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditInfo.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void makeAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/MakeApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void cancelAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/CancelApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void editAppClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditApp.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void editColorClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/EditColor.fxml"));

        Main.window.getScene().setRoot(root);
        Main.window.show();
    }

    public void monthItemClick(ActionEvent event) {
        try {
            Statement stmt = Main.con.createStatement();
            String query = "UPDATE setting "
                    + " SET calendarmode = 'Month'"
                    + " WHERE email = '" + Main.user.getEmail()
                    + "'";
            stmt.executeUpdate(query);
            Main.user.setCalendarMode("Month");
        } catch (SQLException e) {
            e.printStackTrace();
            Main.user.setCalendarMode("Month");
        }
    }

    public void weekItemClick(ActionEvent event) {
        try {
            Statement stmt = Main.con.createStatement();
            String query = "UPDATE setting "
                    + " SET calendarmode = 'Week'"
                    + " WHERE email = '" + Main.user.getEmail()
                    + "'";
            stmt.executeUpdate(query);
            Main.user.setCalendarMode("Week");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dayItemClick(ActionEvent event) {
        try {
            Statement stmt = Main.con.createStatement();
            String query = "UPDATE setting "
                    + " SET calendarmode = 'Day'"
                    + " WHERE email = '" + Main.user.getEmail()
                    + "'";
            stmt.executeUpdate(query);
            Main.user.setCalendarMode("Day");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}