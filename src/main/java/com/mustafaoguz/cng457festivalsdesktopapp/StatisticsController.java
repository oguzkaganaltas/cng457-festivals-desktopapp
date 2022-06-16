package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class StatisticsController {
    @FXML
    private CheckBox concertCheckBox;
    @FXML
    private CheckBox festivalCheckBox;
    @FXML
    private ListView festivalListView;
    @FXML
    private ListView concertListView;

    public void showButtonPressed(ActionEvent event) throws IOException, ParseException {

        Runnable fillFestival = new StatisticsFestival(festivalCheckBox,festivalListView);
        Thread festivalThread = new Thread(fillFestival);

        Runnable fillConcert = new StatisticsConcert(concertCheckBox,concertListView);
        Thread concertThread = new Thread(fillConcert);

        if(festivalCheckBox.isSelected() && concertCheckBox.isSelected()){
            festivalThread.start();
            concertThread.start();
        }
        else{
            if(festivalCheckBox.isSelected()){
                festivalThread.start();
            }
            if(concertCheckBox.isSelected()){
                concertThread.start();
            }
        }

    }


    public void onReturnButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setTitle("TRNC FestMan");
        s.setScene(new Scene(root));
        s.show();
    }
}
