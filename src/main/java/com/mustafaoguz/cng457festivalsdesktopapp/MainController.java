package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

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


}