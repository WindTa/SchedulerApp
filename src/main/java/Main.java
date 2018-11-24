package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    public static Connection con;

    public static void main(String[] args) {
        con = ConnectionManager.getConnection();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("J&K Scheduler");
        stage.setScene(scene);
        stage.show();
    }
}
