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
            Main.alert(Alert.AlertType.WARNING, "Validate Email", "Please enter valid email");
            return false;
        }
    }

     public static boolean password(PasswordField passwordText) {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z]).{8,16})");
        Matcher m = p.matcher(passwordText.getText());
        if (m.find() && m.group().equals(passwordText.getText())) {
            return true;
        } else {
            Main.alert(Alert.AlertType.WARNING, "Validate Password", "Password must contain at least one (Digit, Uppercase or Lowercase Character) and length must be between 8-16 characters");
            return false;
        }
    }

    public static boolean general(TextField generalText) {
        if (!generalText.getText().isEmpty()) {
            return true;
        } else {
            Main.alert(Alert.AlertType.WARNING, "Validate Name", "Name must not be empty");
            return false;
        }
    }

    public static boolean date(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            return true;
        } else {
            Main.alert(Alert.AlertType.WARNING, "Validate Birth Date", "Birth date must not be empty");
            return false;
        }
    }

    public static boolean isMatchingPW(PasswordField passwordText) {
        if (passwordText.getText().equals(Main.user.getPassword())) {
            return true;
        } else {
            Main.alert(Alert.AlertType.WARNING, "Password Mismatch", "Old password does not match");
            return false;
        }
    }
}
