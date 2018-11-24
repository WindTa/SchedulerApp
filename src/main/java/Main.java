package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
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
