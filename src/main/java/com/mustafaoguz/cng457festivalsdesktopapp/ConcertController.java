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
    @FXML
    private ComboBox festivalRunComboBox;
    @FXML
    private ComboBox performerComboBox;

    public void addButtonPressed(ActionEvent event) throws IOException {

    }

    public void isFestivalSelected(ActionEvent event) throws IOException, org.json.simple.parser.ParseException {
        StringBuilder response = new StringBuilder();
        String selectedItem = festivalComboBox.getValue().toString();
        String festivalRunId = selectedItem.split("-")[0];
        HttpURLConnection connection = (HttpURLConnection)new URL("http://localhost:8080/getallfestivalruns/"+festivalRunId).openConnection();

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
                festivalRunComboBox.getItems().add(temp.get("festRunId")+"-"+temp.get("date"));
                System.out.println(temp.get("festRunId")+"-"+temp.get("date"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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

        StringBuilder performerResponse = new StringBuilder();
        HttpURLConnection performerConnection = (HttpURLConnection)new URL("http://localhost:8080/getallperformers").openConnection();
        performerConnection.setRequestMethod("GET");
        int performerResponseCode = performerConnection.getResponseCode();
        if(performerResponseCode == 200){
            Scanner scanner = new Scanner(performerConnection.getInputStream());
            while(scanner.hasNextLine()){
                performerResponse.append(scanner.nextLine());
            }
            scanner.close();
        }

        JSONParser performerParser = new JSONParser();
        JSONArray performerArray = (JSONArray) performerParser.parse(performerResponse.toString());

        for (Object p : performerArray) {
            try {
                JSONObject tempPerformer = (JSONObject) p;
                performerComboBox.getItems().add(tempPerformer.get("performerId") + "-" + tempPerformer.get("name")+" " + tempPerformer.get("surname"));
                System.out.println(tempPerformer.get("performerId") + "-" + tempPerformer.get("name")+" " + tempPerformer.get("surname"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }

}
