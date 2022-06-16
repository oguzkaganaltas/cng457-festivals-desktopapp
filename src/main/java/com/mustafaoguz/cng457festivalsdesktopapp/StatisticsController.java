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

        if(festivalCheckBox.isSelected()){
            festivalListView.getItems().clear();
            StringBuilder responseFestival = new StringBuilder();
            HttpURLConnection connectionFestival = (HttpURLConnection)new URL("http://localhost:8080/popularfestivals").openConnection();
            connectionFestival.setRequestMethod("GET");
            int responseCodeFestival = connectionFestival.getResponseCode();
            if(responseCodeFestival == 200){
                Scanner scanner = new Scanner(connectionFestival.getInputStream());
                while(scanner.hasNextLine()){
                    responseFestival.append(scanner.nextLine());
                }
                scanner.close();
            }
            System.out.println(responseFestival.toString());
            JSONParser parserFestival = new JSONParser();
            JSONArray arrayFestival = (JSONArray) parserFestival.parse(responseFestival.toString());
            for (Object o : arrayFestival) {
                try {
                    JSONObject temp = (JSONObject) o;
                    festivalListView.getItems().add(temp.get("festivalId") + "-" + temp.get("festivalName"));
                } catch (Exception e) {
                    StringBuilder responseCatch = new StringBuilder();
                    HttpURLConnection connectionCatch = (HttpURLConnection)new URL("http://localhost:8080/getfestival/"+o.toString()).openConnection();
                    connectionCatch.setRequestMethod("GET");
                    int responseCodeCatch = connectionCatch.getResponseCode();
                    if(responseCodeCatch == 200){
                        Scanner scannerCatch = new Scanner(connectionCatch.getInputStream());
                        while(scannerCatch.hasNextLine()){
                            responseCatch.append(scannerCatch.nextLine());
                        }
                        scannerCatch.close();
                    }

                    JSONParser parserCatch = new JSONParser();
                    JSONObject objectCatch = (JSONObject) parserCatch.parse(responseCatch.toString());
                    festivalListView.getItems().add(objectCatch.get("festivalId") + "-" + objectCatch.get("festivalName"));
                }
            }
        }
        if(concertCheckBox.isSelected()){
            concertListView.getItems().clear();
            StringBuilder responseConcert = new StringBuilder();
            HttpURLConnection connectionConcert = (HttpURLConnection)new URL("http://localhost:8080/longestconcerts").openConnection();
            connectionConcert.setRequestMethod("GET");
            int responseCodeFestival = connectionConcert.getResponseCode();
            if(responseCodeFestival == 200){
                Scanner scanner = new Scanner(connectionConcert.getInputStream());
                while(scanner.hasNextLine()){

                    responseConcert.append(scanner.nextLine());
                }
                scanner.close();
            }
            System.out.println(responseConcert.toString());
            JSONParser parserConcert = new JSONParser();
            JSONArray arrayConcert = (JSONArray) parserConcert.parse(responseConcert.toString());
            for (Object o : arrayConcert) {
                try {
                    JSONObject temp = (JSONObject) o;
                    concertListView.getItems().add(temp.get("eventId") + "-" + temp.get("name"));
                } catch (Exception e) {
                    StringBuilder responseCatch = new StringBuilder();
                    HttpURLConnection connectionCatch = (HttpURLConnection)new URL("http://localhost:8080/getconcert/"+o.toString()).openConnection();
                    connectionCatch.setRequestMethod("GET");
                    int responseCodeCatch = connectionCatch.getResponseCode();
                    if(responseCodeCatch == 200){
                        Scanner scannerCatch = new Scanner(connectionCatch.getInputStream());
                        while(scannerCatch.hasNextLine()){
                            responseCatch.append(scannerCatch.nextLine());
                        }
                        scannerCatch.close();
                    }

                    JSONParser parserCatch = new JSONParser();
                    JSONObject objectCatch = (JSONObject) parserCatch.parse(responseCatch.toString());
                    concertListView.getItems().add(objectCatch.get("eventId") + "-" + objectCatch.get("name"));
                }
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
