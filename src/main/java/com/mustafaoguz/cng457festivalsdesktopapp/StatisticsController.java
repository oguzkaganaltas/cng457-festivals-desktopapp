package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class StatisticsController {
    @FXML
    private CheckBox concertCheckBox;
    @FXML
    private CheckBox festivalCheckBox;
    @FXML
    private ListView festivalListView;
    @FXML
    private ListView concertListView;

    public void showButtonPressed(ActionEvent event){

        Runnable fillFestival = new StatisticsFestival(festivalListView);
        Thread festivalThread = new Thread(fillFestival);

        Runnable fillConcert = new StatisticsConcert(concertListView);
        Thread concertThread = new Thread(fillConcert);

        if(festivalCheckBox.isSelected() && concertCheckBox.isSelected()){

            Platform.runLater(concertThread);
            Platform.runLater(festivalThread);

        }
        else{
            if(festivalCheckBox.isSelected()){
                Platform.runLater(festivalThread);
            }
            if(concertCheckBox.isSelected()){
                Platform.runLater(concertThread);
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
