package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import main.java.Main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancelAppController {

    @FXML ComboBox searchBox;

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
                searchBox.getItems().add(categories.getString("category"));
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
        String query = "SELECT ";
    }
}
