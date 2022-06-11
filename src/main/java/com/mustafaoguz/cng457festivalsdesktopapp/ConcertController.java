package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class ConcertController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField durationTextField;
    @FXML
    private DatePicker datePickerConcert;
    @FXML
    private ComboBox festivalComboBox;

    public void addButtonPressed(ActionEvent event) throws IOException {

    }

    public void initialize() throws IOException, ParseException, org.json.simple.parser.ParseException {
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection)new URL("http://localhost:8080/getallfestivals").openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();
        if(responsecode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response.append(scanner.nextLine());
            }
            scanner.close();
        }

        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(response.toString());
        for (Object o : array) {
            try {
                JSONObject temp = (JSONObject) o;
                festivalComboBox.getItems().add(temp.get("festivalId") + "-" + temp.get("festivalName"));
                System.out.println(temp.get("festivalId") + "-" + temp.get("festivalName"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
