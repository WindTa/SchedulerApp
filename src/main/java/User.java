package main.java;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class User {
    private static StringProperty email;
    private static StringProperty password;
    private static StringProperty name;
    private static StringProperty phone;
    private static ObjectProperty<LocalDate> birthdate;

    private static StringProperty calendarMode;
    private static ObjectProperty<Color> calendarColor;
    private static ObjectProperty<Color> appointmentColor;

    private static Statement stmt;

    public User(ResultSet user, ResultSet setting) throws SQLException {
        email = new SimpleStringProperty(this, "email", user.getString("email"));
        password = new SimpleStringProperty(this, "password", user.getString("password"));
        name = new SimpleStringProperty(this, "name", user.getString("name"));
        phone = new SimpleStringProperty(this, "phone", user.getString("phone"));
        birthdate = new SimpleObjectProperty<>(user.getDate("birthdate").toLocalDate());
        calendarMode = new SimpleStringProperty(this, "calendarmode", setting.getString("calendarmode"));
        calendarColor = new SimpleObjectProperty<>(Color.web(setting.getString("calendarcolor")));
        appointmentColor = new SimpleObjectProperty<>(Color.web(setting.getString("appointmentcolor")));
    }

    public static String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        User.email.set(email);
    }

    public static StringProperty emailProperty() {
        return email;
    }

    public static String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        User.password.set(password);
    }

    public static StringProperty passwordProperty() {
        return password;
    }

    public static String getName() {
        return name.get();
    }

    public void setName(String name) {
        User.name.set(name);
    }

    public static StringProperty nameProperty() {
        return name;
    }

    public static String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        User.phone.set(phone);
    }

    public static StringProperty phoneProperty() {
        return phone;
    }

    public static LocalDate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(LocalDate birthdate) {
        User.birthdate.set(birthdate);
    }

    public static ObjectProperty<LocalDate> birthdateProperty() {
        return birthdate;
    }

    public static String getCalendarMode() {
        return calendarMode.get();
    }

    public void setCalendarMode(String calendarMode) {
        User.calendarMode.set(calendarMode);
    }

    public static StringProperty calendarModeProperty() {
        return calendarMode;
    }

    public static Color getCalendarColor() {
        return calendarColor.get();
    }

    public void setCalendarColor(Color calendarColor) {
        User.calendarColor.set(calendarColor);
    }

    public static ObjectProperty<Color> calendarColorProperty() {
        return calendarColor;
    }

    public static Color getAppointmentColor() {
        return appointmentColor.get();
    }

    public void setAppointmentColor(Color appointmentColor) {
        User.appointmentColor.set(appointmentColor);
    }

    public static ObjectProperty appointmentColorProperty() {
        return appointmentColor;
    }

    public void update(String email) {
        try {
            stmt = Main.con.createStatement();
            ResultSet user = stmt.executeQuery(
                    String.format("SELECT * FROM user "
                            + "WHERE email = '%s'", email)
            );

            stmt = Main.con.createStatement();
            ResultSet setting = stmt.executeQuery(
                    String.format("SELECT * FROM setting "
                            + "WHERE email = '%s'", email)
            );

            user.next();
            setting.next();

            setEmail(user.getString("email"));
            setPassword(user.getString("password"));
            setName(user.getString("name"));
            setPhone(user.getString("phone"));
            setBirthdate(user.getDate("birthdate").toLocalDate());
            setCalendarMode(setting.getString("calendarmode"));
            setCalendarColor(Color.web(setting.getString("calendarcolor")));
            setAppointmentColor(Color.web(setting.getString("appointmentcolor")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
