package com.example.runapp;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class HelloApplication extends Application {
    static GridPane root;
    static Stage stage;
   static GridPane calendar;
    static Scene scene;
    static List<Run> runs;

    static {
        try {
            runs = DBController.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void menuLayout() {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(20);
        root.setHgap(50);
        Scene scene = new Scene(root, 600, 650);

//        Button distanceButton = new Button("distance");
//        root.add(distanceButton, 1, 2);
//
//        Button timeButton = new Button("time");
//        root.add(timeButton, 2, 2);


        Button tableButton = new Button("table");
        root.add(tableButton, 2, 3);
        tableButton.setOnAction(actionEvent -> tableLayout());

        Button addButton = new Button("add new run");
        root.add(addButton, 3, 3);
        addButton.setOnAction(actionEvent -> addNewRunLayout());

        Button calendarButton = new Button("calendar");
        root.add(calendarButton, 4, 3);
        calendarButton.setOnAction(actionEvent -> calendarLayout());

        ComboBox<String> dataFilter = new ComboBox<>();
        dataFilter.getItems().addAll("all data", "last year", "last month");
        root.add(dataFilter,3,2);
        dataFilter.getSelectionModel().selectFirst();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("day");
        yAxis.setLabel("km");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Running progress");
        runs.sort(Comparator.comparing(Run::getDate));
        XYChart.Series series = new XYChart.Series();
        if (dataFilter.getSelectionModel().isSelected(0)) {
        for (int i = 0; i < runs.size(); i++) {
            series.getData().add(new XYChart.Data(runs.get(i).getDate().toString(), runs.get(i).getDistance()));
        }}
        if ((dataFilter.getSelectionModel().isSelected(0))) {
            int year = LocalDate.now().getYear();
            for (int i = 0; i < runs.size(); i++) {
                if (runs.get(i).getDate().getYear() == (year)) {
                    series.getData().add(new XYChart.Data(runs.get(i).getDate().toString(), runs.get(i).getDistance()));
                }
            }
        }
        else if ( (dataFilter.getSelectionModel().isSelected(1))) {
            LocalDate now1 = LocalDate.now();
            for (int i = 0; i < runs.size(); i++) {
                LocalDate date = runs.get(i).getDate();
                if (now1.minusDays(30).isBefore(date)) {
                    series.getData().add( new XYChart.Data(runs.get(i).getDate().toString(), runs.get(i).getDistance()));
                }

            }
        }
        dataFilter.getSelectionModel().selectFirst();
        dataFilter.setOnAction(actionEvent -> {
            series.getData().clear();
            if (dataFilter.getSelectionModel().isSelected(0)) {
                for (int i = 0; i < runs.size(); i++) {
                    series.getData().add(new XYChart.Data( runs.get(i).getDate().toString(), runs.get(i).getDistance()));

                }
            }
            else if (dataFilter.getSelectionModel().isSelected(1)) {
                int year = LocalDate.now().getYear();
                for (int i = 0; i < runs.size(); i++) {
                    if (runs.get(i).getDate().getYear() == year){
                        series.getData().add(new XYChart.Data( runs.get(i).getDate().toString(), runs.get(i).getDistance()));
                    }

                }
            }
            else if ((dataFilter.getSelectionModel().isSelected(2))) {
                LocalDate now2 = LocalDate.now();
                for (int i = 0; i < runs.size(); i++) {
                    LocalDate date = runs.get(i).getDate();
                    if (now2.minusDays(30).isBefore(date)) {
                        series.getData().add( new XYChart.Data(runs.get(i).getDate().toString(), runs.get(i).getDistance()));
                    }
                }
            }
        });
        lineChart.getData().add(series);
        root.add(lineChart,0,0, 5,1);

        series.setName("Runs");

        stage.setScene(scene);

    }
    public static void tableLayout(){
        root = new GridPane();

        root.setVgap(20);
        root.setHgap(50);
        root.setAlignment(Pos.CENTER);
        stage.setTitle("TABLE");

        Button backButton = new Button("back");
        root.add(backButton, 0, 0);
        backButton.setOnAction(actionEvent -> menuLayout());


        final Label label = new Label("Your history of runs");
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font("Arial", 20));


        TableView<Run> tableWithRuns = new TableView<>();

        tableWithRuns.setEditable(true);

        TableColumn<Run, Double> distanceColumn = new TableColumn<>("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));

        TableColumn<Run, Double> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Run, Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableWithRuns.getColumns().addAll(distanceColumn, timeColumn, dateColumn);
        tableWithRuns.getItems().addAll(runs);


        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableWithRuns);
        root.add(vbox, 1, 1);

        stage.getScene().setRoot(root);
    }

    public static void addNewRunLayout(){
        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(20);
        root.setHgap(50);
        stage.setTitle("ADD NEW RUN");

        Button backButton = new Button("back");
        root.add(backButton, 0, 0);
        backButton.setOnAction(actionEvent -> menuLayout());

        Text timeText = new Text("time");
        root.add(timeText, 0, 2);

        Text distanceText = new Text("distance");
        root.add(distanceText,0,1);

        Text dateText = new Text("date");
        root.add(dateText,0,3);

        TextField timeTextField = new TextField();
        root.add(timeTextField,1,2);

        TextField distanceTextField = new TextField();
        root.add(distanceTextField,1,1);

        //TextField dateTextField = new TextField();
        //  root.add(dateTextField,1,3);
     //   DatePicker
        DatePicker datePicker = new DatePicker();
        root.add(datePicker,1,3 );


        Button addButton = new Button("add");
        addButton.setStyle("-fx-background-color: #74B72E");
        root.add(addButton,1,4);
        addButton.setOnAction(actionEvent -> {
            Run newRun = new Run(Double.parseDouble(distanceTextField.getText()), Double.parseDouble(timeTextField.getText()), datePicker.getValue());
            DBController.add(newRun);
            runAddedLayout();
        });

        Button cancelButton = new Button("cancel");
        cancelButton.setStyle("-fx-background-color: #D0312D");
        root.add(cancelButton,2,4);
        cancelButton.setOnAction(actionEvent -> {
            distanceTextField.clear();
            timeTextField.clear();
            datePicker.setValue(null);
        });


        stage.getScene().setRoot(root);
    }
    public static void runAddedLayout () {
        root = new GridPane();
        root.setAlignment(Pos.CENTER);

        root.setVgap(20);
        root.setHgap(50);
        Scene scene = new Scene(root, 200, 200);
        stage.setScene(scene);
        stage.setTitle("ADDED");

        Text distanceText = new Text("Run added");
        root.add(distanceText,1,0);

        Button backButton = new Button("back");
        root.add(backButton, 1, 1);
        backButton.setOnAction(actionEvent -> menuLayout());

        stage.getScene().setRoot(root);
    }

    public static void calendarLayout(){
        root = new GridPane();
        root.setAlignment(Pos.CENTER);

        root.setVgap(20);
        root.setHgap(50);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.setTitle("CALENDAR");

        Button backButton = new Button("back");
        root.add(backButton, 0, 0);
        backButton.setOnAction(actionEvent -> menuLayout());

        ComboBox<Integer> yearComboBox = new ComboBox<>();
        yearComboBox.getItems().addAll(CalendarHelper.getYears(runs));
        yearComboBox.getSelectionModel().selectLast();
        yearComboBox.setMinWidth(100);
        root.add(yearComboBox, 1, 0);

        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(CalendarHelper.getMonths());
        int now = LocalDate.now().getMonth().getValue();
        monthComboBox.getSelectionModel().select(now - 1);
        monthComboBox.setMinWidth(100);
        root.add(monthComboBox,2,0);

       calendar = CalendarHelper.getCalendar(yearComboBox.getValue(), monthComboBox.getValue(), runs);
        root.add(calendar, 1, 2);

        yearComboBox.setOnAction(event -> {
            root.getChildren().remove(calendar);
            calendar = CalendarHelper.getCalendar(yearComboBox.getValue(), monthComboBox.getValue(), runs);
                    root.add(calendar, 1,2);
        });
        monthComboBox.setOnAction(event -> {
            root.getChildren().remove(calendar);
            calendar = CalendarHelper.getCalendar(yearComboBox.getValue(), monthComboBox.getValue(), runs);
            root.add(calendar, 1,2);
        });

        stage.getScene().setRoot(root);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = new Stage();
        stage.setTitle("MENU");
        stage.show();

        root = new GridPane();

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);

        menuLayout();
    }


    public static void main(String[] args) {

        launch(args);

    }

}