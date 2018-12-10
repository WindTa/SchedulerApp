package main.java;

import com.jfoenix.controls.*;
import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public static boolean email(JFXTextField emailText) {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailText.getText());
        if (m.find() && m.group().equals(emailText.getText())) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Email", "Please enter valid email");
            return false;
        }
    }

     public static boolean password(JFXPasswordField passwordText) {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z]).{8,16})");
        Matcher m = p.matcher(passwordText.getText());
        if (m.find() && m.group().equals(passwordText.getText())) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Password", "Password must contain at least one (Digit, Uppercase or Lowercase Character) and length must be between 8-16 characters");
            return false;
        }
    }

    public static boolean name(JFXTextField generalText) {
        if (!generalText.getText().isEmpty()) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Name", "Name must not be empty");
            return false;
        }
    }

    public static boolean title(JFXTextField generalText) {
        if (!generalText.getText().isEmpty()) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Title", "Title must not be empty");
            return false;
        }
    }

    public static boolean description(TextArea generalText) {
        if (!generalText.getText().isEmpty()) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Description", "Description must not be empty");
            return false;
        }
    }

    public static boolean date(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Date", "Date must not be empty");
            return false;
        }
    }

    public static boolean time(JFXTextField time) {
        Pattern p = Pattern.compile("(1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm])");
        Matcher m = p.matcher(time.getText());
        if (m.find() && m.group().equals(time.getText())) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Time", "Please enter valid time");
            return false;
        }
    }

    public static boolean category(JFXComboBox categoryBox) {
        if (categoryBox.getValue() != null) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Validate Category", "Category must not be empty");
            return false;
        }
    }

    public static boolean isMatchingPW(JFXPasswordField passwordText) {
        if (passwordText.getText().equals(User.getPassword())) {
            return true;
        } else {
            alert(Alert.AlertType.WARNING, "Password Mismatch", "Old password does not match");
            return false;
        }
    }

    public static void alert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
