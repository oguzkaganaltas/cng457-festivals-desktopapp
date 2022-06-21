package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
/**
 * thread for festival stats object
 */
public class StatisticsFestival implements Runnable{

    @FXML
    private ListView festivalListView;

    StatisticsFestival(ListView festivalListView){
        this.festivalListView = festivalListView;
    }

    /**
     * festial run operation thread
     */
    @Override
    public void run() {
        festivalListView.getItems().clear();
        StringBuilder responseFestival = new StringBuilder();
        HttpURLConnection connectionFestival = null;
        try {
            connectionFestival = (HttpURLConnection)new URL("http://localhost:8080/popularfestivals").openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connectionFestival.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        int responseCodeFestival = 0;
        try {
            responseCodeFestival = connectionFestival.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responseCodeFestival == 200){
            Scanner scanner = null;
            try {
                scanner = new Scanner(connectionFestival.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(scanner.hasNextLine()){
                responseFestival.append(scanner.nextLine());
            }
            scanner.close();
        }
        JSONParser parserFestival = new JSONParser();
        JSONArray arrayFestival = null;
        try {
            arrayFestival = (JSONArray) parserFestival.parse(responseFestival.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Object o : arrayFestival) {
            try {
                JSONObject temp = (JSONObject) o;
                festivalListView.getItems().add(temp.get("festivalId") + "-" + temp.get("festivalName"));
            } catch (Exception e) {
                StringBuilder responseCatch = new StringBuilder();
                HttpURLConnection connectionCatch = null;
                try {
                    System.out.println("http://localhost:8080/getfestival/"+o.toString());
                    connectionCatch = (HttpURLConnection)new URL("http://localhost:8080/getfestival/"+o.toString()).openConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    connectionCatch.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    ex.printStackTrace();
                }
                int responseCodeCatch = 0;
                try {
                    responseCodeCatch = connectionCatch.getResponseCode();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(responseCodeCatch == 200){
                    Scanner scannerCatch = null;
                    try {
                        scannerCatch = new Scanner(connectionCatch.getInputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    while(scannerCatch.hasNextLine()){
                        responseCatch.append(scannerCatch.nextLine());
                    }
                    scannerCatch.close();
                }

                JSONParser parserCatch = new JSONParser();
                JSONObject objectCatch = null;
                try {
                    objectCatch = (JSONObject) parserCatch.parse(responseCatch.toString());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                festivalListView.getItems().add(objectCatch.get("festivalId") + "-" + objectCatch.get("festivalName"));
            }
        }
    }
}
