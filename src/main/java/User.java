package main.java;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class User {
    private StringProperty email;
    private StringProperty password;
    private StringProperty name;
    private ObjectProperty<LocalDate> birthdate;

    private StringProperty calendarMode;
    private StringProperty calendarColor;
    private StringProperty appointmentColor;

    //rs.getString("email"), rs.getString("password"), rs.getString("name"), rs.getDate("birthdate").toLocalDate()
    public User(ResultSet user, ResultSet setting) throws SQLException {
        this.email = new SimpleStringProperty(this, "email", user.getString("email"));
        this.password = new SimpleStringProperty(this, "password", user.getString("password"));
        this.name = new SimpleStringProperty(this, "name", user.getString("name"));
        this.birthdate = new SimpleObjectProperty<LocalDate>(user.getDate("birthdate").toLocalDate());
        this.calendarMode = new SimpleStringProperty(this, "calendarmode", setting.getString("calendarmode"));
        this.calendarColor = new SimpleStringProperty(this, "calendarcolor", setting.getString("calendarcolor"));
        this.appointmentColor = new SimpleStringProperty(this, "appointmentcolor", setting.getString("appointmentcolor"));
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public LocalDate getBirthdate() {
        return birthdate.get();
    }

    public ObjectProperty<LocalDate> birthdateProperty() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate.set(birthdate);
    }

    public String getCalendarMode() {
        return calendarMode.get();
    }

    public StringProperty calendarModeProperty() {
        return calendarMode;
    }

    public void setCalendarMode(String calendarMode) {
        this.calendarMode.set(calendarMode);
    }

    public String getCalendarColor() {
        return calendarColor.get();
    }

    public StringProperty calendarColorProperty() {
        return calendarColor;
    }

    public void setCalendarColor(String calendarColor) {
        this.calendarColor.set(calendarColor);
    }

    public String getAppointmentColor() {
        return appointmentColor.get();
    }

    public StringProperty appointmentColorProperty() {
        return appointmentColor;
    }

    public void setAppointmentColor(String appointmentColor) {
        this.appointmentColor.set(appointmentColor);
    }
}
