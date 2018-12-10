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
    private static ObjectProperty<LocalDate> birthdate;

    private static StringProperty calendarMode;
    private static ObjectProperty<Color> calendarColor;
    private static ObjectProperty<Color> appointmentColor;

    private static Statement stmt;

    public User(ResultSet user, ResultSet setting) throws SQLException {
        email = new SimpleStringProperty(this, "email", user.getString("email"));
        password = new SimpleStringProperty(this, "password", user.getString("password"));
        name = new SimpleStringProperty(this, "name", user.getString("name"));
        birthdate = new SimpleObjectProperty<>(user.getDate("birthdate").toLocalDate());
        calendarMode = new SimpleStringProperty(this, "calendarmode", setting.getString("calendarmode"));
        calendarColor = new SimpleObjectProperty<>(Color.web(setting.getString("calendarcolor")));
        appointmentColor = new SimpleObjectProperty<>(Color.web(setting.getString("appointmentcolor")));
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
            setBirthdate(user.getDate("birthdate").toLocalDate());
            setCalendarMode(setting.getString("calendarmode"));
            setCalendarColor(Color.web(setting.getString("calendarcolor")));
            setAppointmentColor(Color.web(setting.getString("appointmentcolor")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getEmail() {
        return email.get();
    }

    public static StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public static String getPassword() {
        return password.get();
    }

    public static StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public static String getName() {
        return name.get();
    }

    public static StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public static LocalDate getBirthdate() {
        return birthdate.get();
    }

    public static ObjectProperty<LocalDate> birthdateProperty() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate.set(birthdate);
    }

    public static String getCalendarMode() {
        return calendarMode.get();
    }

    public static StringProperty calendarModeProperty() {
        return calendarMode;
    }

    public void setCalendarMode(String calendarMode) {
        this.calendarMode.set(calendarMode);
    }

    public static Color getCalendarColor() {
        return calendarColor.get();
    }

    public static ObjectProperty<Color> calendarColorProperty() {
        return calendarColor;
    }

    public void setCalendarColor(Color calendarColor) {
        this.calendarColor.set(calendarColor);
    }

    public static Color getAppointmentColor() {
        return appointmentColor.get();
    }

    public static ObjectProperty appointmentColorProperty() {
        return appointmentColor;
    }

    public void setAppointmentColor(Color appointmentColor) {
        this.appointmentColor.set(appointmentColor);
    }

}
