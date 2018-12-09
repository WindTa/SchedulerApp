package main.java.controllers;

import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import main.java.Main;

import java.net.URL;

import java.time.LocalDate;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class WeekController implements Initializable {

    @FXML private Label labelMonth;
    @FXML private Label labelYear;
    @FXML private GridPane gridPane;

    private ArrayList<AnchorPaneNode> dateList = new ArrayList<>();
    private LocalDate date = LocalDate.now();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelMonth.setText(String.valueOf(date.getMonth()));
        labelYear.setText(String.valueOf(date.getYear()));

        //Add AnchorPane to GridView
        for (int i = 0; i < 1; i++) { //Row has 6, means we only shows six weeks on calendar, change it to your needs.
            for (int j = 0; j < 7; j++) { //Column has 7, for 7 days a week
                //Layout of AnchorPane
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(200,200);
                anchorPane.setPadding(new Insets(10));
                anchorPane.setStyle("-fx-background-color: #" + Main.user.getCalendarColor().toString().substring(2, 8));
                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateList.add(anchorPane); //add the AnchorPane in a list
            }
        }

        populateDate(YearMonth.now());

    }

    /**Method that populate the date of moth in GridPane**/
    private void populateDate(YearMonth yearMonthNow){
        YearMonth yearMonth = yearMonthNow;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), date.getDayOfMonth());

        // Populate the calendar with day numbers
        for (AnchorPaneNode anchorPane : dateList) {
            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear(); //remove the label in AnchorPane
            }

            anchorPane.setDate(calendarDate); //set date into AnchorPane

            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font("Roboto",16)); //set the font of Text
            label.getStyleClass().add("notInRangeDays");
            if(isDateInRange(yearMonth, anchorPane.getDate())){
                label.getStyleClass().remove("notInRangeDays");
            }
            if (anchorPane.getDate().equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))){
                label.getStyleClass().remove("notInRangeDays");
            }

            anchorPane.setTopAnchor(label, 5.0);
            anchorPane.setLeftAnchor(label, 5.0);
            anchorPane.getChildren().add(label);
            anchorPane.getStyleClass().remove("selectedDate"); //remove selection on date change
            anchorPane.getStyleClass().remove("dateNow"); //remove selection on current date
            if(anchorPane.getDate().equals(LocalDate.now())){ //if date is equal to current date now, then add a defualt color to pane
                anchorPane.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> { //Handle click event of AnchorPane
                for(AnchorPaneNode anchorPaneNode : dateList){
                    anchorPaneNode.getStyleClass().remove("selectedDate");
                }
                anchorPane.getStyleClass().add("selectedDate");
            });

            calendarDate = calendarDate.plusDays(1);
        }
    }

    /**Method that return TRUE/FALSE if the specified date is in range of the current month**/
    private boolean isDateInRange(YearMonth yearMonth, LocalDate currentDate){
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), date.getDayOfMonth());
        LocalDate stop = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        return ( ! currentDate.isBefore( start ) ) && ( currentDate.isBefore( stop ) ) ;
    }

    /**Method that call the method populateDate(year, month) to change the calendar according to selected month and year**/
    private void changeCalendar(int year, String month){
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
    private void nextClick(ActionEvent event){
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
