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

    public void initialize() throws IOException, ParseException{
        String response = "";
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/getallfestivals").openConnection();
        connection.setRequestMethod("GET");
        int responsecode = connection.getResponseCode();

        if(responsecode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
            }
            scanner.close();
        }

        JSONParser parser = new JSONParser();
        JSONArray array = (JSONArray) parser.parse(response);
        for(int i=0; i<array.size(); i++){
            try {
                JSONObject temp = (JSONObject) array.get(i);
                festivalComboBox.getItems().add(temp.get("festivalId") + "-" + temp.get("festivalName"));
            }
            catch(Exception e){
                String response2 = "";
                HttpURLConnection connection2 = (HttpURLConnection)new URL("http://localhost:8080/getdepartment/" + array.get(i)).openConnection();
                connection2.setRequestMethod("GET");
                int responsecode2 = connection2.getResponseCode();

                if(responsecode2 == 200){
                    Scanner scanner = new Scanner(connection2.getInputStream());
                    while(scanner.hasNextLine()){
                        response2 += scanner.nextLine();
                    }
                    scanner.close();
                }
                JSONObject object = (JSONObject) parser.parse(response2);
                festivalComboBox.getItems().add(object.get("festivalId") + "-" + object.get("festivalName"));
            }
        }


    }

}
