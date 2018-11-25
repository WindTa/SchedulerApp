package main.java;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class Main extends Application {

    public static Connection con;
    public static Stage window;
    public static User user;

    public static void main(String[] args) {
        con = ConnectionManager.getConnection();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;


        window.setTitle("J&K Scheduler");

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));
        Scene scene = new Scene(root, 1280, 720);

        window.setScene(scene);
        window.show();
    }

    public static void alert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void closeProgram() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("Couldn't close connection");
        }
        window.close();
    }
}
