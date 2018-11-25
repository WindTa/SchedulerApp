package main.java;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    public static boolean email(TextField emailText) {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailText.getText());
        if (m.find() && m.group().equals(emailText.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid email");
            alert.showAndWait();
            return false;
        }
    }

     public static boolean password(PasswordField passwordText) {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z]).{8,16})");
        Matcher m = p.matcher(passwordText.getText());
        if (m.find() && m.group().equals(passwordText.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one (Digit, Uppercase or Lowercase Character) and length must be between 8-16 characters");
            alert.showAndWait();
            return false;
        }
    }

    public static boolean general(TextField nameText) {
        if (!nameText.getText().isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Name");
            alert.setHeaderText(null);
            alert.setContentText("Name must not be empty");
            alert.showAndWait();
            return false;
        }
    }

    public static boolean date(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate Birth Date");
            alert.setHeaderText(null);
            alert.setContentText("Birth date must not be empty");
            alert.showAndWait();
            return false;
        }
    }
}
