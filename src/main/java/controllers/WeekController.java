package main.java.controllers;

import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.java.Main;
import main.java.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class WeekController implements Initializable {

    @FXML
    private Label labelMonth;
    @FXML
    private Label labelYear;
    @FXML
    private GridPane gridPane;
    @FXML
    private HBox hbox;

    private ArrayList<AnchorPaneNode> dateList = new ArrayList<>();
    private LocalDate date = LocalDate.now();
    private Statement stmt;

    public static Color getContrastColor(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();

        if (r <= 0.03928) {
            r = r / 12.92;
        } else {
            r = Math.pow((r + 0.055)/1.055, 2.4) ;
        }

        if (g <= 0.03928) {
            g = g / 12.92;
        } else {
            r = Math.pow((g + 0.055)/1.055, 2.4);
        }

        if (b <= 0.03928) {
            b = b / 12.92;
        } else {
            b = Math.pow((g + 0.055)/1.055, 2.4);
        }

        double L = 0.2126 * r + 0.7152 * g + 0.0722 * b;

        return L >= 0.1791 ? Color.color(0, 0, 0) : Color.color(1, 1, 1);
    }

    public static String convertTime(String time) {
        DateTimeFormatter fmt_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fmt_12 = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime t = LocalTime.parse(time, fmt_24);
        return fmt_12.format(t);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Color background = User.getCalendarColor();
        Color font = getContrastColor(User.getCalendarColor());
        hbox.setStyle(
                "-fx-background-color: #" + background.toString().substring(2, 8) + ";"
                        + "-fx-border-color: #" + font.toString().substring(2, 8)
        );

        labelMonth.setStyle(
                "-fx-text-fill: #" + font.toString().substring(2, 8) + ";"
        );

        labelYear.setStyle(
                "-fx-text-fill: #" + font.toString().substring(2, 8) + ";"
        );

        labelMonth.setText(String.valueOf(date.getMonth()));
        labelYear.setText(String.valueOf(date.getYear()));

        //Add AnchorPane to GridView
        for (int i = 0; i < 1; i++) { //Row has 6, means we only shows six weeks on calendar, change it to your needs.
            for (int j = 0; j < 7; j++) { //Column has 7, for 7 days a week
                //Layout of AnchorPane
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(200, 200);
                anchorPane.setPadding(new Insets(10));
                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateList.add(anchorPane); //add the AnchorPane in a list
            }
        }

        populateDate(YearMonth.now());

    }

    /**
     * Method that populate the date of moth in GridPane
     **/
    private void populateDate(YearMonth yearMonthNow) {
        YearMonth yearMonth = yearMonthNow;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), date.getDayOfMonth());
        calendarDate = calendarDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        // Populate the calendar with day numbers
        for (AnchorPaneNode anchorPane : dateList) {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(15));
            vbox.setSpacing(10);
            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear(); //remove the label in AnchorPane
            }

            anchorPane.setDate(calendarDate); //set date into AnchorPane

            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font("Roboto", 16)); //set the font of Text
            label.getStyleClass().add("notInRangeDays");
            if (isDateInRange(yearMonth, anchorPane.getDate())) {
                label.getStyleClass().remove("notInRangeDays");
            }
            if (anchorPane.getDate().equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))) {
                label.getStyleClass().remove("notInRangeDays");
            }

            AnchorPane.setTopAnchor(label, 5.0);
            AnchorPane.setLeftAnchor(label, 5.0);
            anchorPane.getChildren().add(vbox);
            vbox.getChildren().add(label);
            anchorPane.getStyleClass().remove("selectedDate"); //remove selection on date change
            anchorPane.getStyleClass().remove("dateNow"); //remove selection on current date
            if (anchorPane.getDate().equals(LocalDate.now())) { //if date is equal to current date now, then add a defualt color to pane
                anchorPane.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> { //Handle click event of AnchorPane
                for (AnchorPaneNode anchorPaneNode : dateList) {
                    anchorPaneNode.getStyleClass().remove("selectedDate");
                }
                anchorPane.getStyleClass().add("selectedDate");
            });

            try {
                stmt = Main.con.createStatement();
                ResultSet date = stmt.executeQuery(
                        String.format(
                                "SELECT DISTINCT appdate FROM appointment "
                                        + "WHERE email = '%s' AND appdate = '%s'"
                                , User.getEmail(), calendarDate
                        )
                );

                if (date.next()) {
                    Color background = User.getAppointmentColor();
                    Color font = getContrastColor(User.getAppointmentColor());
                    anchorPane.setStyle("-fx-background-color: #" + background.toString().substring(2, 8) + ";"
                            + "-fx-border-color: #" + font.toString().substring(2, 8)
                    );
                    label.setStyle(
                            "-fx-text-fill: #" + font.toString().substring(2, 8) + ";"
                    );
                } else {
                    Color background = User.getCalendarColor();
                    Color font = getContrastColor(User.getCalendarColor());
                    anchorPane.setStyle("-fx-background-color: #" + background.toString().substring(2, 8) + ";"
                            + "-fx-border-color: #" + font.toString().substring(2, 8)
                    );
                    label.setStyle(
                            "-fx-text-fill: #" + font.toString().substring(2, 8) + ";"
                    );
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                stmt = Main.con.createStatement();
                ResultSet appointments = stmt.executeQuery(
                        String.format(
                                "SELECT apptime, category, event FROM appointment "
                                        + "WHERE email = '%s' AND appdate = '%s'"
                                , User.getEmail(), calendarDate
                        )
                );

                while (appointments.next()) {
                    Text appointment = new Text(
                            String.format("%s \n"
                                            + "\tCategory:\t %s \n"
                                            + "\tEvent:\t %s"
                                    , convertTime(appointments.getString("apptime"))
                                    , appointments.getString("category")
                                    , appointments.getString("event")
                            )
                    );
                    Color font = getContrastColor(User.getAppointmentColor());
                    appointment.setFill(font);
                    vbox.getChildren().add(appointment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            calendarDate = calendarDate.plusDays(1);
        }
    }

    /**
     * Method that return TRUE/FALSE if the specified date is in range of the current month
     **/
    private boolean isDateInRange(YearMonth yearMonth, LocalDate currentDate) {
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), date.getDayOfMonth());
        LocalDate stop = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        return (!currentDate.isBefore(start)) && (currentDate.isBefore(stop));
    }

    /**
     * Method that call the method populateDate(year, month) to change the calendar according to selected month and year
     **/
    private void changeCalendar(int year, String month) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
    }

    @FXML
    private void lastClick(ActionEvent event) {
        if (date.getMonth() != date.minusWeeks(1).getMonth()) {
            date.minusMonths(1);
            if (date.getMonth().getValue() == 1) {
                date.minusYears(1);
            }
        }
        date = date.minusWeeks(1);

        labelMonth.setText(String.valueOf(date.getMonth()));
        labelYear.setText(String.valueOf(date.getYear()));
        changeCalendar(Integer.parseInt(labelYear.getText()), labelMonth.getText());
    }

    @FXML
    private void nextClick(ActionEvent event) {
        if (date.getMonth() != date.plusWeeks(1).getMonth()) {
            date.plusMonths(1);
            if (date.getMonth().getValue() == 1) {
                date.plusYears(1);
            }
        }
        date = date.plusWeeks(1);

        labelMonth.setText(String.valueOf(date.getMonth()));
        labelYear.setText(String.valueOf(date.getYear()));
        changeCalendar(Integer.parseInt(labelYear.getText()), labelMonth.getText());
    }
    /**Method that hides/shows the navigation of selected month**/
}
