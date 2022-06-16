package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Main page.
 */
public class MainController {

    @FXML
    private RadioButton addFestivalRunRadioButton;
    @FXML
    private RadioButton addConcertRadioButton;
    @FXML
    private RadioButton addStatisticsRadioButton;
    @FXML
    private Button goButton;

    /**
     * initializes view
     */
    public void initialize(){
        goButton.setDisable(true);
    }

    /**
     * disables concert and stats if add festival selected. enables go button.
     */
    @FXML
    private void onAddFestivalRunRadioButtonPressed(){
        addConcertRadioButton.setDisable(!addConcertRadioButton.isDisabled());
        addStatisticsRadioButton.setDisable(!addStatisticsRadioButton.isDisabled());
        goButton.setDisable(!goButton.isDisabled());
    }
    /**
     * disables festival run and stats if add concert selected. enables go button.
     */
    @FXML
    private void onAddConcertRadioButtonPressed(){
        addFestivalRunRadioButton.setDisable(!addFestivalRunRadioButton.isDisabled());
        addStatisticsRadioButton.setDisable(!addStatisticsRadioButton.isDisabled());
        goButton.setDisable(!goButton.isDisabled());
    }
    /**
     * disables concert and festival run if add stats selected. enables go button.
     */
    @FXML
    private void onAddStatisticsRadioButtonPressed(){
        addConcertRadioButton.setDisable(!addConcertRadioButton.isDisabled());
        addFestivalRunRadioButton.setDisable(!addFestivalRunRadioButton.isDisabled());
        goButton.setDisable(!goButton.isDisabled());
    }
    
    @FXML
    private void onGoButtonClick(ActionEvent event) throws IOException {

        if (addFestivalRunRadioButton.isSelected()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("festivalRun-view.fxml")));
            Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
            s.setTitle("Add Festival Run");
            s.setScene(new Scene(root));
            s.show();
        }
        else if(addConcertRadioButton.isSelected()){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("concerts-view.fxml")));
            Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
            s.setTitle("Add Concert");
            s.setScene(new Scene(root));
            s.show();
        }
        else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("statistics-view.fxml")));
            Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
            s.setTitle("Statistics");
            s.setScene(new Scene(root));
            s.show();
        }

    }


}