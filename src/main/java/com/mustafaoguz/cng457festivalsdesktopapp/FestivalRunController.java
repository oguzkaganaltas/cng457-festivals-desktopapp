package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class FestivalRunController {

    @FXML
    private ComboBox<String> festivalsComboBox;
    @FXML
    private TextField durationTextField;
    @FXML
    private DatePicker dateTimePicker;


    public void initialize() throws IOException, ParseException {

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
                festivalsComboBox.getItems().add(temp.get("festivalId") + "-" + temp.get("festivalName"));
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
                festivalsComboBox.getItems().add(objectCatch.get("festivalId") + "-" + objectCatch.get("festivalName"));
            }
        }
    }

    public void addButtonPressed(ActionEvent event) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/addfestivalrun").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json; utf-8");
        connection.setDoInput(true); //Set the DoInput flag to true if you intend to use the URL connection for input
        connection.setDoOutput(true);
        String festivalId = festivalsComboBox.getValue().toString().split("-")[0];

        JSONObject festivalRun = new JSONObject();
        JSONObject festival = new JSONObject();
        festival.put("festivalId",festivalId);
        festivalRun.put("festival", festival);
        festivalRun.put("duration", durationTextField.getText());
        festivalRun.put("date", dateTimePicker.getValue().toString());


        System.out.println(festivalRun.toJSONString());
        try(OutputStream os = connection.getOutputStream()){
            byte[] input = festivalRun.toJSONString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String response = "";
        int responsecode = connection.getResponseCode();
        if(responsecode == 200){
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()){
                response += scanner.nextLine();
            }
            scanner.close();
        }

        System.out.println(response);
        onReturnButtonClick(event);
    }

    @FXML
    private void onReturnButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main-view.fxml")));
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setTitle("TRNC FestMan");
        s.setScene(new Scene(root));
        s.show();
    }


}
