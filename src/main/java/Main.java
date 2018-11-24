package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    public static Connection con;
    public static Stage window;

    public static void main(String[] args) {
        con = ConnectionManager.getConnection();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/SignUp.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        window.setTitle("J&K Scheduler");

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        window.setScene(scene);
        window.show();
    }

    public static void closeProgram() {
        System.out.println("Closing program...");
        window.close();
    }
}
