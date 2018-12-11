package main.java;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    private Date appdate;

    private Time apptime;

    private String category;

    private String event;

    private String description;

    public Appointment(Date appdate, Time apptime, String category, String event, String description) {
        this.appdate = appdate;
        this.apptime = apptime;
        this.category = category;
        this.event = event;
        this.description = description;
    }

    public Date getDate() {
        return appdate;
    }

    public Time getTime() {
        return apptime;
    }

    public String getCategory() {
        return category;
    }

    public String getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public static String convertTime(String time) {
        DateTimeFormatter fmt_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fmt_12 = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime t = LocalTime.parse(time, fmt_24);
        return fmt_12.format(t);
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s \n\t%s", appdate.toString(), convertTime(apptime.toString()), event, description);
    }
}
