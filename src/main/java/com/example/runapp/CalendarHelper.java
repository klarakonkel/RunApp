package com.example.runapp;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CalendarHelper {
    public static List<Integer> getYears (List<Run> list) {
        List<Integer> years = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //tablica ma length, a lista ma size
            int year = list.get(i).getDate().getYear();
            if (!years.contains(year)){
                years.add(year);
            }
        }

        return years;
    }
    public static StackPane getTile (double number) {
        StackPane stackPane = new StackPane();

        Rectangle rectangle = new Rectangle(30, 30, Color.BLUEVIOLET);
        Text text = new Text(number+ "");
        text.setFont(Font.font(16));
        text.setFill(Color.WHITE);

        stackPane.getChildren().addAll(rectangle,text);
        return stackPane;
    }
    public static StackPane getEmptyTile() {
        StackPane stackPane = new StackPane();

        Rectangle rectangle = new Rectangle(30, 30, Color.LIGHTGRAY);
//        Text text = new Text(dayOfTheWeek + "");
//        text.setFont(Font.font(16));
//        text.setFill(Color.WHITE);

        stackPane.getChildren().addAll(rectangle);
        return stackPane;
    }

    public static List<String> getMonths() {
        List<String> months = Arrays.asList("January","February","March","April","May","June","July","August","September","October","November","December");

        return months;
    }

    public static GridPane getCalendar(int year, String month, List<Run> runs) {
        List<Run> chosen = new ArrayList<>();
        for (int i = 0; i < runs.size(); i++) {
            if (runs.get(i).getDate().getYear() ==
                    year && runs.get(i).getDate().getMonth().toString().equals(month.toUpperCase())) {
                chosen.add(runs.get(i));
            }
        }
        /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */ System.out.println(chosen.toString());

        GridPane gridPane = new GridPane();

        int dayOfTheWeek = LocalDate.of(year,Month.valueOf(month.toUpperCase()), 1).getDayOfWeek().getValue() -1;


        //if (chosen.size() == 0) {
            for (int i = 0; i < Month.valueOf(month.toUpperCase()).length(Year.isLeap(year)); i++) {
                gridPane.add(getEmptyTile(), (i + dayOfTheWeek) % 7, (i + dayOfTheWeek) / 7); }
           // return gridPane;
        //}

        //else {

         //   LocalDate startOfMonth = LocalDate.of(year,chosen.get(0).getDate().getMonth(),1);

            for (int i = 0; i < chosen.size(); i++) {
                gridPane.add(getTile(chosen.get(i).getDistance()),
                        (chosen.get(i).getDate().getDayOfMonth()+dayOfTheWeek)%7-1,
                        (chosen.get(i).getDate().getDayOfMonth()+dayOfTheWeek)/7);
                //takie obliczenia + rozrysowanie tak jak pan są fajne do internala
            }
            // return gridPane;

       // }
        return gridPane;
    }

}
